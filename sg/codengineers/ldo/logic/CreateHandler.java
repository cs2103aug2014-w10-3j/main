package sg.codengineers.ldo.logic;

import java.sql.Time;
import java.util.Iterator;
import java.util.List;

import sg.codengineers.ldo.model.AdditionalArgument;
import sg.codengineers.ldo.model.Command.CommandType;
import sg.codengineers.ldo.model.Result;
import sg.codengineers.ldo.model.Task;
import sg.codengineers.ldo.parser.ResultImpl;

public class CreateHandler extends Handler {
	
	public CreateHandler(List<Task> _taskList) {
		super(_taskList);
	}

	@Override
	public Result execute(String primaryOperand,Iterator<AdditionalArgument> iterator) {
		if(primaryOperand == null){
			return null;
		}
		
		Task task = new TaskImpl(TaskImpl.getNextId(), primaryOperand);
		
		while(iterator.hasNext()){
			AdditionalArgument arg = iterator.next();
			modifyTask(task, arg);
		}
		
		_taskList.add(task);
		
		Result result = new ResultImpl(CommandType.CREATE, 
							primaryOperand,
							new Time(System.currentTimeMillis()), 
							task);
		
		return result;
	}

}
