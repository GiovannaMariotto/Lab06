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
	List<Citta> leCitta = new ArrayList<Citta>();
	List<Citta> best;

	public Model() {
		MeteoDAO dao = new MeteoDAO();
		this.leCitta = dao.getAllCitta();
	}

	// of course you can change the String output with what you think works best
	public Double getUmiditaMedia(int mese, Citta c) {
		MeteoDAO dao = new MeteoDAO();
		return dao.getUmiditaMedia(mese, c);
	}
	
	// of course you can change the String output with what you think works best
	public List<Citta> trovaSequenza(int mese) {
		MeteoDAO dao = new MeteoDAO();
		List<Citta> parziale = new ArrayList();
		//Objetivo: MIN(costo)
		int costo=0;
		//vincoli: min=3g, max=6g. + cambio citta=100 + tutte le citta visitate almeno 1 volta
		//nella lista ha 3 citta. Calcolare con la ricorsione la somma minima di costi
	
		for(Citta c : leCitta) {
			c.setRilevamenti(dao.getAllRilevamentiLocalitaMese(mese, c.getNome()));
		}
		cerca(parziale,0);
		
		return best ;
	}
	
	private void cerca(List<Citta> parziale, int livello) {
		if(livello == NUMERO_GIORNI_TOTALI) {
			//CASO TERMINALE
			Double costo = calcolaCosto(parziale);
			if(best==null||costo<calcolaCosto(best)) {
				best = new ArrayList<Citta>(parziale);
			}
		}else {
			//caso intermediario
			for(Citta prova : leCitta) {
				if(aggiuntaValida(prova,parziale)) {
					parziale.add(prova);
					cerca(parziale,livello+1);
					parziale.remove(parziale.size()-1);
				}
			}
		}
		
	}

	private Double calcolaCosto(List<Citta> parziale) {
		double costo=0.0;
		for(int giorno=1;giorno<=NUMERO_GIORNI_TOTALI;giorno++) {
			//dove mi trovo
			Citta c = parziale.get(giorno-1);
			double umid = c.getRilevamenti().get(giorno-1).getUmidita();
			costo+=umid;
		}
		//poi devo sommare 100*numero di volta in cui cambio citta
		for(int giorno=2;giorno<=NUMERO_GIORNI_TOTALI;giorno++) {
			if(!parziale.get(giorno-1).equals(parziale.get(giorno-2))) {
				costo += COST;
			}
		}
		
		return costo;
	}

	private boolean aggiuntaValida(Citta prova, List<Citta> parziale) {
		//verifica giorni massimi
		//contiamo quante volte la città 'prova' era già apparsa nell'attuale lista costruita fin qui
		int conta=0;
		for(Citta precedente : parziale) {
			if(precedente.equals(prova))
				conta++;
		}
		
		if(conta>= NUMERO_GIORNI_CITTA_MAX ) {
			return false;
		}
		
		if(parziale.size()==0) { 
			return true;//primo giorno posso inserire qualsiasi Citta
		}
		if(parziale.size()==1 || parziale.size()==2) {
			//siamo qui nel secondo o terzo giorno, non posso cambiare
			//quindi l'aggiunta è valida solo se la citta prova coincide con sua precedente
			return parziale.get(parziale.size()-1).equals(prova);
		}
		//nel caso generale, se ho già passato i controlli sopra, non c'è nulla che mi vieta di rimanere nella stessa città
		//quindi per i giorni successivi ai primi tre posso sempre rimanere			
		if (parziale.get(parziale.size()-1).equals(prova))
				return true; 
		if (parziale.get(parziale.size()-1).equals(parziale.get(parziale.size()-2)) 
				&& parziale.get(parziale.size()-2).equals(parziale.get(parziale.size()-3)))
					return true;
		
		return false;
	}

 	public List<Rilevamento> getAllRilevamentiCittaNelMese(int mese, String localita){
		Citta c = new Citta(localita);
		c.setRilevamenti(dao.getAllRilevamentiLocalitaMese(mese, localita));
		return c.getRilevamenti();
	}
	
	public Double calcolaUmiditaMedia(List<Rilevamento> lista) {
		int n = lista.size();
		double somma=0;
		for(Rilevamento r : lista) {
			somma+=r.getUmidita();
		}
		return(Double)(somma/n);
	}
	
	public List<Citta> getLeCitta(){
		return leCitta;
	}

}
