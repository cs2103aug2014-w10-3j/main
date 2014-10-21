package sg.codengineers.ldo.db;

/**
 * This class is the database abstraction layer that sits on top of the data
 * layer. It manages how data is handled within the program, decides where
 * to save all data and how to go about doing it. It chooses the appropriate
 * connector which saves the data diligently. All changes to the data must go
 * through this class
 *
 * @author Sean
 */

import java.util.*;

public class Database {

	private Map<String, List<DBConnector>> classToConnector;
	
	private static Database database;
	private static boolean isInitialized = false;
	
	public static Database initDatabase() {
		if (!isInitialized) { 
			database = new Database();
		}
		return database;
	}

	/**
	 * Constructor This is where we choose which connector to use and
	 * initialize them accordingly.
	 */
	private Database() {
		classToConnector = DBConfig.initDatabases();
	}
	
	/**
	 * This allows a class to create a new instance which represents
	 * one entry in the database and save it to the database
	 * 
	 * @param data The data to be saved
	 * @param className The name of the model in question
	 */
	public void create(String data, String className) {
		List<DBConnector> connectorList = classToConnector.get(className.toLowerCase());
		
		for (DBConnector connector : connectorList) {
			connector.create(data);
		}
	}
	
	/**
	 * This allows the model to get all entries in the database
	 * 
	 * @param className The name of the model in question
	 */
	public List<String> read(String className) {
		List<DBConnector> connectorList = classToConnector.get(className.toLowerCase());
		
		// Make sure that the first one is authoritative
		return connectorList.get(0).read();
	}
	
	/**
	 * This allows the model to update an entry in the database
	 * 
	 * @param data The data to be updated
	 * @param className The name of the model in question
	 */
	public void update(String data, String className) {
		List<DBConnector> connectorList = classToConnector.get(className.toLowerCase());

		for (DBConnector connector : connectorList) {
			connector.update(data);
		}
	}
	
	/**
	 * This allows entries in the database to be deleted
	 * 
	 * @param id The id of the entry to be deleted
	 * @param className The name of the model in question
	 */
	public void delete(String data, String className) {
		List<DBConnector> connectorList = classToConnector.get(className.toLowerCase());

		for (DBConnector connector : connectorList) {
			connector.delete(data);
		}
	}
}
