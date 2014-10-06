package sg.codengineers.ldo.logic;

import java.sql.Time;
import java.util.Iterator;
import java.util.List;

import sg.codengineers.ldo.model.AdditionalArgument;
import sg.codengineers.ldo.model.Result;
import sg.codengineers.ldo.model.Task;
import sg.codengineers.ldo.model.Command.CommandType;
import sg.codengineers.ldo.parser.ResultImpl;

public class UpdateHandler extends Handler {
	
	public UpdateHandler(List<Task> _taskList) {
		super(_taskList);
	}

	@Override
	public Result execute(String primaryOperand,
			Iterator<AdditionalArgument> iterator) {
		
		Task task = null;
		
		if(primaryOperand != null){
			int id = Integer.valueOf(primaryOperand);
			
			task = _taskList.get(id);
			
			while(iterator.hasNext()){
				AdditionalArgument arg = iterator.next();
				modifyTask(task, arg);
			}
			
		}
		
		Result result = new ResultImpl(CommandType.CREATE, 
						new Time(System.currentTimeMillis()), 
						task);
		return result;
	}

}
