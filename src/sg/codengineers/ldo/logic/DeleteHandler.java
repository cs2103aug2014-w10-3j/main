package sg.codengineers.ldo.logic;
//@author A0119541J
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

	public DeleteHandler(List<Task> taskList) {
		super(taskList);
	}

	@Override
	public Result execute(String primaryOperand,
			Iterator<AdditionalArgument> iterator) {
		
		Result result = null;
		Task task = null;
		if(primaryOperand.equalsIgnoreCase("all")){
			List<Task> theList = new ArrayList<Task>(_taskList);
			_taskList.clear();
			indexMap.clear();
			result = new ResultImpl(CommandType.DELETE, 
					primaryOperand,
					new Time(System.currentTimeMillis()), 
					theList);	
			return result;
		}
		int id = -1;
		try{
			id = Integer.valueOf(primaryOperand) - DIFFERENCE_DIPSLAY_INDEX_AND_SYSTEM_INDEX;
			task = getTask((int)indexMap.get(id));
			_taskList.remove(task);
			indexMap.remove(Integer.valueOf(id));
		} catch(Exception e){
			if(Logic.DEBUG){
				e.printStackTrace();
			}
			return new ResultImpl(CommandType.INVALID, 
					"Task "+primaryOperand+" doesn't exist.",
					new Time(System.currentTimeMillis()));		
		}
		//wkurniawan07 was here
		List<Task> showList = eliminateDoneTasks(_taskList);
		populateIndexMap(showList);
		result = new ResultImpl(CommandType.DELETE, 
				primaryOperand,
				new Time(System.currentTimeMillis()), 
				task);		
		return result;
	}

}
