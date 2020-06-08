package it.polito.tdp.food.model;

public class TestModel {

	public static void main(String[] args) {

		Model m = new Model();
		m.creaGrafo(300);
		System.out.println("GRAFO CREATO CON SUCCESSO! \n # VERTICI: "+m.getSizeVertici()+"\n # ARCHI: "+m.getSizeArchi());
	}

}
