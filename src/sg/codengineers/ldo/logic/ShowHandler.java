package sg.codengineers.ldo.logic;

import java.sql.Time;
import java.util.List;

import sg.codengineers.ldo.model.Result;
import sg.codengineers.ldo.model.Task;
import sg.codengineers.ldo.model.Command.CommandType;
import sg.codengineers.ldo.parser.ResultImpl;

public class ShowHandler {
	private List<Task> _taskList;
	public ShowHandler(List<Task> taskList){
		this._taskList = taskList;
	}
	
	public Result execute(int index){
		if(index == -1){
			return new ResultImpl(CommandType.RETRIEVE, 
					String.valueOf(index),
					new Time(System.currentTimeMillis()), 
					_taskList);	 
		}
		Task task = null;
		try{
			task = _taskList.get(index - 1);
		} catch(Exception e){
			if(Logic.DEBUG){
				e.printStackTrace();
			}
			return new ResultImpl(CommandType.INVALID, 
					"Task "+index+" doesn't exist.",
					new Time(System.currentTimeMillis()));
		}
		
		return new ResultImpl(CommandType.RETRIEVE, 
				String.valueOf(index),
				new Time(System.currentTimeMillis()), 
				task);	
	}
}
