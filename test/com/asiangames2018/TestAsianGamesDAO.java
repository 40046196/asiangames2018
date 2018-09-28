package com.asiangames2018;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

import com.asiangames2018.dao.AsianGamesDAO;
import com.asiangames2018.entity.Country;
import com.asiangames2018.util.GeneralLogging;

public class TestAsianGamesDAO {
    private Logger logger = GeneralLogging.getLogger();
    
    @Test
    public void testListAllCountries() {
	AsianGamesDAO dao = new AsianGamesDAO();
	Collection<Country> countries = dao.listAllCountries();
	assertNotNull(countries.size());
	for (Iterator<Country> i = countries.iterator(); i.hasNext();) {
	    Country c = i.next();
	    System.out.println(c.getCountryId());
	}
    }
    
    @Test
    public void testIsCountryExist() {
	AsianGamesDAO dao = new AsianGamesDAO();
	assertTrue(dao.isCountryExist(new Country("PHI", "Philippines")));
	assertFalse(dao.isCountryExist(new Country("XYZ", "Nowhere")));
    }
    
    @Test
    public void testInsertCountry() {
	AsianGamesDAO dao = new AsianGamesDAO();
	Country can = new Country("CAN", "Canada");
	dao.insertCountry(can);
	assertTrue(dao.isCountryExist(can));
    }
    
    @Test
    public void testDeleteCountry() {
	// TODO
    }
    
    @Test
    public void testUpdateCountry() {
	// TODO
    }
    
}
