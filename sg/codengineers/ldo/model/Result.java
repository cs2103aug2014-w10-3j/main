package sg.codengineers.ldo.model;

import java.sql.Time;
import java.util.Iterator;

import sg.codengineers.ldo.model.Command.CommandType;

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
	 * @return A CommandType object representing the command type of the result
	 */
	public CommandType getCommandType();

	/**
	 * Gets the primary operand of the result
	 * 
	 * @return A String object representing the primary operand of the command
	 *         by user
	 */
	public String getPrimaryOperand();

	/**
	 * Gets the message tagged to the Result class. This message is used for
	 * --help calls
	 * 
	 * @return A String object containing all the help information for the
	 *         commandType
	 */
	public String getMessage();

	/**
	 * Gets the time stamp of the completion of the operation
	 * 
	 * @return A Time object representing the time of completion of the
	 *         operation
	 */
	public Time getOperationTime();

	/**
	 * Gets an iterator of the tasks involved in the operation
	 * 
	 * @return An Iterator<Task> object containing all the tasks involved in the
	 *         operation
	 */
	public Iterator<Task> getTasksIterator();
}
