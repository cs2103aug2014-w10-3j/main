package sg.codengineers.ldo.parser;

import java.util.ArrayList;
import java.util.Iterator;

public class ResultImpl implements Result {

	private CommandType		_commandType;
	private String			_operationTime;
	private ArrayList<Task>	_tasks;

	/* Constructors */
	public ResultImpl(CommandType commandType, String time,
			ArrayList<Task> tasks) {
		_commandType = commandType;
		_operationTime = time;
		_tasks = tasks;
	}

	public ResultImpl(CommandType commandType, String time, Task task) {
		_commandType = commandType;
		_operationTime = time;
		_tasks = new ArrayList<Task>(task);
	}

	/* Public Methods */
	@Override
	public CommandType getCommandType() {
		return _commandType;
	}

	@Override
	/**
	 * Gets the time taken for the operation to complete
	 */
	public String getOperationTime() {
		return _operationTime;
	}

	@Override
	/**
	 * Returns an iterator which iterates through all the
	 * tasks completed.
	 */
	public Iterator<ArrayList<Task>> getTasksIterator() {
		// TODO Auto-generated method stub
		return null;
	}
}