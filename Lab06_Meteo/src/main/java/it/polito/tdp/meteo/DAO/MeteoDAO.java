package it.polito.tdp.meteo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

		String sql = "SELECT * "
				+ "FROM situazione s "
				+ "WHERE s.Localita=? AND s.DATA=?";
		String data = 2013+"-"+mese;
		List<Rilevamento> r=new ArrayList<Rilevamento>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, localita);
			st.setString(2, data);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Rilevamento r1 = new Rilevamento(rs.getString("Localita"),rs.getDate("Data"),rs.getInt("umidita"));
				r.add(r1);
			}
			
			rs.close();
			st.close();
			conn.close();
			
		}catch(SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		if(r==null) {
			return null;
		}
		
		return r;
	}


}
