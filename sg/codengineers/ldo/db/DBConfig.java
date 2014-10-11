package sg.codengineers.ldo.db;

/**
 * This model defines the database configurations of the
 * program, including which model class has access to which
 * type of database. Each object represents one type of
 * configuration. It also helps to initialize all the 
 * database connections that the program has. This model is
 * special since it has direct connection to the database. 
 * This is done since initializing the database requires
 * information from the objects of this class, hence keeping
 * to the abstraction layers would inevitably result in a
 * circular definition.
 * 
 * @author Sean
 */

import java.util.*;

public class DBConfig {

	// Essential information for the configuration of the database
	private static DBConnector config;
	private static Map<String, List<DBConnector>> classToConnector;
	private static boolean isInitialized = false;
	
	// Filename of the config file
	private static final String FILENAME = "DBConfig";
	
	private static final int FIRST_WORD = 0;
	private static final int TYPE_ARRAY = 1;
	
	private String className;
	private String[] type;

	public DBConfig(String className, String[] type) {
		this.className = className;
		this.type = type;
	}
	
	/**
	 * Getter method for class name of the configuration
	 * 
	 * @return The name of the class
	 */
	public String getClassName() {
		return className;
	}
	
	/**
	 * Getter method for the array of types of database
	 * that the class can have
	 * 
	 * @return An array of the database types
	 */
	public String[] getType() {
		return type;
	}

	/**
	 * Initializes all the connections to the databases
	 * in accordance to the configurations in the config
	 * file
	 * 
	 * @return A mapping of the text representation of the
	 * class name to the database connector that it needs
	 */
	public static Map<String, List<DBConnector>> initDatabases() {
		if (!isInitialized) {

			/* 
			 * Open a connection to the config file, skipping
			 * the database abstraction layer. There is no way
			 * around this since this is needed to initialize 
			 * the layer.
			 */
			config = new TextDBConnector(FILENAME);
			List<String> jsonConfigList = config.read();

			for (String s : jsonConfigList) {
				DBConfig config = DBConfig.fromString(s);
				config.initDB();
			}

			isInitialized = true;
		}

		return classToConnector;
	}
	
	/*****************************
	 * Convert to string
	 *****************************/
	
	public static String toString(DBConfig dBConfig) {
		String types = arrayToString(dBConfig.getType());		
		return dBConfig.getClassName()+ " " + types;
	}
	
	public static DBConfig fromString(String entry) {
		String className = getClassFromEntry(entry);
		String[] typeArray = getTypeFromEntry(entry);
		return new DBConfig(className, typeArray);
	}
	
	private static String arrayToString(String[] array) {
		StringBuilder builder = new StringBuilder();
		
		for (String s : array) {
			builder.append(s);
			builder.append(",");
		}
		
		// Remove the last comma. Index starts from 0
		builder.deleteCharAt(builder.length() - 1);
		return builder.toString();
	}
	
	private static String getClassFromEntry(String entry) {
		return entry.trim().split(" ", 2)[FIRST_WORD];
	}
	
	private static String[] getTypeFromEntry(String entry) {
		String types = entry.trim().split(" ", 2)[TYPE_ARRAY];
		return types.split(",");
	}
}
