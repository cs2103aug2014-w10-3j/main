package sg.codengineers.ldo.logic;

import java.util.ArrayList;
import java.util.HashMap;

import sg.codengineers.ldo.db.DBConnector;
import sg.codengineers.ldo.model.Command.CommandType;
import sg.codengineers.ldo.model.Handler;
import sg.codengineers.ldo.model.Task;

public class Logic {
	private DBConnector _dbConnector;
	private ArrayList<Task> _taskList;
	private HashMap<CommandType, Handler> _map;
	private boolean _isInitialized = false;
	
	public Logic(DBConnector dbConnector){
		this._dbConnector = dbConnector;
		initialize();
	}

	private void initialize() {
		if(_isInitialized){
			return;
		}
		
		_taskList = _dbConnector.retrieveTaskList();
		
		_map = new HashMap<CommandType, Handler> ();
		_map.put(CommandType.CREATE, new CreateHandler(_taskList));
		_map.put(CommandType.DELETE, new DeleteHandler(_taskList));
		_map.put(CommandType.UPDATE, new UpdateHandler(_taskList));
		RetrieveHandler retrieveHandler = new RetrieveHandler(_taskList);
		_map.put(CommandType.RETRIEVE, retrieveHandler);
		_map.put(CommandType.SHOW, retrieveHandler);
		_map.put(CommandType.INVALID, null);
		_isInitialized = true;
	}
}
