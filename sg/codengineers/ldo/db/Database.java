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

	/**
	 * Constructor This is where we choose which connector to use and
	 * initialize them accordingly.
	 */
	public Database() {
		classToConnector = DBConfig.initDatabases();
	}
}
