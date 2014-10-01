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
	 */
	public void displayResult(Result result);

	/**
	 * Displays an error message to the user
	 * 
	 * @param message
	 *            The message to be show to the user. Should contain the reason
	 *            behind the error
	 */
	public void displayError(String message, Exception e);
}