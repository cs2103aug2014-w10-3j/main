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

	/**
	 * An update method that allows each entry in the
	 * database to be updated.
	 * 
	 * @param jsonData The data to be updated. It 
	 * must already be converted to JSON format using
	 * the toJSON implementation within the model
	 * @return Returns a boolean value indicating the
	 * success or failure of the method
	 * @return
	 */
	public boolean update(String jsonData);
	
	/**
	 * A method that retrieves all entries from the
	 * database.
	 * 
	 * @return A list containing all the entries from
	 * the database
	 */
	public List<String> read();
}
