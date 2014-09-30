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
	private static final String	CREATED_MESSAGE	= "Added %1s\n";
	private static final String	UPDATED_MESSAGE	= "Updated %1s with %2s\n";
	private static final String DELETED_MESSAGE	= "Deleted %1s\n";
	private static final String	STUB_MESSAGE	= "This module is still under development.\n";
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
			default :
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
		showToUser(String.format(DELETED_MESSAGE,completedTask.getName()));
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

	private void showToUser(String message) {
		System.out.print(message);
	}

	private void stub() {
		showToUser(STUB_MESSAGE);
	}

}
