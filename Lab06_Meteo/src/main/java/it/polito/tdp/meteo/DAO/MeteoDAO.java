package it.polito.tdp.meteo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.meteo.model.Citta;
import it.polito.tdp.meteo.model.Rilevamento;

public class MeteoDAO {
	
	public List<Rilevamento> getAllRilevamenti() {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Rilevamento> getAllRilevamentiLocalitaMese(int mese, String localita) {
		int counter =0;
		
		if(mese==1||mese==3||mese==5||mese==7||mese==8||mese==10||mese==12) {
			counter=31;
		}else if(mese==2) {
			counter=28;
		}else {
			counter=30;
		}
		String sql = "SELECT * "
				+ "FROM situazione s "
				+ "WHERE s.Localita=? AND s.DATA=?";
		
		String data;
		List<Rilevamento> r=new ArrayList<Rilevamento>();
		try {
		
			for(int i=0;i<counter;i++) {
				data="2013-"+mese+"-"+i;
				Connection conn = ConnectDB.getConnection();
				PreparedStatement st = conn.prepareStatement(sql);
				st.setString(1, localita);
				st.setString(2, data);
				ResultSet rs = st.executeQuery();
				
				if(rs!=null) {
					while(rs.next()) {
						Rilevamento r1 = new Rilevamento(rs.getString("Localita"),rs.getDate("Data"),rs.getInt("Umidita"));
						r.add(r1);
					}
				}
				
				rs.close();
				st.close();
				conn.close();
			}
			
		
			
			
		}catch(SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		if(r==null) {
			return null;
		}
		
		return r;
	}

	public List<Citta> getUmiditaMediadelleCitta(int mese){
		List<Citta> citta = new ArrayList<Citta>();
		String data1 ="2013-"+mese+"-01";
		String data2;
		if(mese==1||mese==3||mese==5||mese==7||mese==8||mese==10||mese==12) {
			 data2 ="2013-"+mese+"-31";
		}else if(mese==2) {
			 data2 ="2013-"+mese+"-28";
		}else {
			 data2 ="2013-"+mese+"-30";
		}
		
		
		
		String sql = "SELECT s.Localita,s.Umidita, SUM(s.umidita) AS 'somma', COUNT(*) AS 'tot' "
				+ "FROM situazione s "
				+ "WHERE  (s.data BETWEEN ? AND ? ) "
				+ "GROUP BY s.Localita";
		
		try {
			Connection con = ConnectDB.getConnection();
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, data1);
			st.setString(2, data2);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				Citta c = new Citta(rs.getString("localita"));
				double somma = rs.getInt("somma");
				int n = rs.getInt("tot");
				c.setUmiditaMedia((double)(somma/n));
				citta.add(c);
			}
			rs.close();
			st.close();
			con.close();
			
		}catch(SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		return citta;
	}
	

	public List<Citta> getUmitaNelGiorno(int mese, int giorno){
		String data ="2013-"+mese+"-"+giorno;
		List<Citta> citta = new ArrayList<Citta>();
		String sql = "SELECT s.Localita,s.Umidita "
				+ "FROM situazione s "
				+ "WHERE  s.`Data`=? "
				+ "GROUP BY s.Localita";
		try {
			Connection con = ConnectDB.getConnection();
			PreparedStatement  st = con.prepareStatement(sql);
			st.setString(1,data);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				Citta c = new Citta(rs.getString("localita"));
				citta.add(c);
			}
			rs.close();
			st.close();
			con.close();
			
		} catch(SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		
		
		return citta;
		
		
	}
}
