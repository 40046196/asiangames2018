package com.asiangames2018;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Iterator;
import org.junit.Test;

import com.asiangames2018.dao.AsianGamesDAO;
import com.asiangames2018.entity.Country;

public class TestCountryDAO {

    /**
     * To test insert, delete, and update country, you have to test them in
     * order of insert, update, and delete.
     */

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
    public void testUpdateCountry() {
	AsianGamesDAO dao = new AsianGamesDAO();
	String countryId = "CAN";
	String newCountryName = "Canadas";
	Country can = new Country(countryId, newCountryName);
	if (dao.isCountryExist(can)) {
	    dao.updateCountry(can);
	}
	dao.insertCountry(can);
	String countryName = "";
	Collection<Country> countries = dao.listAllCountries();
	for (Iterator<Country> i = countries.iterator(); i.hasNext();) {
	    Country c = i.next();
	    if (c.getCountryId().equals(countryId)) {
		countryName = c.getCountryName();
		break;
	    }
	}
	assertEquals(countryName, newCountryName);
    }

    @Test
    public void testDeleteCountry() {
	AsianGamesDAO dao = new AsianGamesDAO();
	Country can = new Country("CAN", "Canada");
	if (dao.isCountryExist(can)) {
	    dao.deleteCountry(can);
	    assertFalse(dao.isCountryExist(can));
	}
	dao.insertCountry(can);
	assertTrue(dao.isCountryExist(can));
    }

}
