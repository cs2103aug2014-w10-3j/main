package sg.codengineers.ldo.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import sg.codengineers.ldo.db.DBConnector;
import sg.codengineers.ldo.db.Database;
import sg.codengineers.ldo.model.AdditionalArgument;
import sg.codengineers.ldo.model.Command.CommandType;
import sg.codengineers.ldo.model.Result;
import sg.codengineers.ldo.model.Task;
/**
 * Logic class provides basic manipulation functions of tasks and task lists. <br>
 * To construct a Logic instance, please call {@link Logic#getLogic()}. There will be only
 * one Logic instance alive at a time.
 * @author Wenhao
 *
 */
public class Logic {
	
	private Database _dbConnector;
	private List<Task> _taskList;
	private boolean _isInitialized = false;
	
	private Handler createHandler,retrieveHandler,updateHandler,deleteHandler;
	private HelpHandler helpHandler;
	
	private static Logic instance = null;
	
	public static Logic getInstance(){
		try{
			if(instance == null){
				instance = new Logic();
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return instance;
	}
	
	private Logic()throws Exception{
		this._dbConnector = Database.initDatabase();
		initialize();
	}
	
	protected Logic (boolean stub){
		
	}

	private void initialize() throws Exception{
		if(_isInitialized){
			return;
		}
		
		_taskList = new ArrayList<Task>();
		List<String> stringList = _dbConnector.read(TaskImpl.CLASS_NAME);
		for(String s : stringList){
			_taskList.add(TaskImpl.valueOf(s));
		}
		
		createHandler = new CreateHandler(_taskList);
		retrieveHandler = new RetrieveHandler(_taskList);
		updateHandler = new UpdateHandler(_taskList);
		deleteHandler = new DeleteHandler(_taskList);
		helpHandler = new HelpHandler(_taskList);
		_isInitialized = true;
	}
	

	public Result createTask(String primaryOperand,
			Iterator<AdditionalArgument> iterator) throws IOException{
		Result result = createHandler.execute(primaryOperand, iterator);
		_dbConnector.create(result.getTasksIterator().next().toString(),TaskImpl.CLASS_NAME);
		return result;
	}

	public Result deleteTask(String primaryOperand,
			Iterator<AdditionalArgument> iterator) throws IOException{
		Result result = deleteHandler.execute(primaryOperand, iterator);
		_dbConnector.delete(result.getTasksIterator().next().toString(), TaskImpl.CLASS_NAME);
		return result;
	}

	public Result updateTask(String primaryOperand,
			Iterator<AdditionalArgument> iterator) throws IOException{
		Result result = updateHandler.execute(primaryOperand, iterator);
		_dbConnector.update(result.getTasksIterator().next().toString(),TaskImpl.CLASS_NAME);
		return result;
	}

	public Result retrieveTask(String primaryOperand,
			Iterator<AdditionalArgument> iterator) {
		return retrieveHandler.execute(primaryOperand, iterator);
	}

	public Result showTasks(Iterator<AdditionalArgument> iterator) {
		return retrieveHandler.execute(null, iterator);
	}
	
	public Result showHelp(CommandType type){
		return helpHandler.execute(type);
	}
	
}
