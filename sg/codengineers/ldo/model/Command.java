package sg.codengineers.ldo.model;

import java.util.Iterator;

/**
 * This interface specifies the public methods of the Command Class
 * 
 * @author Victor Hazali
 * @Collaborator Sharon Lynn
 * 
 */
public interface Command {
	public enum CommandType {
		CREATE, RETRIEVE, UPDATE, DELETE, SHOW, INVALID
	};
	/**
	 * Gets the command type of the command.
	 * 
	 * @return the command type; either create, retrieve, update, delete show or
	 *         invalid
	 */
	public CommandType getCommandType();

	/**
	 * Gets the primary operand of the command.
	 * 
	 * @return A string containing the primary operand.
	 */
	public String getPrimaryOperand();

	/**
	 * Gets an iterator to iterate through all the additional arguments received
	 * from the user.
	 * 
	 * @return An Iterator that is able to iterate through all the additional
	 *         arguments
	 */
	public Iterator<AdditionalArgument> getIterator();
}