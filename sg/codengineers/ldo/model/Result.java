package sg.codengineers.ldo.model;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;

import sg.codengineers.ldo.model.Command.CommandType;

//import sg.codengineers.ldo.parser.CommandImpl.CommandType;

/**
 * This interface specifies the available public methods to the Result Class
 * 
 * @author Victor Hazali
 * 
 */
public interface Result {

	/**
	 * Gets the command type of the result
	 * 
	 * @return A CommandType class representing the command type of the result
	 */
	public CommandType getCommandType();

	/**
	 * Gets the time stamp of the completion of the operation
	 * 
	 * @return A Time class representing the time of completion of the operation
	 */
	public Time getOperationTime();

	/**
	 * Gets an iterator of the tasks involved in the operation
	 * 
	 * @return An Iterator class containing all the tasks involved in the
	 *         operation
	 */
	public Iterator<Task> getTasksIterator();
}
