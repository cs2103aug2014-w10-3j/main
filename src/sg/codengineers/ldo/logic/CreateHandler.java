package sg.codengineers.ldo.logic;
//@author A0119541J
import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import sg.codengineers.ldo.model.AdditionalArgument;
import sg.codengineers.ldo.model.Command.CommandType;
import sg.codengineers.ldo.model.Result;
import sg.codengineers.ldo.model.Task;
import sg.codengineers.ldo.parser.ResultImpl;

public class CreateHandler extends Handler {

	public CreateHandler(List<Task> taskList) {
		super(taskList);
	}

	@Override
	public Result execute(String primaryOperand,Iterator<AdditionalArgument> iterator) throws IllegalArgumentException{

		if(primaryOperand == null){
			return null;
		}

		Task task = new TaskImpl(primaryOperand);


		AdditionalArgument arg = null;
		while(iterator.hasNext()){
			try{
				arg = iterator.next();
				modifyTask(task, arg);
			} catch (Exception e){
				if(arg != null){
					return new ResultImpl(CommandType.INVALID, 
							"Invalid argument "+arg.getOperand()+".",
							new Time(System.currentTimeMillis()));						
				} else {
					return new ResultImpl(CommandType.INVALID, 
							"Invalid argument.",
							new Time(System.currentTimeMillis()));		
				}

			}
		}

		System.out.println(task);

		_taskList.add(task);
		//wkurniawan07 was here
		List<Task> showList = eliminateDoneTasks(_taskList);
		populateIndexMap(showList);
		Result result = new ResultImpl(CommandType.CREATE, 
				primaryOperand,
				new Time(System.currentTimeMillis()), 
				task);

		return result;

	}

}
