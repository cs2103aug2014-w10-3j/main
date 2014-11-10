//@author A0111163Y

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
	private static final int FIRST = 0;

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
	public boolean create(Object data, String className) {
		List<DBConnector> connectorList = classToConnector.get(className.toLowerCase());
		boolean success = true;

		for (DBConnector connector : connectorList) {
			// If anyone of the process fails, the whole operation fails
			success = success && connector.create(data);
		}
		return success;
	}
	
	/**
	 * This allows the model to get all entries in the database
	 * 
	 * @param className The name of the model in question
	 */
	public List<String> read(String className) {
		List<DBConnector> connectorList = classToConnector.get(className.toLowerCase());
		
		// Make sure that the first one is authoritative
		DBConnector authoritative = connectorList.get(FIRST);
		if (authoritative == null) {
			return null;
		} else {
			return authoritative.read();
		}
	}
	
	/**
	 * This allows the model to update an entry in the database
	 * 
	 * @param data The data to be updated
	 * @param className The name of the model in question
	 */
	public boolean update(Object data, String className) {
		List<DBConnector> connectorList = classToConnector.get(className.toLowerCase());
		boolean success = true;

		for (DBConnector connector : connectorList) {
			// If anyone of the process fails, the whole operation fails
			success = success &&connector.update(data);
		}
		return success;
	}
	
	/**
	 * This allows entries in the database to be deleted
	 * 
	 * @param id The id of the entry to be deleted
	 * @param className The name of the model in question
	 */
	public boolean delete(Object data, String className) {
		List<DBConnector> connectorList = classToConnector.get(className.toLowerCase());
		boolean success = true;
		
		for (DBConnector connector : connectorList) {
			// If anyone of the process fails, the whole operation fails
			success = success && connector.delete(data);
		}
		return success;
	}
	
	/**
	 * This method is used to clear the database of entries
	 * This is only meant for unit testing purposes, to ensure
	 * a clean state of the database so that the tests does not
	 * depend on the information already present
	 * 
	 * @param className The class name to clear out
	 * @return True if the operation succeeds
	 */
	public boolean clear(String className) {
		List<DBConnector> connectorList = classToConnector.get(className.toLowerCase());
		boolean success = true;
		
		for (DBConnector connector : connectorList) {
			// If anyone of the process fails, the whole operation fails
			success = success && connector.clear();
		}
		return success;
	}
	
	public void gCalAuth() {
		GCalDBConnector.auth();
	}
	
	public boolean loginGCal(String authCode) {
		GCalDBConnector gCal = GCalDBConnector.setup(authCode);
		List<DBConnector> connectorList = classToConnector.get("task");
		connectorList.add(gCal);
		classToConnector.put("task", connectorList);
		
		return (gCal == null);
	}
}
