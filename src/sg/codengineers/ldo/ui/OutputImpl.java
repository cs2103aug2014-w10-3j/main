package sg.codengineers.ldo.ui;

import java.util.Iterator;

import sg.codengineers.ldo.model.Command.CommandType;
import sg.codengineers.ldo.model.Output;
import sg.codengineers.ldo.model.Result;
import sg.codengineers.ldo.model.Task;

/**
 * This class implements the output class as specified by the Output interface
 * 
 * @author Victor Hazali
 * 
 */
public class OutputImpl implements Output {

	/* Constants */

	/* Message Strings */
	private static final String	CREATED_MESSAGE	= "Added %s\n";
	private static final String	UPDATED_MESSAGE	= "Updated %s\n";
	private static final String	DELETED_MESSAGE	= "Deleted %s\n";
	private static final String	EXIT_MESSAGE	= "Bye! See you again.\n";
	private static final String	STUB_MESSAGE	= "This module is still under development.\n";
	private static final String	TASK			= "[%d] %s %s %s %s %s\n";
	private static final String	NAME			= "Name: %s\n";
	private static final String	DESCRIPTION		= "Description: %s\n";
	private static final String	TAG				= "Tag: %s\n";
	private static final String	DEADLINE		= "Deadline: %s %s\n";
	private static final String	PRIORITY		= "Priority: %s\n";

	/* Welcome messages */
	private static final String	PROGRAM_NAME	= "L'Do";
	private static final String	NO_TASK_TODAY	= "There are no tasks for today!\n";
	private static final String	TODAYS_TASK		= "Here are your tasks for today:\n";

	/* Member Variables */
	private Result				_result;
	private Iterator<Task>		_taskItr;

	/* Public Methods */

	/**
	 * Displays the result to user
	 * 
	 * @param result
	 *            Result from the executed command
	 */
	@Override
	public void displayResult(Result result) {
		_result = result;
		_taskItr = result.getTasksIterator();
		CommandType commandType = _result.getCommandType();

		switch (commandType) {
			case CREATE :
				feedbackForCreate();
				break;
			case UPDATE :
				feedbackForUpdate();
				break;
			case DELETE :
				feedbackForDelete();
				break;
			case RETRIEVE :
				feedbackForRetrieve();
				break;
			case SEARCH :
				feedbackForSearch();
				break;
			case HELP :
				feedbackForHelp();
				break;
			case UNDO :
				feedbackForUndo();
				break;
			default:
				// Nothing to do
		}
	}

	/**
	 * Displays the error message to the user. The method simply shows the
	 * message without any further formatting to the message.
	 * 
	 * @param errorMessage
	 *            String object containing the error message.
	 * @precondition String must already be properly formatted for output to
	 *               user
	 */
	@Override
	public void displayError(String errorMessage) {
		showToUser(errorMessage);
	}

	@Override
	/**
	 * Displays the welcome message upon running of program
	 * It will display the program name followed by the day's task in the
	 * following format:
	 * <Program Name>
	 * Here are today's tasks:
	 * 1. <Task 1>
	 * 2. <Task 2>
	 * 
	 * If there are no tasks due today, the display will be replaced to
	 * <Program Name>
	 * <NO_TASK_TODAY_MESSAGE>
	 */
	public void displayWelcome(Result result) {
		clearScreen();
		_result = result;
		showToUser(PROGRAM_NAME + "\n");
		displayTodaysTask();
	}

	/**
	 * Displays the exit message to the user upon receiving the exit command.
	 */
	@Override
	public void displayExit() {
		showToUser(EXIT_MESSAGE);
	}
	
	@Override
	public void displayMessage(String message){
		showToUser(message);
	}

	/* Private methods */

	/**
	 * Gives the feedback for a CREATE Command Type.
	 * Shows user that the command was successfully executed and prints out the
	 * details of the new task added.
	 */
	private void feedbackForCreate() {
		Task completedTask = _result.getTasksIterator().next();
		showToUser(String.format(CREATED_MESSAGE, completedTask.getName()));
		showOneTaskToUser();
	}

	/**
	 * Gives the feedback for an UPDATE CommandType.
	 * Shows the user that the command was successfully executed and prints out
	 * the details of the newly updated task.
	 */
	private void feedbackForUpdate() {
		Task completedTask = _result.getTasksIterator().next();
		showToUser(String.format(UPDATED_MESSAGE, completedTask.getName()));
		showOneTaskToUser();
	}

	/**
	 * Gives the feedback for a DELETE CommandType.
	 * Shows the user that the command was successfully executed and prints out
	 * the name of the deleted task.
	 */
	private void feedbackForDelete() {
		Task completedTask = _taskItr.next();
		showToUser(String.format(DELETED_MESSAGE, completedTask.getName()));
	}

	/**
	 * Gives the feedback for a RETRIEVE Command Type.
	 * Method will display all the tasks as requested by user. The format for
	 * display will be as dictated by the showOneTaskToUser method or the
	 * showMultipleTasksToUser method.
	 */
	private void feedbackForRetrieve() {
		clearScreen();
		if (!_result.getTasksIterator().hasNext()) {
			showToUser("Task list is empty.\n");
		}
		else {
			if (isNumeric(_result.getPrimaryOperand())) {
				showToUser("Showing task " + _result.getPrimaryOperand()
						+ ": \n");
				showOneTaskToUser();
			} else {
				showMultipleTasksToUser();
			}
		}
	}

	/**
	 * Gives the feedback for a SEARCH CommandType.
	 * Method will display all the tasks that meets the search criteria provided
	 * by the user.
	 * The format for display will be as dictated by the showMultipleTasksToUser
	 * method.
	 */
	private void feedbackForSearch() {
		clearScreen();
		showToUser("Showing all tasks containing \""
				+ _result.getPrimaryOperand() + "\":\n");
		showMultipleTasksToUser();
	}

	/**
	 * Gives the feedback for a HELP CommandType.
	 * 
	 * Method will display the help message associated with what the user
	 * requests.
	 * The format of the message will already be decided.
	 */
	private void feedbackForHelp() {
		showToUser(_result.getMessage());
	}

	/**
	 * Gives the feedback for an UNDO CommandType.
	 * 
	 * Shows the user that the command was successfully executed and tells the
	 * user which command was undone.
	 */
	private void feedbackForUndo() {
		showToUser("Undone last command");
	}

	/**
	 * Method will display multiple tasks to user.
	 * An example of the format will be:
	 * 
	 * [1] <Task Name>
	 * [2] <Task Name>
	 * [3] <Task Name>
	 * 
	 */
	@SuppressWarnings("deprecation")
	private void showMultipleTasksToUser() {
		int counter = 1;
		String priOp = _result.getPrimaryOperand();
		StringBuilder sb = new StringBuilder();
		if (priOp.isEmpty()) {
			priOp = "all";
		}
		showToUser("Showing " + priOp + "\n");
		while (_taskItr.hasNext()) {
			sb = new StringBuilder();
			Task toPrint = _taskItr.next();
			sb.append(String.format("%02d", toPrint.getDeadline()
					.getHours()));
			sb.append(":");
			sb.append(String.format("%02d", toPrint.getDeadline()
					.getMinutes()));
			sb.append(" ");
			sb.append(String
					.format("%02d", toPrint.getDeadline().getDate()));
			sb.append(" ");
			int month = toPrint.getDeadline().getMonth();
			switch (month) {
				case 0 :
					sb.append("Jan");
					break;
				case 1 :
					sb.append("Feb");
					break;
				case 2 :
					sb.append("Mar");
					break;
				case 3 :
					sb.append("Apr");
					break;
				case 4 :
					sb.append("May");
					break;
				case 5 :
					sb.append("Jun");
					break;
				case 6 :
					sb.append("Jul");
					break;
				case 7 :
					sb.append("Aug");
					break;
				case 8 :
					sb.append("Sep");
					break;
				case 9 :
					sb.append("Oct");
					break;
				case 10 :
					sb.append("Nov");
					break;
				case 11 :
					sb.append("Dec");
					break;
				default:
			}
			sb.append(" ");
			sb.append(String.format("%04d",
					toPrint.getDeadline().getYear() + 1900));
			showToUser(String.format(TASK, counter, toPrint.getName(),
					toPrint.getDescription(), toPrint.getTag(),
					sb.toString(), toPrint.getPriority()));
			counter++;
		}
	}

	/**
	 * Method will display one task to the user.
	 * The format will be:
	 * 
	 * <Task Name>
	 * <Task Description>
	 * <Task Tag(s)>
	 * <Task Deadline>
	 * <Priority>
	 * 
	 * All fields will only show if they are not empty.
	 * For deadline, the format will be hh:mm dd mmm yyyy
	 * An example:
	 * 20:59 11 Jan 2015
	 */
	@SuppressWarnings("deprecation")
	private void showOneTaskToUser() {
		while (_taskItr.hasNext()) {
			Task toPrint = _taskItr.next();

			showToUser(String.format(NAME, toPrint.getName()));

			if (!toPrint.getDescription().isEmpty()) {
				showToUser(String.format(DESCRIPTION, toPrint.getDescription()));
			}

			if (!toPrint.getTag().isEmpty()) {
				showToUser(String.format(TAG, toPrint.getTag()));
			}

			if (toPrint.getDeadline() != null) {
				StringBuilder sb = new StringBuilder();
				sb.append(String.format("%02d", toPrint.getDeadline()
						.getHours()));
				sb.append(":");
				sb.append(String.format("%02d", toPrint.getDeadline()
						.getMinutes()));
				String time = sb.toString();
				sb = new StringBuilder();
				sb.append(" ");
				sb.append(String
						.format("%02d", toPrint.getDeadline().getDate()));
				sb.append(" ");
				int month = toPrint.getDeadline().getMonth();
				switch (month) {
					case 0 :
						sb.append("Jan");
						break;
					case 1 :
						sb.append("Feb");
						break;
					case 2 :
						sb.append("Mar");
						break;
					case 3 :
						sb.append("Apr");
						break;
					case 4 :
						sb.append("May");
						break;
					case 5 :
						sb.append("Jun");
						break;
					case 6 :
						sb.append("Jul");
						break;
					case 7 :
						sb.append("Aug");
						break;
					case 8 :
						sb.append("Sep");
						break;
					case 9 :
						sb.append("Oct");
						break;
					case 10 :
						sb.append("Nov");
						break;
					case 11 :
						sb.append("Dec");
						break;
					default:
				}
				sb.append(" ");
				sb.append(String.format("%04d",
						toPrint.getDeadline().getYear() + 1900));
				String date = sb.toString();
				showToUser(String.format(DEADLINE, time, date));
			}

			if (toPrint.getPriority() != null) {
				switch (toPrint.getPriority()) {
					case HIGH :
						showToUser(String.format(PRIORITY, "high"));
						break;
					case MEDIUM :
						showToUser(String.format(PRIORITY, "medium"));
						break;
					case LOW :
						showToUser(String.format(PRIORITY, "low"));
						break;
					default:
				}
			}
		}
	}

	/**
	 * Displays a list of tasks to be done today. If no task is to be done
	 * today, the NO_TASK_TODAY_MESSAGE will be shown instead
	 */
	private void displayTodaysTask() {
		boolean hasTasksToday = false;
		Iterator<Task> taskList = _result.getTasksIterator();
		if (taskList != null && taskList.hasNext()) {
			hasTasksToday = true;
		}
		if (hasTasksToday) {
			showToUser(TODAYS_TASK);
			int counter = 1;
			while (taskList.hasNext()) {
				Task toPrint = taskList.next();
				showToUser(String.format(TASK, counter, toPrint.getName()));
				counter++;
			}
		}
		else {
			showToUser(NO_TASK_TODAY);
		}
	}

	/**
	 * Method to inform user that module has not been fully developed. Only
	 * used during development, not in the final product.
	 * 
	 */
	private void stub() {
		showToUser(STUB_MESSAGE);
	}

	/**
	 * Helper method to clear the screen.
	 * 
	 * Used to help provide a cleaner user interface.
	 */
	private void clearScreen() {
		try
		{
			final String os = System.getProperty("os.name");

			if (os.contains("Windows"))
			{
				Runtime.getRuntime().exec("cls");
			}
			else
			{
				Runtime.getRuntime().exec("clear");
			}
		} catch (final Exception e)
		{
			// Do nothing.
		}
	}

	/**
	 * Helper method to check if the contents of a string is numeric.
	 * 
	 * @param str
	 *            String to check
	 * @return True if all the values within the string are digits. False
	 *         otherwise. Also returns false for empty strings or blank strings.
	 */
	private boolean isNumeric(String str) {
		if (str == null) {
			return false;
		}
		if (str.trim().isEmpty()) {
			return false;
		}
		for (char c : str.toCharArray()) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Displays a message to the user
	 * 
	 * @param message
	 *            Message to be shown
	 */
	private void showToUser(String message) {
		System.out.print(message);
	}

}