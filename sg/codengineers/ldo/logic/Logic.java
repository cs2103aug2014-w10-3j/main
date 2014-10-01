package sg.codengineers.ldo.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import sg.codengineers.ldo.db.DBConnector;
import sg.codengineers.ldo.model.AdditionalArgument;
import sg.codengineers.ldo.model.Command.CommandType;
import sg.codengineers.ldo.model.Handler;
import sg.codengineers.ldo.model.Result;
import sg.codengineers.ldo.model.Task;

public class Logic {
	private DBConnector _dbConnector;
	private ArrayList<Task> _taskList;
	private boolean _isInitialized = false;
	
	private CreateHandler createHandler;
	private RetrieveHandler retrieveHandler;
	private UpdateHandler updateHandler;
	private DeleteHandler deleteHandler;
	
	public Logic(){
		this._dbConnector = new DBConnector();
		initialize();
	}

	private void initialize() {
		if(_isInitialized){
			return;
		}
		
		_taskList = _dbConnector.read();
		
		createHandler = new CreateHandler(_taskList);
		retrieveHandler = new RetrieveHandler(_taskList);
		updateHandler = new UpdateHandler(_taskList);
		deleteHandler = new DeleteHandler(_taskList);
		
		_isInitialized = true;
	}
	
	private void commitChange(){
		_dbConnector.write(_taskList);
	}

	public Result createTask(String primaryOperand,
			Iterator<AdditionalArgument> iterator) {
		Result result = createHandler.execute(primaryOperand, iterator);
		commitChange();
		return result;
	}

	public Result deleteTask(String primaryOperand,
			Iterator<AdditionalArgument> iterator) {
		Result result =deleteHandler.execute(primaryOperand, iterator);
		commitChange();
		return result;
	}

	public Result updateTask(String primaryOperand,
			Iterator<AdditionalArgument> iterator) {
		Result result = updateHandler.execute(primaryOperand, iterator);
		commitChange();
		return result;
	}

	public Result retrieveTask(String primaryOperand,
			Iterator<AdditionalArgument> iterator) {
		return retrieveHandler.execute(primaryOperand, iterator);
	}

	public Result showTasks(Iterator<AdditionalArgument> iterator) {
		return retrieveHandler.execute(null, iterator);
	}
	
}
