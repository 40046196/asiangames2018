package com.asiangames2018;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Iterator;
import org.junit.Test;

import com.asiangames2018.dao.AsianGamesDAO;
import com.asiangames2018.entity.Country;
import com.asiangames2018.entity.Sport;

public class TestSportDAO {

    @Test
    public void testListAllSports() {
	AsianGamesDAO dao = new AsianGamesDAO();
	Collection<Sport> sports = dao.listAllSports();
	assertNotNull(sports.size());
	for (Iterator<Sport> i = sports.iterator(); i.hasNext();) {
	    Sport s = i.next();
	    System.out.println(s.getSportId());
	}
    }
    
    @Test
    public void testIsSportExist() {
	// TODO
    }
    
    @Test
    public void testDeleteSport() {
	// TODO
    }
    
    @Test
    public void testUpdateSport() {
	// TODO
    }
    
}
