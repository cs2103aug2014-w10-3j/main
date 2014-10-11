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
	 * @param Data The data to be entered. It 
	 * must already be converted to string format using
	 * the toString implementation within the model
	 * @return Returns a boolean value indicating the
	 * success or failure of the method
	 */
	public boolean create(String Data);

	/**
	 * An update method that allows each entry in the
	 * database to be updated.
	 * 
	 * @param Data The data to be updated. It 
	 * must already be converted to string format using
	 * the toString implementation within the model
	 * @return Returns a boolean value indicating the
	 * success or failure of the method
	 */
	public boolean update(String Data);
	
	/**
	 * A method that retrieves all entries from the
	 * database.
	 * 
	 * @return A list containing all the entries from
	 * the database
	 */
	public List<String> read();
	
	/**
	 * A method that deletes an entry from the database
	 * 
	 * @param id The unique identifier of the entry
	 * @return Returns a boolean value indicating the
	 * success or failure of the method
	 */
	public boolean delete(int id);
}
