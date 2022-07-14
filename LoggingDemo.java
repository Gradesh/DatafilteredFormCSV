import java.util.*;
import java.io.*;
import java.sql.SQLException;

import org.apache.log4j.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;



public class LoggingDemo {

	public static Logger logger= Logger.getLogger(LoggingDemo.class.getName());
	public static void main(String[] args) throws IOException,SQLException
	{
		BasicConfigurator.configure();
		logger.setLevel(Level.DEBUG);
		/*logger.setAdditivity(false);
		logger.debug("Debug Info");
		logger.info("Info Log");
		logger.warn("Warn Log ");
		System.out.println("Logging Log4j Demo");*/
		logger.debug("Debug Info");
	

		

	}

}
