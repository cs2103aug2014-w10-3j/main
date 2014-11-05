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

public class DeleteHandler extends Handler {

	
	public DeleteHandler(List<Task> _taskList) {
		super(_taskList);
	}

	@Override
	public Result execute(String primaryOperand,
			Iterator<AdditionalArgument> iterator) {
		
		Result result = null;
		Task task = null;
		
		if(primaryOperand.equalsIgnoreCase("all")){
			List<Task> theList = new ArrayList<Task>(_taskList);
			_taskList.clear();
			result = new ResultImpl(CommandType.DELETE, 
					primaryOperand,
					new Time(System.currentTimeMillis()), 
					theList);	
			return result;
		}
		
		int id = Integer.valueOf(primaryOperand) - DIFFERENCE_DIPSLAY_INDEX_AND_SYSTEM_INDEX;
		try{
			task = _taskList.remove(id);
		} catch(Exception e){
			if(Logic.DEBUG){
				e.printStackTrace();
			}
			throw e;
		}
		
		result = new ResultImpl(CommandType.DELETE, 
				primaryOperand,
				new Time(System.currentTimeMillis()), 
				task);		
		return result;
	}

}
