package it.polito.tdp.meteo.model;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.meteo.DAO.MeteoDAO;

public class Model {
	
	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	private MeteoDAO dao = new MeteoDAO();

	public Model() {

	}

	// of course you can change the String output with what you think works best
	public String getUmiditaMedia(int mese) {
		
		return "TODO!";
	}
	
	// of course you can change the String output with what you think works best
	public String trovaSequenza(int mese) {
		//Objetivo: MIN(costo)
		//vincoli: min=3g, max=6g. + cambio citta=100 + tutte le citta visitate almeno 1 volta
		List<Citta> citta = new ArrayList(dao.getUmiditaMediadelleCitta(mese));
		//nella lista ha 3 citta. Calcolare con la ricorsione la somma minima di costi
		int counter=0;
		String sequenza="";
		
		for(int i=1;i<NUMERO_GIORNI_TOTALI;i++) {
			Citta c1 = citta.get(0);
			Citta c2 = citta.get(1);
			Citta c3 = citta.get(2);
			c1.setRilevamenti(dao.getAllRilevamentiLocalitaMese(mese, c1.getNome()));
			c2.setRilevamenti(dao.getAllRilevamentiLocalitaMese(mese, c2.getNome()));
			c3.setRilevamenti(dao.getAllRilevamentiLocalitaMese(mese, c3.getNome()));
			sequenza = this.compareRilevamento(i, c1, c2);
			
			
			
			
		}
		
		return "TODO!";
	}
	/**
	 * Compara a umidade de duas cidades em um dia
	 * 
	 * @param giorno
	 * @param c1 (cidade 1)
	 * @param c2 (cidade 2)
	 * @return Obj Citta with lower umidita
	 */
	public Citta compareRilevamento(int giorno, Citta c1, Citta c2, Citta c3) {
		Rilevamento r1 = c1.getRilevamenti().get(giorno);
		Rilevamento r2 = c2.getRilevamenti().get(giorno);
		Rilevamento r3 = c3.getRilevamenti().get(giorno);
		if(r1.getUmidita()<r2.getUmidita()) {
			if(r1.getUmidita()<r3.getUmidita()) {
				return c1;
			}else if(r3.getUmidita()<r2.getUmidita()){
				return c3;
			}
		}else if(r2.getUmidita()<r3.getUmidita()) {
			return c2;
		}
		
	}
	
	
	public List<Rilevamento> getAllRilevamentiCittaNelMese(int mese, String localita){
		Citta c = new Citta(localita);
		c.setRilevamenti(dao.getAllRilevamentiLocalitaMese(mese, localita));
		return c.getRilevamenti();
	}
	
	public int calcolaUmiditaMedia(List<Rilevamento> lista) {
		int n = lista.size();
		double somma=0;
		for(Rilevamento r : lista) {
			somma+=r.getUmidita();
		}
		return(int)(somma/n);
	}
	

}
