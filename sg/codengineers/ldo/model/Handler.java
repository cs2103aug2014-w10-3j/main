package sg.codengineers.ldo.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import sg.codengineers.ldo.model.AdditionalArgument;

public abstract class Handler {
	public SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
	protected ArrayList<Task> _taskList;
	
	abstract public Result execute(String primaryOperand, Iterator<AdditionalArgument> iterator);
	
	public Handler(ArrayList<Task> taskList){
		this._taskList = taskList;
	}
	protected void modifyTask(Task task, AdditionalArgument arg) {
		String operand = arg.getOperand();
		try{
			switch(arg.getArgumentType()){
			case NAME:
				task.setName(operand);
				break;
			case DEADLINE:
				task.setDeadline(dateFormatter.parse(operand));
				break;
			case TIME:
				String[] sOperand = operand.split("\\s+");
				if(sOperand.length != 2){
					break;
				} else {
					task.setTimeStart(dateFormatter.parse(sOperand[0]));
					task.setTimeEnd(dateFormatter.parse(sOperand[1]));
				}
				break;
			case PRIORITY:
			case DONE:		
			case TAG:
				task.setTag(operand);
				break;
			case DESCRIPTION:
				task.setDescription(operand);
				break;
			case HELP:
			case INVALID:
			default:
				break;
			}
		} catch(ParseException e){
			e.printStackTrace();
		}
	}
	
}
