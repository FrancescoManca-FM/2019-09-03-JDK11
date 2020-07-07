package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private FoodDao dao;
	private SimpleWeightedGraph<String, DefaultWeightedEdge> grafo;
	private List<String> bestCammino;
	
	public Model() {
		this.dao = new FoodDao();
	}
	
	public void creaGrafo(int calorie) {
		this.grafo = new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		List<String> vertici = this.dao.getTipiPorzione(calorie);
		List<Adiacenza> archi = this.dao.getAdiacenze(calorie);
		Graphs.addAllVertices(this.grafo, vertici);
		
		for(Adiacenza a : archi) {
			if(this.grafo.containsVertex(a.getTipo1()) && this.grafo.containsVertex(a.getTipo2())) {
				Graphs.addEdge(this.grafo, a.getTipo1(), a.getTipo2(), a.getPeso());
			}
		}
	}
	
	public int verticiSize() {
		return this.grafo.vertexSet().size();
	}
	
	public int archiSize() {
		return this.grafo.edgeSet().size();
	}
	
	public List<String> tipiPorzione(int calorie){
		return this.dao.getTipiPorzione(calorie);
	}
	
	public List<VicinoPeso> getVicini(String porzione){
		List<String> vicini = Graphs.neighborListOf(this.grafo, porzione);
		List<VicinoPeso> risultato = new ArrayList<>();
		for(String s : vicini) {
			if(this.grafo.containsEdge(porzione, s)) {
				risultato.add(new VicinoPeso(s, (int)this.grafo.getEdgeWeight(this.grafo.getEdge(porzione, s))));
			}else {
				risultato.add(new VicinoPeso(s, (int)this.grafo.getEdgeWeight(this.grafo.getEdge(s, porzione))));
			}
		}
		return risultato;
	}
	
	public List<String> camminoMassimo(int N, String partenza){
		this.bestCammino = new ArrayList<>();
		List<String> parziale = new ArrayList<>();
		parziale.add(partenza);
		ricorsione(parziale, N, 0);
		return bestCammino;
	}
	
	public void ricorsione(List<String> parziale, int N, int livello) {
		
		if(parziale.size()==N) {
			if(pesoCammino(parziale)>pesoCammino(bestCammino)) {
				bestCammino = new ArrayList<>(parziale);
				return;
			}else {
				return;
			}
		}
		List<String> vicini = Graphs.neighborListOf(this.grafo, parziale.get(parziale.size()-1));
		for(String s : vicini) {
			if(!parziale.contains(s)) {
				parziale.add(s);
				ricorsione(parziale, N, livello+1);
				parziale.remove(parziale.size()-1);
			}
		}
	}
	
	public int pesoCammino(List<String> parziale) {
		if(parziale.size()==0) {
			return 0;
		}
		int totale = 0;
		for(int i=0; i<parziale.size()-1; i++) {
			totale += this.grafo.getEdgeWeight(this.grafo.getEdge(parziale.get(i), parziale.get(i+1)));
		}
		return totale;
	}
	
}
