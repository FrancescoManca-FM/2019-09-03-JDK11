package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.food.model.Adiacenza;
import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDao {
	public List<Food> listAllFoods(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"),
							res.getString("display_name")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Portion> listAllPortions(){
		String sql = "SELECT * FROM `portion`" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Portion> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Portion(res.getInt("portion_id"),
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"), 
							res.getDouble("calories"),
							res.getDouble("saturated_fats"),
							res.getInt("food_code")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<String> getPortionDisplayName(int calorie){
		String sql = "SELECT p.portion_display_name as nomePorzione, COUNT(DISTINCT p.portion_id) " + 
				"	FROM `portion` AS p " + 
				"	WHERE p.calories<? " + 
				"	GROUP BY p.portion_display_name " + 
				"	HAVING COUNT(distinct p.portion_id)>0";
		List<String> risultato = new ArrayList<>();
		
		try {
			
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, calorie);
			
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				risultato.add(rs.getString("nomePorzione"));
			}
			conn.close();
			return risultato;
		}catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore nel caricamento dati dal database");
		}
	}
	
	public List<Adiacenza> getAdiacenze(){
		String sql = "SELECT distinct p1.portion_display_name AS nome1, p2.portion_display_name as nome2, COUNT(*) AS peso " + 
				"FROM `portion` AS p1, `portion` AS p2 " + 
				"WHERE p1.portion_display_name!=p2.portion_display_name AND " + 
				"p1.food_code=p2.food_code AND p1.portion_display_name>p2.portion_display_name " + 
				"GROUP BY p1.portion_display_name, p2.portion_display_name";
		List<Adiacenza> risultato = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Adiacenza a = new Adiacenza(rs.getString("nome1"), rs.getString("nome2"), rs.getInt("peso"));
				risultato.add(a);
			}
			
			conn.close();
			return risultato;
							
		}catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("ERRORE NEL CARICAMENTO DATI DAL DATABASE");
		}
	}

}
