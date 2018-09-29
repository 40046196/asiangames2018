package com.asiangames2018.dao;

import com.asiangames2018.util.DAOUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.asiangames2018.entity.Country;
import com.asiangames2018.entity.Sport;
import com.asiangames2018.entity.TotalMedals;
import com.asiangames2018.entity.Athlete;
import com.asiangames2018.entity.AthleteBiography;
import com.asiangames2018.entity.AthleteHighlight;
import com.asiangames2018.entity.AthleteSocial;
import com.asiangames2018.util.GeneralLogging;

/**
 * This is the Data Access Object
 * 
 * @author lion
 *
 */
public class AsianGamesDAO extends DAOUtil {
    private static Logger logger = GeneralLogging.getLogger();

    /***** COUNTRY TABLES ***/
    /**
     * Add Country Data
     * 
     * @param country
     */
    public void insertCountry(Country country) {
	Connection connection = null;
	PreparedStatement statement = null;
	String sql = "Insert IGNORE into country(countryId, countryName, countryFlag) values(?,?,?)";
	try {
	    connection = super.getConnection();
	    statement = connection.prepareStatement(sql);
	    statement.setString(1, country.getCountryId());
	    statement.setString(2, country.getCountryName());
	    if (country.getCountryFlag() != null) {
		statement.setBlob(3, country.getCountryFlag());
	    } else {
		statement.setNull(3, java.sql.Types.BLOB);
	    }
	    statement.executeUpdate();
	} catch (Exception e) {
	    logger.log(Level.SEVERE, "Excption in connection !!  " + sql, e);
	} finally {
	    try {
		if (statement != null) {
		    statement.close();
		}
	    } catch (Exception e) {
		logger.log(Level.SEVERE, "Excption in connection !!  " + sql, e);
	    }
	    try {
		if (connection != null)
		    connection.close();
	    } catch (Exception e) {
		logger.log(Level.SEVERE, "Excption in connection !!  " + sql, e);
	    }
	}
    }

    /**
     * Version 2 of isCountryExist. Code reused is the best way to go. NO need
     * for another query statement because it make use of existing
     * listAllCountries().
     * 
     * Update: since country id is unique (and cannot be changed), check only
     * the country id
     * 
     * @param countryToSearch
     * @return true if found; otherwise return false
     */
    public boolean isCountryExist(Country countryToSearch) {
	Collection<Country> countryCollection = listAllCountries();
	boolean condition = false; 
	for (Iterator<Country> i = countryCollection.iterator(); i.hasNext();) {
	    Country c = i.next();
	    String countryToFindID = countryToSearch.getCountryId();
	    if (countryToFindID.equals(c.getCountryId())) {
		condition = true;
		break;
	    }
	}
	return condition;
    }

    /**
     * delete Country
     * 
     * @param country
     */
    public void deleteCountry(Country country) {
	// make sure country you're deleting exists
	if (!isCountryExist(country)) {
	    logger.log(Level.WARNING, "This country does not exists!", country);
	    return;
	}
	Connection connection = null;
	PreparedStatement statement = null;
	String sql = "DELETE FROM country WHERE countryId = ?";
	try {
	    connection = super.getConnection();
	    statement = connection.prepareStatement(sql);
	    statement.setString(1, country.getCountryId());
	    statement.executeUpdate();
	} catch (Exception e) {
	    logger.log(Level.SEVERE, "Excption in connection !!  " + sql, e);
	} finally {
	    try {
		if (statement != null) {
		    statement.close();
		}
	    } catch (Exception e) {
		logger.log(Level.SEVERE, "Excption in connection !!  " + sql, e);
	    }
	    try {
		if (connection != null)
		    connection.close();
	    } catch (Exception e) {
		logger.log(Level.SEVERE, "Excption in connection !!  " + sql, e);
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
	try {
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
	    logger.log(Level.SEVERE, "Excption in connection !!  " + sql, e);
	} finally {
	    try {
		statement.close();
	    } catch (Exception e) {
		logger.log(Level.SEVERE, "Error close statement !!  ", e);
		e.printStackTrace();
	    }
	    try {
		connection.close();
	    } catch (Exception e) {
		logger.log(Level.SEVERE, "Error close connectionDB !!  ", e);
		e.printStackTrace();
	    }
	}
	return v;
    }

    /**
     * Update Country
     * 
     * @param country
     */
    public void updateCountry(Country country) {
	if (isCountryExist(country)) {
	    Connection connection = null;
	    PreparedStatement statement = null;
	    String updateString = "UPDATE country  SET countryName = ?, countryFlag = ?  WHERE countryId = ?";
	    try {
		connection = super.getConnection();
		statement = connection.prepareStatement(updateString);
		statement.setString(1, country.getCountryName());
		if (country.getCountryFlag() == null) {
		    statement.setNull(2, java.sql.Types.BLOB);
		} else {
		    statement.setBlob(2, country.getCountryFlag());
		}
		statement.setString(3, country.getCountryId());
		statement.executeUpdate();
	    } catch (Exception e) {
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
		    if (connection != null)
			connection.close();
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	} else {
	    logger.log(Level.WARNING, "Can't update country that does not exist!");
	}
    }

    /***** SPORTS TABLES ***/
    /**
     * Add Sport Data
     * 
     * @param sport
     */
    public void insertSport(Sport sport) {
	Connection connection = null;
	PreparedStatement statement = null;

	String sql = "Insert IGNORE into sport(sportId, sportName, sportIcon, sportImage) values(?,?,?,?)";
	try {
	    connection = super.getConnection();
	    statement = connection.prepareStatement(sql);
	    statement.setString(1, sport.getSportId());
	    statement.setString(2, sport.getSportName());
	    statement.setBlob(3, sport.getSportIcon());
	    statement.setBlob(4, sport.getSportImage());
	    statement.executeUpdate();
	} catch (Exception e) {
	    logger.log(Level.SEVERE, "Excption in connection !!  " + sql, e);
	} finally {
	    try {
		if (statement != null) {
		    statement.close();
		}
	    } catch (Exception e) {
		logger.log(Level.SEVERE, "Excption in connection !!  " + sql, e);
	    }
	    try {
		if (connection != null)
		    connection.close();
	    } catch (Exception e) {
		logger.log(Level.SEVERE, "Excption in connection !!  " + sql, e);
	    }
	}
    }

    /**
     * Checking if a sport exist in the table
     * 
     * @param sportToFind
     * @return
     */
    public boolean isSportExist2(Sport sportToFind) {
	Collection<Sport> sportCollection = listAllSports();
	boolean condition = false;
	for (int i = 0; i < sportCollection.size(); i++) {
	    if (sportCollection.contains(sportToFind)) {
		condition = true;
		break;
	    }
	}
	return condition;
    }

    /**
     * delete Sport
     * 
     * @param sport
     */
    public void deleteSport(Sport sport) {
	if (!isSportExist2(sport)) {
	    logger.log(Level.WARNING, "This sport does not exists!", sport);
	    return;
	}
	Connection connection = null;
	PreparedStatement statement = null;
	String sql = "DELETE FROM sport WHERE sportId = ?";
	try {
	    connection = super.getConnection();
	    statement = connection.prepareStatement(sql);
	    statement.setString(1, sport.getSportId());
	    statement.executeUpdate();
	} catch (Exception e) {
	    logger.log(Level.SEVERE, "Excption in connection !!  " + sql, e);
	} finally {
	    try {
		if (statement != null) {
		    statement.close();
		}
	    } catch (Exception e) {
		logger.log(Level.SEVERE, "Excption in connection !!  " + sql, e);
	    }
	    try {
		if (connection != null)
		    connection.close();
	    } catch (Exception e) {
		logger.log(Level.SEVERE, "Excption in connection !!  " + sql, e);
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
	try {
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
	    logger.log(Level.SEVERE, "Excption in connection !!  " + sql, e);
	} finally {
	    try {
		statement.close();
	    } catch (Exception e) {
		logger.log(Level.SEVERE, "Error close statement !!  ", e);
		e.printStackTrace();
	    }
	    try {
		connection.close();
	    } catch (Exception e) {
		logger.log(Level.SEVERE, "Error close connectionDB !!  ", e);
		e.printStackTrace();
	    }
	}
	return v;
    }

    /**
     * Update Sport
     * 
     * @param Sport
     */
    public void updateSport(Sport sport) {
	if (isSportExist2(sport)) {
	    Connection connection = null;
	    PreparedStatement statement = null;
	    String updateString = "UPDATE sport  SET sportName = ?, sportIcon = ?, sportImage=?  WHERE countryId = ?";
	    try {
		connection = super.getConnection();
		statement = connection.prepareStatement(updateString);
		statement.setString(1, sport.getSportName());
		statement.setBlob(2, sport.getSportIcon());
		statement.setBlob(3, sport.getSportImage());
		statement.executeUpdate();
	    } catch (Exception e) {
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
		    if (connection != null)
			connection.close();
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	} else {
	    logger.log(Level.WARNING, "Can't update sport that does not exist!");
	}
    }

    /***** ATHLETES TABLES ***/
    /**
     * Normally when playing with store procedure (SP) the format is : ? =
     * PackageAthlete.InsertAthlete(?,?,?,?....); if the return value is
     * successful then you can continue with the next procedure or use the
     * return value for your next input in the apps.
     * 
     * In this apps, we just show how to insert into more than one tables at
     * once, SP will handle whether they have to insert or update..
     * 
     * Add Athlete Data
     * 
     * @param Athlete
     */
    public void insertAthlete(Athlete athlete) {
	Connection connection = null;
	CallableStatement statement = null;
	TotalMedals totalMedals = athlete.getMedals();
	AthleteBiography biography = athlete.getBio();
	Collection socials = biography.getSocialMedia();
	Collection colHighlight = biography.getHighlight();

	String sql = "CALL insertAthlete(?,?,?,?,?,?,?,?,?,?,?," // athlete
		+ "?,?,?," // totalMedal [gold, silver, bronze
		+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; // biography withouth
							// highlights and
							// socialmedias

	try {
	    connection = super.getConnection();
	    // statement = connection.prepareStatement(sql);
	    statement = connection.prepareCall(sql);

	    // athlete main data
	    statement.setString(1, athlete.getAthleteId());
	    statement.setString(2, athlete.getAthleteName());
	    statement.setString(3, athlete.getFamilyName());
	    statement.setString(4, athlete.getBirthdate());
	    statement.setString(5, athlete.getBirthCity());
	    statement.setString(6, athlete.getBirthCountry());
	    statement.setString(7, athlete.getCountryId());
	    statement.setString(8, athlete.getSportId());
	    statement.setInt(9, athlete.getHeight());
	    statement.setInt(10, athlete.getWeight());
	    statement.setBlob(11, athlete.getPhoto());

	    // athlete medals
	    statement.setInt(12, totalMedals.getGold());
	    statement.setInt(13, totalMedals.getSilver());
	    statement.setInt(14, totalMedals.getBronze());

	    // athlete biography there are 17 items
	    if (biography != null) {
		statement.setString(15, biography.getBeginning());
		statement.setString(16, biography.getDebut());
		statement.setString(17, biography.getReason());
		statement.setString(18, biography.getCoach());
		statement.setString(19, biography.getTraining());
		statement.setString(20, biography.getAmbition());
		statement.setString(21, biography.getAwards());
		statement.setString(22, biography.getHero());
		statement.setString(23, biography.getMemorable());
		statement.setString(24, biography.getInfluence());
		statement.setString(25, biography.getNickname());
		statement.setString(26, biography.getRelatives());
		statement.setString(27, biography.getInjuries());
		statement.setString(28, biography.getEducation());
		statement.setString(29, biography.getLanguage());
		statement.setString(30, biography.getHobbies());
		statement.setString(31, biography.getAdditionalInformation());
	    } else {
		statement.setNull(15, java.sql.Types.NULL);
		statement.setNull(16, java.sql.Types.NULL);
		statement.setNull(17, java.sql.Types.NULL);
		statement.setNull(18, java.sql.Types.NULL);
		statement.setNull(19, java.sql.Types.NULL);
		statement.setNull(20, java.sql.Types.NULL);
		statement.setNull(21, java.sql.Types.NULL);
		statement.setNull(22, java.sql.Types.NULL);
		statement.setNull(23, java.sql.Types.NULL);
		statement.setNull(24, java.sql.Types.NULL);
		statement.setNull(25, java.sql.Types.NULL);
		statement.setNull(26, java.sql.Types.NULL);
		statement.setNull(27, java.sql.Types.NULL);
		statement.setNull(28, java.sql.Types.NULL);
		statement.setNull(29, java.sql.Types.NULL);
		statement.setNull(30, java.sql.Types.NULL);
		statement.setNull(31, java.sql.Types.NULL);
	    }

	    // not comfortable with this type, because you will open more than
	    // one connection SQL
	    // if this is transactional avoid this kind of connection.. or
	    // database connection could be hang
	    // once too many connection opened and unclosed.. --> RESET database
	    // server..
	    // just fine for this stand alone application..
	    if (socials != null) {
		Iterator<AthleteSocial> it = socials.iterator();
		while (it.hasNext()) {
		    AthleteSocial social = it.next();
		    this.insertAthleteSocialMedia(social);
		}
	    }
	    if (colHighlight != null) {
		Iterator<AthleteHighlight> it = colHighlight.iterator();
		while (it.hasNext()) {
		    AthleteHighlight highlight = it.next();
		    this.insertAthleteHighlight(highlight);
		}
	    }

	    statement.executeUpdate();
	} catch (Exception e) {
	    logger.log(Level.SEVERE, "Excption in connection !!  " + sql, e);
	    logger.info("Error for this message " + athlete.toString());
	} finally {
	    try {
		if (statement != null) {
		    statement.close();
		}
	    } catch (Exception e) {
		logger.log(Level.SEVERE, "Excption in connection !!  " + sql, e);
	    }
	    try {
		if (connection != null)
		    connection.close();
	    } catch (Exception e) {
		logger.log(Level.SEVERE, "Excption in connection !!  " + sql, e);
	    }
	}
    }

    private void insertAthleteHighlight(AthleteHighlight highlight) {
	String sql = "INSERT IGNORE INTO athletehighlight values (?,?,?,?,?,?,?)";
	Connection connection = null;
	PreparedStatement statement = null;
	try {
	    connection = super.getConnection();
	    statement = connection.prepareStatement(sql);
	    statement.setString(1, highlight.getAthleteId());
	    statement.setString(2, highlight.getEventName());
	    statement.setString(3, highlight.getRank());
	    statement.setString(4, highlight.getSportCategory());
	    statement.setString(5, highlight.getYear());
	    statement.setString(6, highlight.getLocation());
	    statement.setString(7, highlight.getBestScoreTime());
	    statement.executeUpdate();
	} catch (Exception e) {
	    logger.log(Level.SEVERE, "Excption in connection !!  " + sql, e);
	} finally {
	    try {
		statement.close();
	    } catch (Exception e) {
		logger.log(Level.SEVERE, "Error close statement !!  ", e);
		e.printStackTrace();
	    }
	    try {
		connection.close();
	    } catch (Exception e) {
		logger.log(Level.SEVERE, "Error close connectionDB !!  ", e);
		e.printStackTrace();
	    }
	}
    }

    /**
     * insert athlete social media account ex: fB, instagram, telegram, Whatsup,
     * etc..
     * 
     * @param social
     */
    private void insertAthleteSocialMedia(AthleteSocial social) {
	String sql = "INSERT IGNORE INTO athletesocial values (?,?)";
	Connection connection = null;
	PreparedStatement statement = null;
	try {
	    connection = super.getConnection();
	    statement = connection.prepareStatement(sql);
	    statement.setString(1, social.getAthleteId());
	    statement.setString(2, social.getSocialAccount());
	    statement.executeUpdate();
	} catch (Exception e) {
	    logger.log(Level.SEVERE, "Excption in connection !!  " + sql, e);
	} finally {
	    try {
		statement.close();
	    } catch (Exception e) {
		logger.log(Level.SEVERE, "Error close statement !!  ", e);
		e.printStackTrace();
	    }
	    try {
		connection.close();
	    } catch (Exception e) {
		logger.log(Level.SEVERE, "Error close connectionDB !!  ", e);
		e.printStackTrace();
	    }
	}
    }

    /**
     * Checking if a Athlete exist in the table
     * 
     * @param athleteToFind
     * @return
     */
    public boolean isAthleteExist2(Athlete athleteToFind) {
	Collection<Athlete> athleteCollection = listAllAthletes();
	boolean condition = false;
	for (int i = 0; i < athleteCollection.size(); i++) {
	    if (athleteCollection.contains(athleteToFind)) {
		condition = true;
		break;
	    }
	}
	return condition;
    }

    /**
     * delete Athlete
     * 
     * @param Athlete
     */
    public void deleteAthlete(Athlete athlete) {
	if (!isAthleteExist2(athlete)) {
	    logger.log(Level.WARNING, "This athlete does not exists!");
	    return;
	}
	Connection connection = null;
	PreparedStatement statement = null;
	String sql = "DELETE FROM athlete WHERE athleteId = ?";
	try {
	    connection = super.getConnection();
	    statement = connection.prepareStatement(sql);
	    statement.setString(1, athlete.getAthleteId());
	    statement.executeUpdate();
	} catch (Exception e) {
	    logger.log(Level.SEVERE, "Excption in connection !!  " + sql, e);
	} finally {
	    try {
		if (statement != null) {
		    statement.close();
		}
	    } catch (Exception e) {
		logger.log(Level.SEVERE, "Excption in connection !!  " + sql, e);
	    }
	    try {
		if (connection != null)
		    connection.close();
	    } catch (Exception e) {
		logger.log(Level.SEVERE, "Excption in connection !!  " + sql, e);
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
	try {
	    connection = super.getConnection();
	    statement = connection.prepareStatement(sql);
	    ResultSet resultSet = statement.executeQuery();
	    while (resultSet.next()) {
		Athlete athlete = new Athlete();
		athlete.setAthleteId(resultSet.getString(1));
		athlete.setAthleteName(resultSet.getString(2));
		athlete.setFamilyName(resultSet.getString(3));
		athlete.setBirthdate(resultSet.getString(4));
		athlete.setBirthCity(resultSet.getString(5));
		athlete.setBirthCountry(resultSet.getString(6));
		athlete.setCountryId(resultSet.getString(7));
		athlete.setSportId(resultSet.getString(8));
		athlete.setHeight(resultSet.getInt(9));
		athlete.setWeight(resultSet.getInt(10));
		athlete.setPhoto(resultSet.getBlob(11));
		v.add(athlete);
	    }
	} catch (Exception e) {
	    logger.log(Level.SEVERE, "Excption in connection !!  " + sql, e);
	} finally {
	    try {
		statement.close();
	    } catch (Exception e) {
		logger.log(Level.SEVERE, "Error close statement !!  ", e);
		e.printStackTrace();
	    }

	    try {
		connection.close();
	    } catch (Exception e) {
		logger.log(Level.SEVERE, "Error close connectionDB !!  ", e);
		e.printStackTrace();
	    }
	}
	return v;
    }

    /**
     * to do next Update Sport
     * 
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
	    statement.setBlob(2, sport.getSportIcon());
	    statement.setBlob(3, sport.getSportImage());
	    statement.executeUpdate();
	} catch (Exception e) {
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
		if (connection != null)
		    connection.close();
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
    }

}
