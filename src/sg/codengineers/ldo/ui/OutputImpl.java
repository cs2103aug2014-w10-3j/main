package sg.codengineers.ldo.ui;

import java.util.Iterator;

import org.fusesource.jansi.Ansi;

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
	public static final String	ANSI_CLS		= "\u001b[2J";
	public static final String	ANSI_HOME		= "\u001b[H";

	/* Color Strings */
	private static final String	KEYWORD_COLOR	= "@|blue %s|@%s";
	/* Message Strings */
	private static final String	CREATED_MESSAGE	= String.format(KEYWORD_COLOR,
														"Added", " \"%s\"\n");
	private static final String	UPDATED_MESSAGE	= String.format(KEYWORD_COLOR,
														"Updated", " %s\n");
	private static final String	DELETED_MESSAGE	= String.format(KEYWORD_COLOR,
														"Deleted", " %s\n");
	private static final String	SYNC_MESSAGE	= String.format(KEYWORD_COLOR,
														"Syncing",
														" with Google\n");
	private static final String	SEARCH_MESSAGE	= String.format(KEYWORD_COLOR,
														"Searching",
														" for all tasks containing\"%s\":\n");
	private static final String	UNDO_MESSAGE	= String.format(KEYWORD_COLOR,
														"Undone",
														" command: \"s\".\n");
	private static final String	EXIT_MESSAGE	= "Bye! See you again.\n";
	private static final String	STUB_MESSAGE	= "This module is still under development.\n";
	private static final String	EMPTY_TASK_LIST	= "Task list is empty.\n";
	private static final String	TASK			= "[%d] %s%s%s%s%s%s\n";
	private static final String	NAME			= String.format(KEYWORD_COLOR,
														"Name", ": %s\n");
	private static final String	DESCRIPTION		= String.format(KEYWORD_COLOR,
														"Description", ": %s\n");
	private static final String	TAG				= String.format(KEYWORD_COLOR,
														"Tag", ": %s\n");
	private static final String	DEADLINE		= String.format(KEYWORD_COLOR,
														"Deadline", ": %s %s\n");
	private static final String	TIME			= String.format(KEYWORD_COLOR,
														"Time", ": %s\n");
	private static final String	PRIORITY		= String.format(KEYWORD_COLOR,
														"Priority", ": %s\n");

	/* Welcome messages */
	private static final String	PROGRAM_NAME	= "\t L'Do";
	private static final String	NO_TASK_TODAY	= "There are @|green no|@ tasks for today!\n";
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
			case SYNC :
				feedbackForSync();
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
		_taskItr = result.getTasksIterator();
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
	public void displayMessage(String message) {
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
		if (_result.getPrimaryOperand().equalsIgnoreCase("all")) {
			showToUser(String.format(DELETED_MESSAGE, "all"));
		} else {
			Task completedTask = _taskItr.next();
			showToUser(String.format(DELETED_MESSAGE, completedTask.getName()));
		}
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
			showToUser(EMPTY_TASK_LIST);
		}
		else {
			if (isNumeric(_result.getPrimaryOperand())) {
				showToUser("Showing task " + _result.getPrimaryOperand()
						+ ": \n");
				showOneTaskToUser();
			} else {
				showToUser("Showing all tasks\n");
				showMultipleTasksToUser();
			}
		}
	}

	private void feedbackForSync() {
		showToUser(String.format(SYNC_MESSAGE));
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
		showToUser(String.format(SEARCH_MESSAGE, _result.getPrimaryOperand()));
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
		showToUser(String.format(UNDO_MESSAGE, _result.getPrimaryOperand()));
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

		while (_taskItr.hasNext()) {
			Task toPrint = _taskItr.next();
			StringBuilder sb = new StringBuilder();
			String name = new String();
			String description = new String();
			String tag = new String();
			String deadline = new String();
			String time = new String();
			String priority = new String();

			name = toPrint.getName();

			if (!toPrint.getDescription().isEmpty()) {
				description = " " + toPrint.getDescription();
			}

			if (!toPrint.getTag().isEmpty()) {
				tag = " " + toPrint.getTag();
			}

			if (toPrint.getDeadline() != null) {

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
				deadline = " " + sb.toString();
			}
			if (toPrint.getStartTime() != null
					&& toPrint.getEndTime() != null
					&& !toPrint.getStartTime().equals(toPrint.getEndTime())) {
				sb = new StringBuilder();
				sb.append(" from ");
				sb.append(String.format("%02d", toPrint.getStartTime()
						.getHours()));
				sb.append(":");
				sb.append(String.format("%02d", toPrint.getStartTime()
						.getMinutes()));
				sb.append(" to ");
				sb.append(String
						.format("%02d", toPrint.getEndTime().getHours()));
				sb.append(":");
				sb.append(String.format("%02d", toPrint.getEndTime()
						.getMinutes()));
				time = sb.toString();
			}

			if (toPrint.getPriority() != null) {
				sb = new StringBuilder();
				switch (toPrint.getPriority()) {
					case HIGH :
						priority = "@|red  high|@";
						break;
					case NORMAL :
						priority = "@|yellow  normal|@";
						break;
					case LOW :
						priority = "@|green  low|@";
						break;
					default:
				}
			}

			showToUser(String.format(TASK, counter, name, description,
					time, tag, deadline, priority));
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
			StringBuilder sb = new StringBuilder();

			showToUser(String.format(NAME, toPrint.getName()));

			if (!toPrint.getDescription().isEmpty()) {
				showToUser(String.format(DESCRIPTION, toPrint.getDescription()));
			}

			if (!toPrint.getTag().isEmpty()) {
				showToUser(String.format(TAG, toPrint.getTag()));
			}

			if (toPrint.getDeadline() != null) {
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

			if (toPrint.getStartTime() != null && toPrint.getEndTime() != null
					&& !toPrint.getStartTime().equals(toPrint.getEndTime())) {
				sb = new StringBuilder();
				sb.append("from ");
				sb.append(String.format("%02d", toPrint.getStartTime()
						.getHours()));
				sb.append(":");
				sb.append(String.format("%02d", toPrint.getStartTime()
						.getMinutes()));
				sb.append(" to ");
				sb.append(String
						.format("%02d", toPrint.getEndTime().getHours()));
				sb.append(":");
				sb.append(String.format("%02d", toPrint.getEndTime()
						.getMinutes()));
				String timeRange = sb.toString();
				showToUser(String.format(TIME, timeRange));
			}

			if (toPrint.getPriority() != null) {
				switch (toPrint.getPriority()) {
					case HIGH :
						showToUser(String.format(PRIORITY, "@|red high|@"));
						break;
					case NORMAL :
						showToUser(String.format(PRIORITY, "@|yellow normal|@"));
						break;
					case LOW :
						showToUser(String.format(PRIORITY, "@|green low|@"));
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
			showMultipleTasksToUser();
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
	@SuppressWarnings("unused")
	private void stub() {
		showToUser(STUB_MESSAGE);
	}

	/**
	 * Helper method to clear the screen.
	 * 
	 * Used to help provide a cleaner user interface.
	 */
	private void clearScreen() {
		showToUser(ANSI_CLS);
		showToUser(ANSI_HOME);
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
		System.out.print(Ansi.ansi().render(message));
	}

}