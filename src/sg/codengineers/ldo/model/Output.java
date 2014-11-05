package sg.codengineers.ldo.model;

/**
 * This interface specifies how a user can interact with the Output class
 * 
 * @author Victor Hazali
 * 
 */
public interface Output {

	/**
	 * Displays result from executing the command
	 * 
	 * @param result
	 *            Result from the execution of command
	 * @throws Exception
	 *             Throws an Illegal Argument Exception when command type of
	 *             result is invalid
	 */
	public void displayResult(Result result) throws Exception;

	/**
	 * Displays an exception message to the user. The method will first display
	 * the message in the exception by calling the getMessage() method as
	 * specified by the Exception Class. However, if a null is received, the
	 * method will skip this step. The method will then execute
	 * printStackTracte() method as specified by the Exception Class.
	 * 
	 * @param e
	 *            The exception to be show to the user. Should contain the
	 *            reason behind the exception.
	 */
	public void displayException(Exception e);

	/**
	 * Displays the welcome message to the user
	 * 
	 * @param result
	 *            The list of task due today.
	 */
	public void displayWelcome(Result result);

	/**
	 * Displays an exit message to the user.
	 */
	public void displayExit();
}
