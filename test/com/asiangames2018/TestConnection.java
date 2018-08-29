package com.asiangames2018;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

import com.asiangames2018.util.DAOUtil;
import com.asiangames2018.util.GeneralLogging;

public class TestConnection extends DAOUtil {
    private Logger logger = GeneralLogging.getLogger();

    @Test
    public void test() {
	Connection connection = null;
	connection = super.getConnection();
	try {
	    // check if connection to MySQL DB is valid under 5 seconds
	    // try using invalid user name or password in dao.properties 
	    // this test will fail as a result 
	    assertEquals(true, connection.isValid(5));
	} catch (SQLException e) {
	    logger.log(Level.WARNING, TestConnection.class.getName() + ": invalid connection", e);
	}
    }
}
