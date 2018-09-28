package com.asiangames2018;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Iterator;
import org.junit.Test;

import com.asiangames2018.dao.AsianGamesDAO;
import com.asiangames2018.entity.Country;

public class TestAsianGamesDAO {
    
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
    
}
