package it.polito.tdp.meteo.model;

public class TestModel {

	public static void main(String[] args) {
		
		Model m = new Model();
		
		System.out.println(m.getUmiditaMedia(12));
		
		System.out.println(m.trovaSequenza(5));
		System.out.println(m.getAllRilevamentiCittaNelMese(1,"Torino"));
		System.out.println(m.calcolaUmiditaMedia(m.getAllRilevamentiCittaNelMese(2,"Torino")));

	}

}
