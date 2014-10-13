package sg.codengineers.ldo.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import sg.codengineers.ldo.model.AdditionalArgument;
import sg.codengineers.ldo.model.Result;
import sg.codengineers.ldo.model.Task;
import sg.codengineers.ldo.logic.Filter;

public abstract class Handler {
	
	public static boolean DEBUG_MODE = true;
	public static SimpleDateFormat FORMATTER = new SimpleDateFormat("dd/MM/yyyy");
	
	protected List<Task> _taskList;
	
	/**
	 * Left for implementation classes to complete. It does the actual operation.
	 * @param primaryOperand
	 * @param iterator
	 * @return a {@link sg.codengineers.ldo.modle.Result Result} object containing the retrieval result
	 * or feedbacks from other operations.
	 */
	abstract public Result execute(String primaryOperand, Iterator<AdditionalArgument> iterator);
	
	public Handler(List<Task> taskList){
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
				task.setDeadline(FORMATTER.parse(operand));
				break;
			case TIME:
				String[] sOperand = operand.split("\\s+");
				if(sOperand.length != 2){
					break;
				} else {
					task.setTimeStart(FORMATTER.parse(sOperand[0]));
					task.setTimeEnd(FORMATTER.parse(sOperand[1]));
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
	
	protected List<Task> searchTask(List<Task> list, AdditionalArgument arg){
		List<Task> newList = new ArrayList<Task>(list);
		final String operand = arg.getOperand();
		try{
			switch(arg.getArgumentType()){
			case NAME:
				newList = filter(newList, new Filter<Task>(){
					@Override
					public boolean call(Task task){
						return task.getName().contains(operand);
					}
				});
				break;
			case DEADLINE:
				final Date deadline = FORMATTER.parse(operand);
				newList = filter(newList, new Filter<Task>(){
					@Override
					public boolean call(Task task){
						Date dd = task.getDeadline();
						return dd.compareTo(deadline) <=0 &&
								dd.compareTo(new Date()) >=0;
					}
				});
				break;
			case TIME:
				String[] sOperand = operand.split("\\s+");
				if(sOperand.length != 2){
					throw new IllegalArgumentException("Please specify both start date and end date");
				} else {
					final Date startDate = FORMATTER.parse(sOperand[0]);
					final Date endDate = FORMATTER.parse(sOperand[1]);
					newList = filter(newList, new Filter<Task>(){
						@Override
						public boolean call(Task task){
							Date ts = task.getStartTime();
							Date te = task.getEndTime();
							if(ts == null || te == null){
								return false;
							} else {
								return (ts.compareTo(endDate) <= 0 && ts.compareTo(startDate)>=0) ||
										(te.compareTo(startDate)>=0 && te.compareTo(endDate)<=0);
							}
						}
					});
				}
				break;
			case PRIORITY:				
			case DONE:		
			case TAG:
				newList = filter(newList, new Filter<Task>(){
					@Override
					public boolean call(Task task){
						return task.getTag().equalsIgnoreCase(operand);
					}
				});				
				break;
			case DESCRIPTION:
				newList = filter(newList, new Filter<Task>(){
					@Override
					public boolean call(Task task){
						return task.getDescription().contains(operand);
					}
				});
				break;
			case HELP:
			case INVALID:
			default:
				break;
			}
		} catch(ParseException e){
			e.printStackTrace();
		}		
		return  newList;
	}

	private List<Task> filter(List<Task> list, Filter<Task> f){
		List<Task> newList = new ArrayList<Task>();
		
		for(Task task : list){
			if(f.call(task)){
				newList.add(task);
			}
		}
		return newList;
	}
}
