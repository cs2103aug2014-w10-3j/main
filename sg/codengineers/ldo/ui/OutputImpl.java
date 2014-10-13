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
	private static final String	TASK			= "%1d. %1s ID:%2d\n";

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
		if(!_result.getMessage().isEmpty()) {
			stub();
		}
		else {
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
					feedbackForShow();
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
	}

	private void feedbackForUpdate() {
		Task completedTask = _taskItr.next();
		showToUser(String.format(UPDATED_MESSAGE, completedTask.getName()));
	}

	private void feedbackForDelete() {
		Task completedTask = _taskItr.next();
		showToUser(String.format(DELETED_MESSAGE, completedTask.getName()));
	}

	/**
	 * Method will display all the tasks as requested by user. The format for
	 * display will be as such:
	 * 
	 * Showing all tasks 1. <Task Name> <Task's Unique ID> 2. <Task Name>
	 * <Task's Unique ID> 3. <Task Name> <Task's Unique ID>
	 */
	private void feedbackForShow() {
		int counter = 1;
		showToUser("Showing "+_result.getPrimaryOperand());
		while (_taskItr.hasNext()) {
			Task toPrint = _taskItr.next();
			showToUser(String.format(TASK, counter, toPrint.getName()/*,
					toPrint.getId() //TODO */));
			counter++;
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
	 * It will display the program name followed by the day's task in the following format:
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
		if (taskList!=null && taskList.hasNext()) {
			hasTasksToday = true;
		}
		if (hasTasksToday) {
			showToUser(TODAYS_TASK);
			int counter =1;
			while (taskList.hasNext()) {
				Task toPrint = taskList.next();
				showToUser(String.format(TASK, counter, toPrint.getName()/*,
						toPrint.getId()//TODO*/));
				counter++;
			}
		}
		else {
			showToUser(NO_TASK_TODAY);
		}
	}

	/**
	 * Method to inform user that module is still a stub
	 */
	private void stub() {
		showToUser(STUB_MESSAGE);
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