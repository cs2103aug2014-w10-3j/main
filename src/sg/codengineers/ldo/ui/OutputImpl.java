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
	private static final String	CREATED_MESSAGE	= "Added %1s\n";
	private static final String	UPDATED_MESSAGE	= "Updated %1s\n";
	private static final String	DELETED_MESSAGE	= "Deleted %1s\n";
	private static final String	STUB_MESSAGE	= "This module is still under development.\n";
	private static final String	TASK			= "%1d. %1s\n";
	private static final String	NAME			= "Name: %1s\n";
	private static final String	DESCRIPTION		= "Description: %1s\n";
	private static final String	TAG				= "Tag: %1s\n";
	private static final String	DEADLINE		= "Deadline: %s %s\n";

	/* Welcome messages */
	private static final String	PROGRAM_NAME	= "L'Do";
	private static final String	NO_TASK_TODAY	= "There are no tasks for today!\n";
	private static final String	TODAYS_TASK		= "Here are your tasks for today:\n";

	/* Member Variables */
	private Result				_result;
	private Iterator<Task>		_taskItr;

	/**
	 * Displays the result to user
	 * 
	 * @param result
	 *            Result from the executed command
	 * @throws Exception
	 *             Throws an IllegalArgumentException when the commandType of
	 *             the result is INVALID
	 */
	@Override
	public void displayResult(Result result) throws Exception {
		_result = result;
		_taskItr = result.getTasksIterator();
		CommandType commandType = _result.getCommandType();
		if (_result == null) {
			feedbackForUndo();
			return;
		}
		if (_result.getMessage() != null) {
			displayWelcome(result);
		} else {
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
				default:
					throw new IllegalArgumentException(
							"Command Type Invalid");
			}
		}
	}

	private void feedbackForCreate() {
		Task completedTask = _taskItr.next();
		showToUser(String.format(CREATED_MESSAGE, completedTask.getName()));
		showOneTaskToUser();
	}

	private void feedbackForUpdate() {
		Task completedTask = _taskItr.next();
		showToUser(String.format(UPDATED_MESSAGE, completedTask.getName()));
		showOneTaskToUser();
	}

	private void feedbackForDelete() {
		Task completedTask = _taskItr.next();
		showToUser(String.format(DELETED_MESSAGE, completedTask.getName()));
	}

	/**
	 * Method will display all the tasks as requested by user. The format for
	 * display will be as dictated by the showOneTaskToUser method or the
	 * showMultipleTasksToUser method.
	 * 
	 */
	private void feedbackForRetrieve() {
		if (!_taskItr.hasNext()) {
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

	private void feedbackForUndo() {
		showToUser("Undone last command");
	}

	/**
	 * Method will display multiple tasks to user.
	 * An example of the format will be:
	 * 
	 * 1. <Task Name>
	 * 2. <Task Name>
	 * 3. <Task Name>
	 * 
	 */
	private void showMultipleTasksToUser() {
		int counter = 1;
		String priOp = _result.getPrimaryOperand();
		if (priOp.isEmpty()) {
			priOp = "all";
		}
		showToUser("Showing " + priOp + "\n");
		while (_taskItr.hasNext()) {
			Task toPrint = _taskItr.next();
			showToUser(String.format(TASK, counter, toPrint.getName()));
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
	 * 
	 * All fields will only show if they are not empty.
	 * For deadline, the format will be hh:mm dd mmm yyyy
	 * An example:
	 * 20:59 Jan 2015
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
				sb.append(String.format("%04d",
						toPrint.getDeadline().getYear() + 1900));
				String date = sb.toString();
				showToUser(String.format(DEADLINE, time, date));
			}
		}
	}

	@Override
	public void displayException(Exception e) {
		if (e.getMessage() != null) {
			showToUser(e.getMessage() + "\n");
		}
		e.printStackTrace();
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
		_result = result;
		showToUser(PROGRAM_NAME + "\n");
		displayTodaysTask();
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

	private void feedbackForSearch() {
		showToUser("Showing all tasks containing \""
				+ _result.getPrimaryOperand() + "\":\n");
		showMultipleTasksToUser();
	}

	/**
	 * Method to inform user that module has not been fully developed. Only
	 * used during development, not in the final product.
	 * 
	 */
	private void stub() {
		showToUser(STUB_MESSAGE);
	}

	private boolean isNumeric(String str) {
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