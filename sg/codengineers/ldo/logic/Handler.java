package sg.codengineers.ldo.logic;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import sg.codengineers.ldo.model.AdditionalArgument;
import sg.codengineers.ldo.model.Result;
import sg.codengineers.ldo.model.Task;
import sg.codengineers.ldo.model.Command.CommandType;
import sg.codengineers.ldo.parser.ResultImpl;
import sg.codengineers.ldo.logic.Filter;

/**
 * Abstract class for logic operations. <br>
 * It provides some basic utility functions for task manipulations.
 * Concrete logic operation classes need to implement {@link #execute(String, Iterator)} method
 * and fill in the actual execution there.
 * @author Wenhao
 *
 */
public abstract class Handler {
	
	public static boolean DEBUG_MODE = false;
	public final static SimpleDateFormat FORMATTER = new SimpleDateFormat("dd/MM/yyyy");
	protected List<Task> _taskList;
	
	/**
	 * The actual operation needs to be implemented in concrete classes.
	 * @param primaryOperand
	 * @param iterator
	 * @return a {@link sg.codengineers.ldo.model.Result Result} object containing the retrieval result
	 * or feedbacks from other operations. Will return <code>null</code> if the execution is unsuccessful.
	 * @throws IllegalArgumentException Thrown when the handler cannot parse the input parameters.
	 */
	abstract public Result execute(String primaryOperand, Iterator<AdditionalArgument> iterator) throws IllegalArgumentException;
	
	public Handler(List<Task> taskList){
		this._taskList = taskList;
	}
	
	/**
	 * Modify a task according to the input {@link AdditionalArgument}. <br>
	 * 
	 * <ul>
	 * <li>Argument type <code>HELP</code> is not implemented yet.</li>
	 * <li>Argument type <code>PRIORITY DONE TAG</code> are written into 
	 * a same field <code>_tag</code> in {@link TaskImpl}.</li>
	 * <li>No exception will be thrown!</li>
	 * </ul>
	 * 
	 * @param task the {@link Task} object to be modified.
	 * @param arg the {@link AdditionalArgument} containing modification information.
	 */
	protected void modifyTask(Task task, AdditionalArgument arg) throws ParseException, IllegalArgumentException{
		String operand = arg.getOperand();

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
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Search through a {@link List} of {@link Task} according to the input {@link AdditionalArgument}.
	 * 
	 * <ul>
	 * <li><code>name</code> field and <code>description</code> field are checked using 
	 * {@link String#contains(String)} method.</li>
	 * <li><code>tag</code> field is checked using {@link String#equalsIgnoreCase(String)} method.</li>
	 * <li><code>deadline</code> field is checked using {@link Date#compareTo(Date)} method. A task will
	 * be taken as a valid return value if and only if:
	 * 		<ol><li><code>taskDeadline >= today</code> and,</li> <li><code>taskDeadline <= inputDeadline</code></li></ol>
	 * <li><code>time</code> field is checked using {@link Date#compareTo(Date)} method. A task will be 
	 * taken as a valid return value if and only if its <code>time</code> field overlaps with the input time.</li>
	 * </ul>
	 * 
	 * @param list a {@link List} of {@link Task} objects from which the 
	 * searching result will be constructed.
	 * @param arg a {@link AdditionalArgument} object containing the search parameter.
	 * @return a {@link List} of {@link Task} objects containing the search result.
	 * Note that this return list contains the original references to the task objects
	 * which are directly obtained from the input list.
	 */
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

	/**
	 * Apply a {@link Filter} to all input {@link List} of {@link Task} objects and construct a 
	 * return {@link List} of tasks that are evaluated to <code>true</code> by the {@link Filter}.
	 * @param list a {@link List} of {@link Task} objects.
	 * @param f a {@link Filter} that implements a {@link Filter#call(Object)} method.
	 * @return a {@link List} of {@link Task} objects. Note that the task references inside the list
	 * refer to the same tasks in the input list.
	 */
	private List<Task> filter(List<Task> list, Filter<Task> f){
		List<Task> newList = new ArrayList<Task>();
		
		for(Task task : list){
			if(f.call(task)){
				newList.add(task);
			}
		}
		return newList;
	}
	
	/**
	 * Construct a {@link Result} object.
	 * @param operand the primary operand of a {@link Command}
	 * @param task the targeted {@link Task} of the command.
	 * @return a {@link Result} object wrapping the input and current time information.
	 * @see Handler#constructResult(String, List)
	 */
	protected Result constructResult(String operand, Task task){
		return new ResultImpl(CommandType.RETRIEVE, 
				operand,
				new Time(System.currentTimeMillis()), 
				task);	
	}
	
	/**
	 * Construct a {@link Result} object.
	 * @param operand the primary operand of a {@link Command}
	 * @param list the targeted {@link List} of {@link Task} of the command.
	 * @return a {@link Result} object wrapping the input and current time information.
	 * @see Handler#constructResult(String, Task)
	 */	
	protected Result constructResult(String operand, List<Task> list){
		return 	new ResultImpl(CommandType.RETRIEVE, 
				operand,
				new Time(System.currentTimeMillis()), 
				list);
	}	
}
