package sg.codengineers.ldo.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import sg.codengineers.ldo.db.DBConnector;
import sg.codengineers.ldo.model.AdditionalArgument;
import sg.codengineers.ldo.model.Command.CommandType;
import sg.codengineers.ldo.model.Result;
import sg.codengineers.ldo.model.Task;

public class Logic {
	private DBConnector _dbConnector;
	private List<Task> _taskList;
	private boolean _isInitialized = false;
	
	private CreateHandler createHandler;
	private RetrieveHandler retrieveHandler;
	private UpdateHandler updateHandler;
	private DeleteHandler deleteHandler;
	
	private static Logic instance = null;
	
	public static Logic getLogic(){
		if(instance == null){
			instance = new Logic();
		}
		return instance;
	}
	
	private Logic(){
		this._dbConnector = new DBConnector("LDoDatabase.txt");
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
		for (Task task : _taskList){
			_dbConnector.write(task);
		}
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
