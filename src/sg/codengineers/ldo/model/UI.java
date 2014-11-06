package sg.codengineers.ldo.model;

/**
 * This interface specifies all the public methods for the UI component
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
	 * 
	 */
	public void displayResult(Result result);

	/**
	 * Shows the user an error message detailing the rationale of the error.
	 * 
	 * @param message
	 *            String object containing the message to be shown to user
	 */
	public void displayError(String message);

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
	 * @param result
	 *            The list of task due today
	 */
	public void displayWelcome(Result result);

	/**
	 * Shows the Exit message to the user
	 */
	public void displayExit();
}
