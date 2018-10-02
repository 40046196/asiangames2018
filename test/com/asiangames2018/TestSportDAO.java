package com.asiangames2018;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Iterator;
import org.junit.Test;

import com.asiangames2018.dao.AsianGamesDAO;
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
	AsianGamesDAO dao = new AsianGamesDAO();
	assertTrue(dao.isSportExist(new Sport("BK", "Basketball")));
	assertFalse(dao.isSportExist(new Sport("AF", "American Football")));
    }

    @Test
    public void testInsertSport() {
	AsianGamesDAO dao = new AsianGamesDAO();
	Sport nfl = new Sport("AF", "American Football");
	if (!dao.isSportExist(nfl)) {
	    dao.insertSport(nfl);
	}
	assertTrue(dao.isSportExist(nfl));
	System.out.println(nfl.toString() + " inserted!");
    }

    @Test
    public void testDeleteSport() {
	AsianGamesDAO dao = new AsianGamesDAO();
	Sport s = new Sport("AF", "American Football");
	if (dao.isSportExist(s)) {
	    dao.deleteSport(s);
	    assertFalse(dao.isSportExist(s));
	}
	dao.insertSport(s);
	assertTrue(dao.isSportExist(s));
    }

    @Test
    public void testUpdateSport() {
	AsianGamesDAO dao = new AsianGamesDAO();
	String sportId = "AF";
	String newSportName = "Football";
	Sport nfl = new Sport(sportId, newSportName);
	if (dao.isSportExist(nfl)) {
	    dao.updateSport(nfl);
	}
	dao.insertSport(nfl);
	String sportName = "";
	Collection<Sport> sports = dao.listAllSports();
	for (Iterator<Sport> i = sports.iterator(); i.hasNext();) {
	    Sport s = i.next();
	    if (s.getSportId().equals(sportId)) {
		sportName = s.getSportName();
		break;
	    }
	}
	assertEquals(newSportName, sportName);
    }

}
