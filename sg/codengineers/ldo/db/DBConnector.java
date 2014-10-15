package sg.codengineers.ldo.db;

/**
 * This class is the connector between the database
 * implementation and the database abstraction layer
 *
 * @author Sean
 */

import java.util.*;
import java.io.*;

public interface DBConnector {

	/**
	 * A create method to enter new data into the
	 * database.
	 * 
	 * @param data The data to be entered. It 
	 * must already be converted to string format using
	 * the toString implementation within the model
	 * @throws IOException
	 */
	public void create(String data) throws IOException;

	/**
	 * An update method that allows each entry in the
	 * database to be updated.
	 * 
	 * @param data The data to be updated. It 
	 * must already be converted to string format using
	 * the toString implementation within the model
	 * @throws IOException
	 */
	public void update(String data) throws IOException;
	
	/**
	 * A method that retrieves all entries from the
	 * database.
	 * 
	 * @return A list containing all the entries from
	 * the database
	 * @throws IOException
	 */
	public List<String> read()  throws IOException;
	
	/**
	 * A method that deletes an entry from the database
	 * 
	 * @param id The unique identifier of the entry
	 * @throws IOException
	 */
	public void delete(String data)  throws IOException;
}
