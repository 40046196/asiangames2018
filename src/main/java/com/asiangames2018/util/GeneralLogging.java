package com.asiangames2018.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * The logging now based on daily.
 * The log is in directory log.
 * Example : the log format :  2018-01-01.txt  etc.
 * @author lion
 *
 */
public class GeneralLogging {
	
	/**
	 * Setup the Logger to folder  log\logding.txt
	 * @throws IOException
	 */
	  static public void setup() throws IOException {

		    // Get the global logger to configure it
		    logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		    Date date = Calendar.getInstance().getTime();
		    String strDate = sdf.format(date);
//		    strDate = LottoUtil.dateManipulation("", 0);
		

		    logger.setLevel(Level.INFO);
		    File file = new File("log\\a.txt");
		    if (!file.getParentFile().exists())  {
		    	file.mkdirs();
		    }
		    
		    fileTxt = new FileHandler("log\\" + strDate  + ".txt", true);
		    // Create txt Formatter
		    formatterTxt = new SimpleFormatter();
		    fileTxt.setFormatter(formatterTxt);
		    logger.addHandler(fileTxt);
		    
		    logger.info("START Logging");
	  }
	
	  /**
	   * Get the logger so it can be accessible in all modules
	   * @return
	   */
	  static public Logger getLogger() {
		  return logger;
	  }
	  
	  /**
	   * release log
	   */
	  static public void close() {
		  fileTxt.close();
	  }

	
	  private static Logger logger;
	  static private FileHandler fileTxt;
	  static private SimpleFormatter formatterTxt;	
	

}
