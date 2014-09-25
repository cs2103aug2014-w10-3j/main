package sg.codengineers.ldo.model;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class Handler {
	protected ArrayList<Task> _taskList;
	
	abstract public Result execute(Command command);
	
	public Handler(ArrayList<Task> taskList){
		this._taskList = taskList;
	}
	
	protected static String getPrimaryOperand(Command command){
		return command.getPrimaryOperand();
	}
	
	protected static Iterator<AdditionalArgument> getAdditionalArgument(Command command){
		return command.getIterator();
	}
}
