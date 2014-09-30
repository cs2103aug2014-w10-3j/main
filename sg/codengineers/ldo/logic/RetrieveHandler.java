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

public class RetrieveHandler extends Handler {
	
	public RetrieveHandler(ArrayList<Task> taskList) {
		super(taskList);
	}

	@Override
	public Result execute(String primaryOperand,
			Iterator<AdditionalArgument> iterator) {

		Result result = null;
		
		if(primaryOperand == null){
			result = new ResultImpl(CommandType.SHOW, 
							new Time(System.currentTimeMillis()), 
							_taskList);
		} else {
			int id = Integer.valueOf(primaryOperand);
			result = new ResultImpl(CommandType.SHOW, 
					new Time(System.currentTimeMillis()), 
					_taskList.get(id));			
		}
		
		//TODO: Deal with Additional Arguments
		
		return result;
	}

}
