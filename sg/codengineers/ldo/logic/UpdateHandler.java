package sg.codengineers.ldo.logic;

import java.sql.Time;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import sg.codengineers.ldo.model.AdditionalArgument;
import sg.codengineers.ldo.model.Result;
import sg.codengineers.ldo.model.Task;
import sg.codengineers.ldo.model.Command.CommandType;
import sg.codengineers.ldo.parser.ResultImpl;

public class UpdateHandler extends Handler {
	
	public static final int INVALID_ID = -1;
	
	public UpdateHandler(List<Task> _taskList) {
		super(_taskList);
	}

	@Override
	public Result execute(String primaryOperand, Iterator<AdditionalArgument> iterator) {
		
		Task task = null;
		int taskId = INVALID_ID;
		
		try{
			if(primaryOperand != null){
				taskId = Integer.valueOf(primaryOperand);
				
				Task modifiedTask = new TaskImpl(_taskList.get(taskId));
				
				while(iterator.hasNext()){
					AdditionalArgument arg = iterator.next();
					modifyTask(modifiedTask, arg);
				}
				
				task = modifiedTask;
			}
		} catch (ParseException | IllegalArgumentException e){
			throw new IllegalArgumentException("Unable to parse the given parameters!");
		}
		
		if(taskId == INVALID_ID || task==null){			
			return null;
		} else {
			_taskList.remove(taskId);
			_taskList.add(task);
		}
		
		Result result = new ResultImpl(CommandType.CREATE, 
							primaryOperand,
							new Time(System.currentTimeMillis()), 
							task);
		return result;
	}

}
