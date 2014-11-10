//@author A0111163Y

package sg.codengineers.ldo.db;

/**
 * This class is the connector between the database
 * implementation and the database abstraction layer
 */

import java.util.*;

public interface DBConnector {
	/**
	 * A create method to enter new data into the
	 * database.
	 * 
	 * @param data The data to be entered. It 
	 * must already be converted to string format using
	 * the toString implementation within the model
	 * @return true if the operation succeeds
	 */
	public boolean create(Object data);

	/**
	 * An update method that allows each entry in the
	 * database to be updated.
	 * 
	 * @param data The data to be updated. It 
	 * must already be converted to string format using
	 * the toString implementation within the model
	 * @return true if the operation succeeds
	 */
	public boolean update(Object data);
	
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
	 * @return true if the operation succeeds
	 */
	public boolean delete(Object data);
	
	/**
	 * This method is used to clear the database of entries
	 * This is only meant for unit testing purposes, to ensure
	 * a clean state of the database so that the tests does not
	 * depend on the information already present
	 * 
	 * @return True if the operation succeeds
	 */
	public boolean clear();
}
