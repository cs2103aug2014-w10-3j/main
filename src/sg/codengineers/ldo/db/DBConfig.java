//@author A0111163Y

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
	private static Map<String, DBConfig> configList;
	private static Map<String, List<DBConnector>> classToConnector;
	private static boolean isInitialized = false;
	
	// Filename of the config file
	private static final String FILENAME = "dbconfig";
	private static final String SEPERATOR = "<;>";
	
	private static final int FIRST_WORD = 0;
	private static final int SECOND_WORD = 1;
	private static final int TYPE_ARRAY = 2;	
	
	private static final String[] DEFAULT_SETTINGS = {"0<;>task<;>textfile", "1<;>test<;>textfile"};
	
	private String className;
	private List<String> types;
	private int id;

	/**
	 * Constructor
	 * 
	 * @param className The name of the class whose
	 * data is being saved
	 * @param types The types of database that the type
	 * of class has
	 */
	public DBConfig(String className, String[] types) {
		this.id = configList.size();
		this.className = className;
		this.types = new ArrayList<String>();
		
		for (String s : types) {
			this.types.add(s);
		}
	}
	
	/**
	 * Constructor
	 * 
	 * @param id The id of the object
	 * @param className The name of the class whose
	 * data is being saved
	 * @param types The types of database that the type
	 * of class has
	 */
	public DBConfig(int id, String className, String[] types) {
		this.id = id;
		this.className = className;
		this.types = new ArrayList<String>();
		
		for (String s : types) {
			this.types.add(s);
		}
	}

	public int getId() {
		return id;
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
	 * @return An list of the database types
	 */
	public List<String> getTypes() {
		return types;
	}
	
	public boolean addType(String type) {
		types.add(type);
		return true;
	}
	
	/**
	 * Initialize the different types of database connectors
	 * that a class can have
	 */
	private void initDB() {
		List<DBConnector> connectorList = null;

		if (classToConnector.containsKey(className)) {
			connectorList = classToConnector.get(className);
		} else {
			connectorList = new ArrayList<DBConnector>();
		}

		for (String s : types) {
			if (s.equalsIgnoreCase("textfile")) {
				connectorList.add(new TextDBConnector(className));
			} else if (s.equalsIgnoreCase("GCal")) {
				connectorList.add(new GCalDBConnector());
			}
		}
		
		classToConnector.remove(className);
		classToConnector.put(className, connectorList);
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
			List<String> textConfigList = config.read();
			
			// Use the default settings if there isn't any
			// existing settings
			if (textConfigList.isEmpty()) {
				textConfigList = addDefaultSettings();
			}
			
			classToConnector = new HashMap<String, List<DBConnector>>();
			configList = new HashMap<String, DBConfig>();

			for (String s : textConfigList) {
				DBConfig config = DBConfig.fromString(s);
				configList.put(config.getClassName(), config);
				config.initDB();
			}

			isInitialized = true;
		}

		return classToConnector;
	}
	
	public static void addNewSettings(String className, String type, DBConnector connector) {
		DBConfig dBConfig = configList.get(className);
		List<DBConnector> connectorList = classToConnector.get(className);
		
		if (dBConfig == null) {
			connectorList = new ArrayList<DBConnector>();
			String[] types = new String[] {type};
			dBConfig = new DBConfig(className, types);
			config.create(dBConfig);
			connectorList.add(connector);
			classToConnector.put(className, connectorList);
		} else {
			dBConfig.addType(type);
			config.update(dBConfig);
			connectorList.add(connector);
			classToConnector.put(className, connectorList);
		}
		
	}

	private static List<String> addDefaultSettings() {
		List<String> settings = new ArrayList<String>();

		for (String s : DEFAULT_SETTINGS) {
			config.create(s);
			settings.add(s);
		}		
		return settings;
	}
	
	/*****************************
	 * Convert to string
	 *****************************/

	public String toString() {
		String types = listToString(getTypes());		
		return getId() + SEPERATOR + getClassName()+ SEPERATOR + types;
	}
	
	public static DBConfig fromString(String entry) {
		int id = getIdFromEntry(entry);
		String className = getClassFromEntry(entry);
		String[] typeArray = getTypeFromEntry(entry);
		return new DBConfig(id, className, typeArray);
	}
	
	private static String listToString(List<String> list) {
		StringBuilder builder = new StringBuilder();
		
		for (String s : list) {
			builder.append(s);
			builder.append(",");
		}
		
		// Remove the last comma. Index starts from 0
		builder.deleteCharAt(builder.length() - 1);
		return builder.toString();
	}
	
	private static int getIdFromEntry(String entry) {
		return Integer.parseInt(entry.trim().split(SEPERATOR, 3)[FIRST_WORD]);
	}
	
	private static String getClassFromEntry(String entry) {
		return entry.trim().split(SEPERATOR, 3)[SECOND_WORD];
	}
	
	private static String[] getTypeFromEntry(String entry) {
		String types = entry.trim().split(SEPERATOR, 3)[TYPE_ARRAY];
		return types.split(",");
	}
}
