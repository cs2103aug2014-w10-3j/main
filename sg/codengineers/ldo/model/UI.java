package sg.codengineers.ldo.model;

/**
 * This interface specifies all the available methods for the UI component
 * 
 * @author Victor Hazali
 * 
 */
public interface UI {

	/**
	 * Reads a string from the user
	 * 
	 * @return a String object representing user input
	 */
	public String readFromUser();

	/**
	 * Shows the result of the execution of a command based on user input to the
	 * user.
	 * 
	 * @param result
	 *            a Result object representing the results of execution
	 * @throws Exception
	 *             throws an IllegalArgumentException when the CommandType of
	 *             the result is INVALID
	 * 
	 */
	public void showToUser(Result result) throws Exception;

	/**
	 * Shows the user a message
	 * 
	 * @param message
	 *            String object containing the message to be shown to user
	 */
	public void showToUser(String message);

	/**
	 * Shows the Welcome message to the user.
	 * It will display the program name followed by the day's task in the
	 * following format:
	 * <Program Name>
	 * Here are today's tasks:
	 * 1. <Task 1>
	 * 2. <Task 2>
	 * 
	 * If there are no tasks due on the same day, the display will be replaced
	 * by:
	 * <Program Name>
	 * <NO_TASK_TODAY_MESSAGE>
	 * 
	 */
	public void showWelcomeMessage();
}
