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
	private static final String	CREATED_MESSAGE	= "added %1s\n";
	private static final String	UPDATED_MESSAGE	= "updated %1s with %2s\n";
	private static final String	STUB_MESSAGE	= "This module is still under development.\n";
	/* Member Variables */
	private Result				_result;

	@Override
	public void displayResult(Result result) {
		// TODO Auto-generated method stub
		_result = result;
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
		Iterator<Task> taskItr = _result.getTasksIterator();
		Task completedTask = taskItr.next();
		showToUser(String.format(CREATED_MESSAGE, completedTask.getName()));
	}

	private void feedbackForUpdate() {
		// TODO Auto-generated method stub
		stub();
	}

	private void feedbackForDelete() {
		// TODO Auto-generated method stub
		stub();
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
