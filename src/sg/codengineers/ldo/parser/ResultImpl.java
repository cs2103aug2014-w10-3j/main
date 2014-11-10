package sg.codengineers.ldo.parser;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import sg.codengineers.ldo.model.Result;
import sg.codengineers.ldo.model.Task;
import sg.codengineers.ldo.model.Command.CommandType;

/**
 * This class specifies the implementation of the Result class as specified by
 * the Result interface
 * 
 */
//@author A0110741X
public class ResultImpl implements Result {

	private CommandType	_commandType;
	private String		_primaryOperand;
	private String		_message;
	private Time		_operationTime;
	private List<Task>	_tasks;

	/* Constructors */
	public ResultImpl(CommandType commandType, String primaryOperand,
			Time time, List<Task> tasks) {
		_commandType = commandType;
		_primaryOperand = primaryOperand;
		_operationTime = time;
		_tasks = tasks;
	}

	public ResultImpl(CommandType commandType, String primaryOperand,
			Time time, Task task) {
		_commandType = commandType;
		_primaryOperand = primaryOperand;
		_operationTime = time;
		_tasks = new ArrayList<Task>();
		_tasks.add(task);
	}

	public ResultImpl(CommandType commandType, String message, Time time) {
		_commandType = commandType;
		_primaryOperand = "";
		_message = message;
		_operationTime = time;
		_tasks = new ArrayList<Task>();
	}

	/* Public Methods */
	@Override
	public CommandType getCommandType() {
		return _commandType;
	}

	@Override
	public String getPrimaryOperand() {
		return _primaryOperand;
	}

	@Override
	public String getMessage() {
		return _message;
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
	public Iterator<Task> getTasksIterator() {
		return _tasks.iterator();
	}
}