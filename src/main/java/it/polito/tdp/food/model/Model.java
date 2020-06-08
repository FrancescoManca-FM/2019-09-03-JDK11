package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private FoodDao dao;
	private SimpleWeightedGraph<String, DefaultWeightedEdge> grafo;
	private List<VicinoPeso> best;
	private int passi;
	
	public Model() {
		this.dao = new FoodDao();
	}
	
	public void creaGrafo(int calorie) {
		
		this.grafo = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		List<String> vertici = this.dao.getPortionDisplayName(calorie);
		for(String s : vertici) {
			this.grafo.addVertex(s);
		}
		List<Adiacenza> archi = this.dao.getAdiacenze();
		for(Adiacenza a : archi) {
			if(this.grafo.containsVertex(a.getNomePorzioneA()) && this.grafo.containsVertex(a.getNomePorzioneB())) {
				if(this.grafo.getEdge(a.getNomePorzioneA(), a.getNomePorzioneB())==null || this.grafo.getEdge(a.getNomePorzioneB(), a.getNomePorzioneA())==null) {
					Graphs.addEdge(this.grafo, a.getNomePorzioneA(), a.getNomePorzioneB(), a.getPeso());
				}
			}
		}
		
		
	}
	
	public int getSizeVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int getSizeArchi(){
		return this.grafo.edgeSet().size();
	}
	
	public List<VicinoPeso> getVicini(String porzione){
		List<VicinoPeso> risultato = new ArrayList<>();
		List<String> vicini = Graphs.neighborListOf(this.grafo, porzione);
		
		for(String s : vicini) {
			DefaultWeightedEdge e = this.grafo.getEdge(porzione, s);
			if(e==null) {
				e = this.grafo.getEdge(s, porzione);
			}
			risultato.add(new VicinoPeso(s, (int)this.grafo.getEdgeWeight(e)));
		}
		return risultato;
	}
	public Set<String> getVertexSet(){
		return this.grafo.vertexSet();
	}
	
	public List<VicinoPeso> getCammino(int N, String porzione) {
		this.passi = N;
		best = new ArrayList<>();
		List<VicinoPeso> parziale = new ArrayList<>();
		List<String> temp = new ArrayList<>();
		parziale.add(new VicinoPeso(porzione, 0));
		temp.add(porzione);
		ricorsione(temp, parziale, 0);
		return best;
		
	}
	
	public void ricorsione(List<String> temp, List<VicinoPeso> parziale, int livello) {
		
		//caso terminale
		if(livello==this.passi) {
			if(pesoCammino(parziale)>pesoCammino(best)) {
				best = new ArrayList<>(parziale);
			}
			return;
		}
		
		List<String> vicini = Graphs.neighborListOf(this.grafo, parziale.get(parziale.size()-1).getVicino());
		String nome = temp.get(temp.size()-1);
		for(String a : vicini) {
			if(!temp.contains(a)) {
				DefaultWeightedEdge e = this.grafo.getEdge(nome, a);
				if(e==null) {
					e = this.grafo.getEdge(a, nome);
				}
				int peso = (int)this.grafo.getEdgeWeight(e);
				parziale.add(new VicinoPeso(a, peso));
				temp.add(a);
				ricorsione(temp, parziale, livello+1);
				parziale.remove(parziale.size()-1);
				temp.remove(temp.size()-1);
			}
		}
		
		
	}
	
	public int pesoCammino(List<VicinoPeso> lista) {
		int totale = 0;
		for(VicinoPeso vp : lista) {
			totale += vp.getPeso(); 
		}
		return totale;
	}
}
