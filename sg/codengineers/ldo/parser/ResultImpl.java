package sg.codengineers.ldo.parser;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;

import sg.codengineers.ldo.logic.Task;
import sg.codengineers.ldo.parser.CommandImpl.CommandType;

public class ResultImpl implements Result {

	private CommandType		_commandType;
	private Time			_operationTime;
	private ArrayList<Task>	_tasks;

	/* Constructors */
	public ResultImpl(CommandType commandType, Time time,
			ArrayList<Task> tasks) {
		_commandType = commandType;
		_operationTime = time;
		_tasks = tasks;
	}

	public ResultImpl(CommandType commandType, Time time, Task task) {
		_commandType = commandType;
		_operationTime = time;
		_tasks = new ArrayList<Task>();
		_tasks.add(task);
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
	public Time getOperationTime() {
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