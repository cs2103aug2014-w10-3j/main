package sg.codengineers.ldo.logic;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import sg.codengineers.ldo.model.AdditionalArgument;
import sg.codengineers.ldo.model.Result;
import sg.codengineers.ldo.model.Task;
import sg.codengineers.ldo.model.Command.CommandType;
import sg.codengineers.ldo.parser.AdditionalArgumentImpl;
import sg.codengineers.ldo.parser.ResultImpl;

public class RetrieveHandler extends Handler {
	
	String pmOperand;
	
	public RetrieveHandler(List<Task> _taskList) {
		super(_taskList);
	}

	@Override
	public Result execute(String primaryOperand,
			Iterator<AdditionalArgument> iterator) {
		
		pmOperand = primaryOperand;
		Result result = null;
		
		boolean isOpEmpty = primaryOperand == null || primaryOperand.equals("");
		boolean isItEmpty = iterator == null || !iterator.hasNext();
		
		if(!isOpEmpty){
			
			if(primaryOperand.equalsIgnoreCase("welcome")){
				result = constructResult(primaryOperand);
			} else {
				int id;
				try{
					id = Integer.valueOf(primaryOperand) - DIFFERENCE_DIPSLAY_INDEX_AND_SYSTEM_INDEX;
					result = constructResult(_taskList.get(id));
				} catch(NumberFormatException e){
					List<Task> resultList = new ArrayList<Task>(_taskList);
					resultList = searchTask(resultList, new AdditionalArgumentImpl("name", primaryOperand));
					result = constructResult(resultList);
				}
			}
			
		} 
		
		if(isOpEmpty && isItEmpty){
			List<Task> resultList = new ArrayList<Task>(_taskList);
			resultList = searchTask(resultList, new AdditionalArgumentImpl("deadline", FORMATTER.format(new Date())));
			result = constructResult(resultList);
		}
		
		if(isOpEmpty && !isItEmpty){
			List<Task> resultList = new ArrayList<Task>(_taskList);
			while(iterator.hasNext()){
				resultList = searchTask(resultList, iterator.next());
			}
			result = constructResult(resultList);
		}
		
		if(DEBUG_MODE){
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
	
	private Result constructResult(Task task){
		return new ResultImpl(CommandType.RETRIEVE, 
				pmOperand,
				new Time(System.currentTimeMillis()), 
				task);	
	}
	
	private Result constructResult(List<Task> list){
		return 	new ResultImpl(CommandType.RETRIEVE, 
				pmOperand,
				new Time(System.currentTimeMillis()), 
				list);
	}
	private Result constructResult(String message){
		return new ResultImpl(CommandType.RETRIEVE,
				pmOperand,
				new Time(System.currentTimeMillis())
				);
	}

}
