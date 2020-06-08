package it.polito.tdp.food.model;

public class Adiacenza {

	
	private String nomePorzioneA;
	private String nomePorzioneB;
	private int peso;
	public Adiacenza(String nomePorzioneA, String nomePorzioneB, int peso) {
		super();
		this.nomePorzioneA = nomePorzioneA;
		this.nomePorzioneB = nomePorzioneB;
		this.peso = peso;
	}
	public String getNomePorzioneA() {
		return nomePorzioneA;
	}
	public void setNomePorzioneA(String nomePorzioneA) {
		this.nomePorzioneA = nomePorzioneA;
	}
	public String getNomePorzioneB() {
		return nomePorzioneB;
	}
	public void setNomePorzioneB(String nomePorzioneB) {
		this.nomePorzioneB = nomePorzioneB;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	
}
