package sg.codengineers.ldo.db;

/**
 * This class is the connector between the database
 * implementation and the database abstraction layer
 *
 * @author Sean
 */

import java.util.*;

public interface DBConnector {

	/**
	 * A create method to enter new data into the
	 * database.
	 * 
	 * @param jsonData The data to be entered. It 
	 * must already be converted to JSON format using
	 * the toJSON implementation within the model
	 * @return Returns a boolean value indicating the
	 * success or failure of the method
	 */
	public boolean create(String jsonData);

}
