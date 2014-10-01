package sg.codengineers.ldo.logic;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;

import sg.codengineers.ldo.model.AdditionalArgument;
import sg.codengineers.ldo.model.Command;
import sg.codengineers.ldo.model.Handler;
import sg.codengineers.ldo.model.Result;
import sg.codengineers.ldo.model.Task;
import sg.codengineers.ldo.model.Command.CommandType;
import sg.codengineers.ldo.parser.ResultImpl;

public class DeleteHandler extends Handler {

	
	public DeleteHandler(ArrayList<Task> taskList) {
		super(taskList);
	}

	@Override
	public Result execute(String primaryOperand,
			Iterator<AdditionalArgument> iterator) {
		
		Result result = null;
		Task task = null;
		
		int id = Integer.valueOf(primaryOperand);
		
		for(int i = 0; i < _taskList.size(); i++){
			if(_taskList.get(i).getId() == id){

				task = _taskList.remove(i);
				break;
			}
		}
		
		result = new ResultImpl(CommandType.DELETE, 
				new Time(System.currentTimeMillis()), 
				task);		
		return result;
		
		//TODO: deal with addtional arguments
	}

}