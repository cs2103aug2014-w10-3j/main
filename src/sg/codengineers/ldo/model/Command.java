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

	// The list of acceptable Command types
	public enum CommandType {
		CREATE, UPDATE, DELETE, RETRIEVE, SYNC, SEARCH, HELP, UNDO, EXIT,
		INVALID
	};

	/**
	 * Gets the original user input
	 * 
	 * @return a string object representing the initial user input
	 */
	public String getUserInput();

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
	 * Note that, for the event of a show command, if it does not have a primary
	 * operand (i.e. the command is simply show), the primary operand that will
	 * be returned is an empty string (i.e. "")
	 * 
	 * @return A string containing the primary operand.
	 */
	public String getPrimaryOperand();

	/**
	 * Gets the message field of the command.
	 * 
	 * The message field should only be used when the commandType is INVALID.
	 * The message will then contain the error message explaining why is the
	 * command invalid.
	 * 
	 * @return A string containing the message
	 */
	public String getMessage();

	/**
	 * Gets an iterator to iterate through all the additional arguments received
	 * from the user.
	 * 
	 * @return An Iterator that is able to iterate through all the additional
	 *         arguments
	 */
	public Iterator<AdditionalArgument> getAdditionalArguments();

	/**
	 * Displays the contents of the command class in the following format:
	 * 
	 * user input:\t <userInput>
	 * command type:\t<commandType>
	 * primary operand:\t<primaryOp>
	 * message:\t<message>
	 * additional arguments:
	 * <additional argument>
	 * <additional argument>
	 * 
	 * @return a string object containing the contents of the command object in
	 *         the format specified
	 */
	@Override
	public String toString();
}
