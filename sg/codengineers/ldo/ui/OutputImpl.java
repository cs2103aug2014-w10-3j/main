package sg.codengineers.ldo.ui;

import java.util.Iterator;

import sg.codengineers.ldo.model.Command;
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
	private static final String	CREATED_MESSAGE			= "Added %1s\n";
	private static final String	UPDATED_MESSAGE			= "Updated %1s with %2s\n";
	private static final String	DELETED_MESSAGE			= "Deleted %1s\n";
	private static final String	STUB_MESSAGE			= "This module is still under development.\n";

	/* Welcome messages */
	private static final String	PROGRAM_NAME			= "L'Do";
	private static final String	NO_TASK_TODAY_MESSAGE	= "There are no tasks for today!\n";

	/* Member Variables */
	private Result				_result;
	private Iterator<Task>		_taskItr;

	@Override
	/**
	 * Displays the result to user
	 * 
	 * @param result
	 * 			Result from the executed command
	 */
	public void displayResult(Result result) {
		// TODO Auto-generated method stub
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
			case SHOW :
				feedbackForShow();
				break;
			default:
				stub();
		}
	}

	private void feedbackForCreate() {
		Task completedTask = _taskItr.next();
		showToUser(String.format(CREATED_MESSAGE, completedTask.getName()));
	}

	private void feedbackForUpdate() {
		// TODO Auto-generated method stub
		stub();
	}

	private void feedbackForDelete() {
		Task completedTask = _taskItr.next();
		stub();
//		showToUser(String.format(DELETED_MESSAGE, completedTask.getName()));
	}

	private void feedbackForRetrieve() {
		// TODO Auto-generated method stub
		stub();
	}

	private void feedbackForShow() {
		// TODO Auto-generated method stub
		stub();
	}

	@Override
	public void displayError(String message, Exception e) {
		// TODO Auto-generated method stub
		stub();
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
	public void displayWelcome() {
		showToUser(PROGRAM_NAME + "\n");
		readExistingTasks();
		displayTodaysTask();
	}

	/**
	 * Reads the existing tasks from the current file
	 */
	private void readExistingTasks() {
		// TODO Auto-generated method stub
		stub();
	}

	/**
	 * Displays a list of tasks to be done today. If no task is to be done
	 * today, the NO_TASK_TODAY_MESSAGE will be shown instead
	 */
	private void displayTodaysTask() {
		showToUser(NO_TASK_TODAY_MESSAGE);
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