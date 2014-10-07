package sg.codengineers.ldo.logic;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import sg.codengineers.ldo.model.AdditionalArgument;
import sg.codengineers.ldo.model.Command;
import sg.codengineers.ldo.model.Result;
import sg.codengineers.ldo.model.Task;
import sg.codengineers.ldo.model.Command.CommandType;
import sg.codengineers.ldo.parser.ResultImpl;

public class RetrieveHandler extends Handler {
	
	public RetrieveHandler(List<Task> _taskList) {
		super(_taskList);
	}

	@Override
	public Result execute(String primaryOperand,
			Iterator<AdditionalArgument> iterator) {

		Result result = null;
		
		if(primaryOperand != null){
			int id = Integer.valueOf(primaryOperand);
			result = constructResult(_taskList.get(id));	
		} 
		
		if(primaryOperand == null && 
				(iterator == null || iterator.hasNext())){
			result = constructResult(_taskList);
		}
		
		if(primaryOperand == null &&
				(iterator != null && iterator.hasNext())){
			List<Task> resultList = new ArrayList<Task>(_taskList);
			while(iterator.hasNext()){
				resultList = searchTask(resultList, iterator.next());
			}
			result = constructResult(resultList);
		}
		
		return result;
	}
	
	private Result constructResult(Task task){
		return new ResultImpl(CommandType.SHOW, 
				new Time(System.currentTimeMillis()), 
				task);	
	}
	
	private Result constructResult(List<Task> list){
		return 	new ResultImpl(CommandType.SHOW, 
				new Time(System.currentTimeMillis()), 
				list);
	}

}
