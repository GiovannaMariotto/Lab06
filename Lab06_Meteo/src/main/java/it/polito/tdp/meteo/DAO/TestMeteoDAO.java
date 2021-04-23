package it.polito.tdp.meteo.DAO;

import java.util.List;

import it.polito.tdp.meteo.model.Citta;
import it.polito.tdp.meteo.model.Rilevamento;

public class TestMeteoDAO {

	public static void main(String[] args) {
		
		MeteoDAO dao = new MeteoDAO();

		//List<Rilevamento> list = dao.getAllRilevamenti();
		List<Citta> listaR = dao.getUmiditaMediadelleCitta(11);

		// STAMPA: localita, giorno, mese, anno, umidita (%)
		for (Citta r : listaR) {
			System.out.format("%-10s %2td/%2$2tm/%2$4tY %3d%%\n",r.getNome(),  r.getUmiditaMedia());
		}
		
//		System.out.println(dao.getAllRilevamentiLocalitaMese(1, "Genova"));
//		System.out.println(dao.getAvgRilevamentiLocalitaMese(1, "Genova"));
//		
//		System.out.println(dao.getAllRilevamentiLocalitaMese(5, "Milano"));
//		System.out.println(dao.getAvgRilevamentiLocalitaMese(5, "Milano"));
//		
//		System.out.println(dao.getAllRilevamentiLocalitaMese(5, "Torino"));
//		System.out.println(dao.getAvgRilevamentiLocalitaMese(5, "Torino"));
		

	}

}
