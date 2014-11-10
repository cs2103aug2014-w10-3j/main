package sg.codengineers.ldo.logic;
//@author A0119541J
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
	
	public UpdateHandler(List<Task> taskList) {
		super(taskList);
	}

	@Override
	public Result execute(String primaryOperand, Iterator<AdditionalArgument> iterator) {
		
		Task task = null;
		int taskId = INVALID_ID;
		
		try{
			if(primaryOperand != null){
				taskId = Integer.valueOf(primaryOperand) - DIFFERENCE_DIPSLAY_INDEX_AND_SYSTEM_INDEX;
				
				Task modifiedTask = new TaskImpl(getTask(indexMap.get(taskId)));
				
				while(iterator.hasNext()){
					AdditionalArgument arg = iterator.next();
					modifyTask(modifiedTask, arg);
				}
				
				task = modifiedTask;
			}
		} catch (Exception e){
			if(Logic.DEBUG){
				e.printStackTrace();
			}
			return new ResultImpl(CommandType.INVALID, 
					primaryOperand,
					new Time(System.currentTimeMillis()), 
					task);
			
		}
		
		if(taskId == INVALID_ID || task==null){			
			return new ResultImpl(CommandType.INVALID, 
					primaryOperand,
					new Time(System.currentTimeMillis()), 
					task);
		} else {
			getTask(indexMap.get(taskId)).setParams(task);
		}
		
		Result result = new ResultImpl(CommandType.UPDATE, 
							primaryOperand,
							new Time(System.currentTimeMillis()), 
							task);
		return result;
	}

}
