package sg.codengineers.ldo.logic;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import sg.codengineers.ldo.db.Database;
import sg.codengineers.ldo.model.AdditionalArgument;
import sg.codengineers.ldo.model.Command;
import sg.codengineers.ldo.model.Command.CommandType;
import sg.codengineers.ldo.model.Result;
import sg.codengineers.ldo.model.Task;
import sg.codengineers.ldo.parser.ResultImpl;
/**
 * Logic class provides basic manipulation functions of tasks and task lists. <br>
 * To construct a Logic instance, please call {@link Logic#getLogic()}. There will be only
 * one Logic instance alive at a time.
 * @author Wenhao
 *
 */
public class Logic {
	private static String MSG_SYNC_SUCCESS = "Sync to Google Calendar successful.\n";
	private static String MSG_SYNC_FAIL = "Sync to Google Calendar failed.\n";
	
	private Database _dbConnector;
	private List<Task> _taskList;
	private Stack<List<Task>> _listStack;
	private Stack<Command> _commandStack;
	private boolean _isInitialized = false;
	
	private Handler createHandler,searchHandler,updateHandler,deleteHandler;
	private HelpHandler helpHandler;
	private ShowHandler showHandler;
	
	private static Logic instance = null;
	public static final boolean DEBUG = false;
	
	public static Logic getInstance(){
		try{
			if(instance == null){
				instance = new Logic();
			}
		} catch (Exception e){
			if(Logic.DEBUG){
				e.printStackTrace();
			}
		}
		return instance;
	}
	
	private Logic()throws Exception{
		try{
			this._dbConnector = Database.initDatabase();
			initialize();			
		} catch(Exception e){
			if(Logic.DEBUG){
				e.printStackTrace();
			}
			throw e;
		}

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
		searchHandler = new SearchHandler(_taskList);
		updateHandler = new UpdateHandler(_taskList);
		deleteHandler = new DeleteHandler(_taskList);
		helpHandler = new HelpHandler(_taskList);
		showHandler = new ShowHandler(_taskList);
		_isInitialized = true;
		_listStack = new Stack<List<Task>>();
		_listStack.push(new ArrayList<Task>(_taskList));
		_commandStack = new Stack<Command>();
	}
	

	public Result createTask(Command command) throws IOException{
		Result result = null;
		try{
			String primaryOperand = command.getPrimaryOperand();
			Iterator<AdditionalArgument> iterator = command.getAdditionalArguments();
			result = createHandler.execute(primaryOperand, iterator);
			_dbConnector.create(result.getTasksIterator().next(),TaskImpl.CLASS_NAME);
			_listStack.push(new ArrayList<Task>(_taskList));
			_commandStack.push(command);
		} catch(Exception e){
			if(DEBUG){
				e.printStackTrace();
			}
			result =  new ResultImpl(CommandType.INVALID, 
					"Can't create task with "+command.getUserInput(),
					new Time(System.currentTimeMillis()));
		}
		
		return result;
	}

	public Result deleteTask(Command command) throws IOException{
		Result result;
		try{
			String primaryOperand = command.getPrimaryOperand();
			Iterator<AdditionalArgument> iterator = command.getAdditionalArguments();
			result = deleteHandler.execute(primaryOperand, iterator);
			_dbConnector.delete(result.getTasksIterator().next(), TaskImpl.CLASS_NAME);
			_listStack.push(new ArrayList<Task>(_taskList));
			_commandStack.push(command);			
		} catch(Exception e){
			return  new ResultImpl(CommandType.INVALID, 
					"Cannot delete task with "+command.getUserInput(),
					new Time(System.currentTimeMillis()));				
		}

		return result;
	}

	public Result updateTask(Command command) throws IOException{
		Result result;
		try{
			String primaryOperand = command.getPrimaryOperand();
			Iterator<AdditionalArgument> iterator = command.getAdditionalArguments();
			result = updateHandler.execute(primaryOperand, iterator);
			_dbConnector.update(result.getTasksIterator().next(),TaskImpl.CLASS_NAME);
			_listStack.push(new ArrayList<Task>(_taskList));
			_commandStack.push(command);			
		} catch(Exception e){
			return  new ResultImpl(CommandType.INVALID, 
					"Cannot update task with "+command.getUserInput(),
					new Time(System.currentTimeMillis()));			
		}
		return result;
	}

	public Result searchTask(Command command){
		try{
			String primaryOperand = command.getPrimaryOperand();
			Iterator<AdditionalArgument> iterator = command.getAdditionalArguments();
			return searchHandler.execute(primaryOperand, iterator);			
		} catch(Exception e) {
			return  new ResultImpl(CommandType.INVALID, 
					"Cannot search for task with "+command.getUserInput(),
					new Time(System.currentTimeMillis()));
		}

	}

	public Result showTask(Command command) {
		int index = -1;
		if(command.getPrimaryOperand() == null || command.getPrimaryOperand().isEmpty() || command.getPrimaryOperand().equalsIgnoreCase("all")){
			return showHandler.execute(index);
		}
		try{
			index = Integer.valueOf(command.getPrimaryOperand());
		} catch (Exception e){
			if(DEBUG){
				e.printStackTrace();
			}
			return new ResultImpl(CommandType.INVALID, 
					"Task "+command.getPrimaryOperand()+" doesn't exist.",
					new Time(System.currentTimeMillis()));
		}
		return showHandler.execute(index);
	}
	
	public Result undoTask(){
		_taskList.clear();
		if(_listStack.size() > 1){
			_listStack.pop();
			_taskList.addAll(_listStack.peek());
		}
		String userInput= null;
		if(!_commandStack.isEmpty()){
			userInput = _commandStack.pop().getUserInput();
		}
		Task task = null;
		return new ResultImpl(CommandType.UNDO,userInput, new Time(System.currentTimeMillis()),task);
	}
	
	public Result showHelp(Command command){
		try{
			CommandType type = command.getCommandType();
			return helpHandler.execute(type);			
		}catch(Exception e){
			return new ResultImpl(CommandType.INVALID, 
					"Cannot find HELP page with "+command.getUserInput(),
					new Time(System.currentTimeMillis()));			
		}

	}
	
	public void gCalAuth(){
		_dbConnector.gCalAuth();
	}
	
	public Result syncGCal(String userGCalAuthKey){
		boolean successSync = _dbConnector.loginGCal(userGCalAuthKey);
		if (successSync == true) {
			return new ResultImpl(CommandType.SYNC, MSG_SYNC_SUCCESS, new Time(System.currentTimeMillis()));
		} else {
			return new ResultImpl(CommandType.SYNC, MSG_SYNC_FAIL, new Time(System.currentTimeMillis()));
		}
	}
}
