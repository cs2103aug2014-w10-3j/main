package sg.codengineers.ldo.logic;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import sg.codengineers.ldo.model.AdditionalArgument;
import sg.codengineers.ldo.model.AdditionalArgument.ArgumentType;
import sg.codengineers.ldo.model.Result;
import sg.codengineers.ldo.model.Task;
import sg.codengineers.ldo.model.Command.CommandType;
import sg.codengineers.ldo.parser.AdditionalArgumentImpl;
import sg.codengineers.ldo.parser.ResultImpl;

public class SearchHandler extends Handler {
	
	String pmOperand;
	boolean isDone = false;
	
	public SearchHandler(List<Task> _taskList) {
		super(_taskList);
	}

	@Override
	public Result execute(String primaryOperand,
			Iterator<AdditionalArgument> iterator) {
		isDone = false;
		pmOperand = primaryOperand;
		Result result = null;
		
		boolean isOpEmpty = primaryOperand == null || primaryOperand.equals("");
		boolean isItEmpty = iterator == null || !iterator.hasNext();
		
		if(isOpEmpty && isItEmpty){
			return new ResultImpl(CommandType.INVALID, 
					"Search for nothing is not allowed!",
					new Time(System.currentTimeMillis()));
		}
		
		if(!isOpEmpty && isItEmpty){
			List<Task> resultList = new ArrayList<Task>(_taskList);
			AdditionalArgument arg = new AdditionalArgumentImpl(AdditionalArgument.ArgumentType.NAME, primaryOperand);
			resultList = searchTask(resultList, arg);
			String opString = populateAddArg("", arg);
			result = constructResult(opString, eliminateDoneTasks(resultList));
		} 
		
		if(!isItEmpty){
			List<Task> resultList = new ArrayList<Task>(_taskList);
			AdditionalArgument arg = null;
			String opString = "";
			if(!isOpEmpty){
				arg = new AdditionalArgumentImpl(AdditionalArgument.ArgumentType.NAME, primaryOperand);
				resultList = searchTask(resultList, arg);	
				opString = populateAddArg(opString, arg);
			}
			while(iterator.hasNext()){
				arg =  iterator.next();
				isDone = isDone || isTagDone(arg);
				resultList = searchTask(resultList, arg);
				opString = populateAddArg(opString, arg);
			}
			if(!isDone){
				resultList = eliminateDoneTasks(resultList);
			}
			result = constructResult(opString, resultList);
		} 		
		
		return result;
	}
	
	@Override
	protected Result constructResult(String operand, List<Task> list){
		return 	new ResultImpl(CommandType.SEARCH, 
				operand,
				new Time(System.currentTimeMillis()), 
				list);
	}
	@Override
	protected Result constructResult(String operand){
		return new ResultImpl(CommandType.SEARCH,
				operand,
				new Time(System.currentTimeMillis())
				);
	}
	
	private String populateAddArg(String accString, AdditionalArgument arg){
		if(!accString.isEmpty()){
			accString+=" and ";
		}
		accString += arg.getArgumentType().toString() + " "+arg.getOperand();
		return accString;
	}
}
