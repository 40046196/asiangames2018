package com.asiangames2018.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import java.sql.Connection;
import java.sql.DriverManager;

/***********************************
 * @author Leo Sudarma
 * @Date March 13, 2012, 5:02 a.m Description: This is the Database Access
 *       Object (DAO) Utility. This function will give you the instance of the
 *       connection to the database. The location of the database is set in the
 *       properties folder, "properties\dao.properties" So, whenever you change
 *       your database into MySQL, Microsoft SQL, or JAVA DB, you no need to
 *       recompile your program, as long your database has the same table name
 *       and field name. :D
 * 
 *       http://www.baselogic.com/blog/solved-java-net-bindexception-address-use-connect-issue-windows/
 */

public class DAOUtil {

    /*****************************************************************
     * Loading the parameter from dao.properties files into class Properties.
     * 
     * @return Properties
     */
    public static Properties getJDBCProperties() {
	properties = new Properties();
	try {
	    // the folder following your working project..
	    properties.load(new FileInputStream("properties\\dao.properties"));
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return properties;
    }

    /**
     * Gets the connection attribute of the DAOUtil class
     *
     * @return The connection value
     */
    public static Connection getConnection() {
	Connection connection = null;
	try {
	    getJDBCProperties();
	    String className = properties.getProperty("classname");
	    String driverManager = properties.getProperty("drivermanager");

	    // Class.forName(className).newInstance();
	    connection = DriverManager.getConnection(driverManager);
	    connection.setAutoCommit(true);
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	return connection;
    }

    private static Properties properties = null;

}
