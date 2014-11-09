package sg.codengineers.ldo.logic;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import sg.codengineers.ldo.model.AdditionalArgument;
import sg.codengineers.ldo.model.Result;
import sg.codengineers.ldo.model.Task;
import sg.codengineers.ldo.model.Command.CommandType;
import sg.codengineers.ldo.parser.AdditionalArgumentImpl;
import sg.codengineers.ldo.parser.ResultImpl;

public class SearchHandler extends Handler {
	
	String pmOperand;
	
	public SearchHandler(List<Task> _taskList) {
		super(_taskList);
	}

	@Override
	public Result execute(String primaryOperand,
			Iterator<AdditionalArgument> iterator) {
		
		pmOperand = primaryOperand;
		Result result = null;
		
		boolean isOpEmpty = primaryOperand == null || primaryOperand.equals("");
		boolean isItEmpty = iterator == null || !iterator.hasNext();
		
		if(!isOpEmpty && isItEmpty){
			List<Task> resultList = new ArrayList<Task>(_taskList);
			resultList = searchTask(resultList, new AdditionalArgumentImpl(AdditionalArgument.ArgumentType.NAME, primaryOperand));
			result = constructResult(primaryOperand, resultList);
			
		} 
		
		if(!isOpEmpty && !isItEmpty){
			List<Task> resultList = new ArrayList<Task>(_taskList);
			resultList = searchTask(resultList, new AdditionalArgumentImpl(AdditionalArgument.ArgumentType.NAME, primaryOperand));
			while(iterator.hasNext()){
				resultList = searchTask(resultList, iterator.next());
			}
			result = constructResult(primaryOperand, resultList);
		} 		
		
		if(isOpEmpty && !isItEmpty){
			List<Task> resultList = new ArrayList<Task>(_taskList);
			while(iterator.hasNext()){
				resultList = searchTask(resultList, iterator.next());
			}
			result = constructResult(primaryOperand, resultList);
		}
		
		if(Logic.DEBUG){
			if(result.getTasksIterator().hasNext()){
				Iterator<Task> debugIter = result.getTasksIterator();
				int cnt = 0;
				while(debugIter.hasNext()){
					System.out.print("Task No."+cnt+": ");
					System.out.println(debugIter.next().getDeadline());
					cnt++;
				}
				System.out.println("The length of the result list is "+ cnt);
				
			}	
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
}
