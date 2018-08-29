package com.asiangames2018;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

import com.asiangames2018.util.DAOUtil;
import com.asiangames2018.util.GeneralLogging;

import junit.framework.TestCase;

public class TestConnection extends TestCase {
    private Logger logger = GeneralLogging.getLogger();

    @Test
    public void testGetJDBCProperties() {
	Properties properties = null;
	properties = DAOUtil.getJDBCProperties();
	String className = properties.getProperty("classname");
        String driverManager = properties.getProperty("drivermanager");
	assertEquals("com.mysql.jdbc.Driver", className);
        assertNotNull(driverManager);
    }
    
    @Test
    public void testGetConnection() {
	Connection connection = null;
	connection = DAOUtil.getConnection();
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
