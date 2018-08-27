package com.asiangames2018.dao;

import com.asiangames2018.util.DAOUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.asiangames2018.entity.Country;
import com.asiangames2018.entity.Sport;
import com.asiangames2018.entity.Athlete;
import com.asiangames2018.util.GeneralLogging;

/**
 * This is the Data Access Object
 * 
 * @author lion
 *
 */
public class AsianGamesDAO extends DAOUtil {
	private static Logger logger = GeneralLogging.getLogger();

	
	/*****  COUNTRY TABLES   ***/
	/**
	 * Add Country Data
	 * @param country
	 */
	public void insertCountry(Country country) {
		Connection connection = null;     
	    PreparedStatement statement = null;
	    
	    String sql  = "Insert IGNORE into country(countryId, countryName, countryFlag) values(?,?,?)";
		try     {       
			connection = super.getConnection();  			
			statement = connection.prepareStatement(sql);      
			statement.setString(1, country.getCountryId());
			statement.setString(2, country.getCountryName());
			statement.setBlob(3, country.getCountryFlag());
			statement.executeUpdate();
		} catch (Exception e) {   
			logger.log(Level.SEVERE, "Excption in connection !!  " + sql,  e );
		} finally {   
	    	try {
	    		if (statement != null) {
	    			statement.close();
	    		}
	    	} catch (Exception e) {
	    		logger.log(Level.SEVERE, "Excption in connection !!  " + sql,  e );
	    	}
	    	try {
	    		if (connection != null)  connection.close();
	    	} catch (Exception e) {
	    		logger.log(Level.SEVERE, "Excption in connection !!  " + sql,  e );
	    	}
		}   	
	}
	
	/**
	 * Checking if a country exist in the table
	 * @param country
	 * @return
	 */
	public boolean isCountryExist(Country country) {
		Vector<Country> v = new Vector<Country>();
		boolean isCountryExist = false;
		String sql = "SELECT * FROM country WHERE countryid = ?";	
		Connection connection = null;
		PreparedStatement statement = null;
		try     {       
			connection = super.getConnection();       
			statement = connection.prepareStatement(sql);
			statement.setString(1, country.getCountryId());
			ResultSet resultSet = statement.executeQuery();   
            while (resultSet.next()) {   
            	isCountryExist = true;
            }   
		} catch (Exception e) {   
			logger.log(Level.SEVERE, "Excption in connection !!  " + sql,  e );
		} finally {   
			try {
				statement.close();   
			} catch (Exception e) {   
				logger.log(Level.SEVERE, "Error close statement !!  ",  e );
				e.printStackTrace();   
			}
			
			try {   
				connection.close();   
			} catch (Exception e) {   
				logger.log(Level.SEVERE, "Error close connectionDB !!  ",  e );
				e.printStackTrace();   
			}   
		}    
		return isCountryExist;
	}
	
	/**
	 * delete Country
	 * @param country
	 */
	public void deleteCountry(Country country) {
		Connection connection = null;     
	    PreparedStatement statement = null;
	    
	    String sql  = "DELETE FROM country WHERE countryId = ?";
		try     {       
			connection = super.getConnection();  			
			statement = connection.prepareStatement(sql);      
			statement.setString(1, country.getCountryId());
			statement.executeUpdate();
		} catch (Exception e) {   
			logger.log(Level.SEVERE, "Excption in connection !!  " + sql,  e );
		} finally {   
	    	try {
	    		if (statement != null) {
	    			statement.close();
	    		}
	    	} catch (Exception e) {
	    		logger.log(Level.SEVERE, "Excption in connection !!  " + sql,  e );
	    	}
	    	try {
	    		if (connection != null)  connection.close();
	    	} catch (Exception e) {
	    		logger.log(Level.SEVERE, "Excption in connection !!  " + sql,  e );
	    	}
		}   	
	}
	
	/*
	 * get all Country List
	 */
	public Collection<Country> listAllCountries() {
		Vector<Country> v = new Vector<Country>();
		String sql = "SELECT * FROM country ORDER BY countryName";	
		Connection connection = null;
		PreparedStatement statement = null;
		try     {       
			connection = super.getConnection();       
			statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();   
             while (resultSet.next()) {   
            	Country country = new Country();
            	country.setCountryId(resultSet.getString(1));
            	country.setCountryName(resultSet.getString(2));
            	country.setCountryFlag(resultSet.getBlob(3));
            	v.add(country);   
            }   
		} catch (Exception e) {   
			logger.log(Level.SEVERE, "Excption in connection !!  " + sql,  e );
		} finally {   
			try {
				statement.close();   
			} catch (Exception e) {   
				logger.log(Level.SEVERE, "Error close statement !!  ",  e );
				e.printStackTrace();   
			}
			
			try {   
				connection.close();   
			} catch (Exception e) {   
				logger.log(Level.SEVERE, "Error close connectionDB !!  ",  e );
				e.printStackTrace();   
			}   
		}    
		return v;
	}
	
	/**
	 * Update Country
	 * @param country
	 */
	public void updateCountry(Country country) {
		Connection connection = null;     
	    PreparedStatement statement = null;
	    String updateString = "UPDATE country  SET countryName = ?, countryFlag = ?  WHERE countryId = ?";
		    
		try {
			connection = super.getConnection();       	    	
		    statement = connection.prepareStatement(updateString);
		    statement.setString(1, country.getCountryName());
		    statement.setString(2,  country.getCountryId());
		    statement.setBlob(3, country.getCountryFlag());
		    statement.executeUpdate();
		} catch (Exception e ) {
		   	e.printStackTrace();
	    } finally {
	    	try {
	    		if (statement != null) {
	    			statement.close();
	    		}
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    	try {
	    		if (connection != null)  connection.close();
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    }		
	}
	
	
	/*****  SPORTS TABLES   ***/
	/**
	 * Add Sport Data
	 * @param sport
	 */
	public void insertSport(Sport sport) {
		Connection connection = null;     
	    PreparedStatement statement = null;
	    
	    String sql  = "Insert IGNORE into sport(sportId, sportName, sportIcon, sportImage) values(?,?,?,?)";
		try     {       
			connection = super.getConnection();  			
			statement = connection.prepareStatement(sql);      
			statement.setString(1, sport.getSportId());
			statement.setString(2, sport.getSportName());
			statement.setBlob(3, sport.getSportIcon());
			statement.setBlob(4, sport.getSportImage());
			statement.executeUpdate();
		} catch (Exception e) {   
			logger.log(Level.SEVERE, "Excption in connection !!  " + sql,  e );
		} finally {   
	    	try {
	    		if (statement != null) {
	    			statement.close();
	    		}
	    	} catch (Exception e) {
	    		logger.log(Level.SEVERE, "Excption in connection !!  " + sql,  e );
	    	}
	    	try {
	    		if (connection != null)  connection.close();
	    	} catch (Exception e) {
	    		logger.log(Level.SEVERE, "Excption in connection !!  " + sql,  e );
	    	}
		}   	
	}
	
	/**
	 * Checking if a sport exist in the table
	 * @param sport
	 * @return
	 */
	public boolean isSportExist(Sport sport) {
		Vector<Sport> v = new Vector<Sport>();
		boolean isSportExist = false;
		String sql = "SELECT * FROM sport WHERE countryid = ?";	
		Connection connection = null;
		PreparedStatement statement = null;
		try     {       
			connection = super.getConnection();       
			statement = connection.prepareStatement(sql);
			statement.setString(1, sport.getSportId());
			ResultSet resultSet = statement.executeQuery();   
            while (resultSet.next()) {   
            	isSportExist = true;
            }   
		} catch (Exception e) {   
			logger.log(Level.SEVERE, "Excption in connection !!  " + sql,  e );
		} finally {   
			try {
				statement.close();   
			} catch (Exception e) {   
				logger.log(Level.SEVERE, "Error close statement !!  ",  e );
				e.printStackTrace();   
			}
			
			try {   
				connection.close();   
			} catch (Exception e) {   
				logger.log(Level.SEVERE, "Error close connectionDB !!  ",  e );
				e.printStackTrace();   
			}   
		}    
		return isSportExist;
	}
	
	/**
	 * delete Sport
	 * @param sport
	 */
	public void deleteSport(Sport sport) {
		Connection connection = null;     
	    PreparedStatement statement = null;
	    
	    String sql  = "DELETE FROM sport WHERE sportId = ?";
		try     {       
			connection = super.getConnection();  			
			statement = connection.prepareStatement(sql);      
			statement.setString(1, sport.getSportId());
			statement.executeUpdate();
		} catch (Exception e) {   
			logger.log(Level.SEVERE, "Excption in connection !!  " + sql,  e );
		} finally {   
	    	try {
	    		if (statement != null) {
	    			statement.close();
	    		}
	    	} catch (Exception e) {
	    		logger.log(Level.SEVERE, "Excption in connection !!  " + sql,  e );
	    	}
	    	try {
	    		if (connection != null)  connection.close();
	    	} catch (Exception e) {
	    		logger.log(Level.SEVERE, "Excption in connection !!  " + sql,  e );
	    	}
		}   	
	}
	
	/*
	 * get all Sport List
	 */
	public Collection<Sport> listAllSports() {
		Vector<Sport> v = new Vector<Sport>();
		String sql = "SELECT * FROM sport ORDER BY sportName";	
		Connection connection = null;
		PreparedStatement statement = null;
		try     {       
			connection = super.getConnection();       
			statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();   
             while (resultSet.next()) {   
            	Sport sport = new Sport();
            	sport.setSportId(resultSet.getString(1));
            	sport.setSportName(resultSet.getString(2));
            	sport.setSportIcon(resultSet.getBlob(3));
            	sport.setSportImage(resultSet.getBlob(4));
            	v.add(sport);   
            }   
		} catch (Exception e) {   
			logger.log(Level.SEVERE, "Excption in connection !!  " + sql,  e );
		} finally {   
			try {
				statement.close();   
			} catch (Exception e) {   
				logger.log(Level.SEVERE, "Error close statement !!  ",  e );
				e.printStackTrace();   
			}
			
			try {   
				connection.close();   
			} catch (Exception e) {   
				logger.log(Level.SEVERE, "Error close connectionDB !!  ",  e );
				e.printStackTrace();   
			}   
		}    
		return v;
	}
	
	/**
	 * Update Sport
	 * @param Sport
	 */
	public void updateSport(Sport sport) {
		Connection connection = null;     
	    PreparedStatement statement = null;
	    String updateString = "UPDATE sport  SET sportName = ?, sportIcon = ?, sportImage=?  WHERE countryId = ?";
		    
		try {
			connection = super.getConnection();       	    	
		    statement = connection.prepareStatement(updateString);
		    statement.setString(1, sport.getSportName());
		    statement.setBlob(2,  sport.getSportIcon());
		    statement.setBlob(3, sport.getSportImage());
		    statement.executeUpdate();
		} catch (Exception e ) {
		   	e.printStackTrace();
	    } finally {
	    	try {
	    		if (statement != null) {
	    			statement.close();
	    		}
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    	try {
	    		if (connection != null)  connection.close();
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    }		
	}	
	
	/*****  ATHLETES TABLES   ***/
	/**
	 * Add Athlete Data
	 * @param Athlete
	 */
	public void insertAthlete(Athlete athlete) {
		Connection connection = null;     
	    PreparedStatement statement = null;
	    
	    String sql  = "Insert IGNORE into athlete(athleteId, athleteName, familyName, birthdate, countryId, sportId, "
	    		+ "height, weight, beginning, coach, memorable, influence, nickname, language, hobbies, photo) "
	    		+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try  {       
			connection = super.getConnection();  			
			statement = connection.prepareStatement(sql);      
			statement.setString(1, athlete.getAthleteId());
			statement.setString(2, athlete.getAthleteName());
			statement.setString(3,  athlete.getFamilyName());
			statement.setString(4,  athlete.getBirthdate());
			statement.setString(5,  athlete.getCountryId());
			statement.setString(6,  athlete.getSportId());
			statement.setInt(7,  athlete.getHeight());
			statement.setInt(8,  athlete.getWeight());
			statement.setString(9,  athlete.getBeginning());
			statement.setString(10,  athlete.getCoach());
			statement.setString(11,  athlete.getMemorable());
			statement.setString(12,  athlete.getInfluence());
			statement.setString(13,  athlete.getNickname());
			statement.setString(14,  athlete.getLanguage());
			statement.setString(15,  athlete.getHobbies());
			statement.setBlob(16, athlete.getPhoto());
			statement.executeUpdate();
		} catch (Exception e) {   
			logger.log(Level.SEVERE, "Excption in connection !!  " + sql,  e );
		} finally {   
	    	try {
	    		if (statement != null) {
	    			statement.close();
	    		}
	    	} catch (Exception e) {
	    		logger.log(Level.SEVERE, "Excption in connection !!  " + sql,  e );
	    	}
	    	try {
	    		if (connection != null)  connection.close();
	    	} catch (Exception e) {
	    		logger.log(Level.SEVERE, "Excption in connection !!  " + sql,  e );
	    	}
		}   	
	}
	
	/**
	 * Checking if a Athlete exist in the table
	 * @param Athlete
	 * @return
	 */
	public boolean isAthleteExist(Athlete athlete) {
		Vector<Athlete> v = new Vector<Athlete>();
		boolean isAthleteExist = false;
		String sql = "SELECT * FROM athlete WHERE athleteId = ?";	
		Connection connection = null;
		PreparedStatement statement = null;
		try     {       
			connection = super.getConnection();       
			statement = connection.prepareStatement(sql);
			statement.setString(1, athlete.getAthleteId());
			ResultSet resultSet = statement.executeQuery();   
            while (resultSet.next()) {   
            	isAthleteExist = true;
            }   
		} catch (Exception e) {   
			logger.log(Level.SEVERE, "Excption in connection !!  " + sql,  e );
		} finally {   
			try {
				statement.close();   
			} catch (Exception e) {   
				logger.log(Level.SEVERE, "Error close statement !!  ",  e );
				e.printStackTrace();   
			}
			
			try {   
				connection.close();   
			} catch (Exception e) {   
				logger.log(Level.SEVERE, "Error close connectionDB !!  ",  e );
				e.printStackTrace();   
			}   
		}    
		return isAthleteExist;
	}
	
	/**
	 * delete Athlete
	 * @param Athlete
	 */
	public void deleteAthlete(Athlete athlete) {
		Connection connection = null;     
	    PreparedStatement statement = null;
	    
	    String sql  = "DELETE FROM athlete WHERE athleteId = ?";
		try     {       
			connection = super.getConnection();  			
			statement = connection.prepareStatement(sql);      
			statement.setString(1, athlete.getAthleteId());
			statement.executeUpdate();
		} catch (Exception e) {   
			logger.log(Level.SEVERE, "Excption in connection !!  " + sql,  e );
		} finally {   
	    	try {
	    		if (statement != null) {
	    			statement.close();
	    		}
	    	} catch (Exception e) {
	    		logger.log(Level.SEVERE, "Excption in connection !!  " + sql,  e );
	    	}
	    	try {
	    		if (connection != null)  connection.close();
	    	} catch (Exception e) {
	    		logger.log(Level.SEVERE, "Excption in connection !!  " + sql,  e );
	    	}
		}   	
	}
	
	/*
	 * get all Athlete List
	 */
	public Collection<Athlete> listAllAthletes() {
		Vector<Athlete> v = new Vector<Athlete>();
		String sql = "SELECT * FROM athlete ORDER BY familyName";	
		Connection connection = null;
		PreparedStatement statement = null;
		try     {       
			connection = super.getConnection();       
			statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();   
             while (resultSet.next()) {   
            	Athlete athlete = new Athlete();
            	athlete.setAthleteId(resultSet.getString(1));
            	athlete.setAthleteName(resultSet.getString(2));
            	athlete.setFamilyName(resultSet.getString(3));
            	athlete.setBirthdate(resultSet.getString(4));
            	athlete.setCountryId(resultSet.getString(5));
            	athlete.setSportId(resultSet.getString(6));
            	athlete.setHeight(resultSet.getInt(7));
            	athlete.setWeight(resultSet.getInt(8));
            	athlete.setBeginning(resultSet.getString(9));
            	athlete.setCoach(resultSet.getString(10));
            	athlete.setMemorable(resultSet.getString(11));
            	athlete.setInfluence(resultSet.getString(12));
            	athlete.setNickname(resultSet.getString(13));
            	athlete.setLanguage(resultSet.getString(14));
            	athlete.setHobbies(resultSet.getString(15));
            	athlete.setPhoto(resultSet.getBlob(16));
            	v.add(athlete);   
            }   
		} catch (Exception e) {   
			logger.log(Level.SEVERE, "Excption in connection !!  " + sql,  e );
		} finally {   
			try {
				statement.close();   
			} catch (Exception e) {   
				logger.log(Level.SEVERE, "Error close statement !!  ",  e );
				e.printStackTrace();   
			}
			
			try {   
				connection.close();   
			} catch (Exception e) {   
				logger.log(Level.SEVERE, "Error close connectionDB !!  ",  e );
				e.printStackTrace();   
			}   
		}    
		return v;
	}
	
	/**
	 * to do next
	 * Update Sport
	 * @param Sport
	 */
	public void updateAthlete(Sport sport) {
		Connection connection = null;     
	    PreparedStatement statement = null;
	    String updateString = "UPDATE sport  SET sportName = ?, sportIcon = ?, sportImage=?  WHERE countryId = ?";
		    
		try {
			connection = super.getConnection();       	    	
		    statement = connection.prepareStatement(updateString);
		    statement.setString(1, sport.getSportName());
		    statement.setBlob(2,  sport.getSportIcon());
		    statement.setBlob(3, sport.getSportImage());
		    statement.executeUpdate();
		} catch (Exception e ) {
		   	e.printStackTrace();
	    } finally {
	    	try {
	    		if (statement != null) {
	    			statement.close();
	    		}
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    	try {
	    		if (connection != null)  connection.close();
	    	} catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    }		
	}		
	

}
