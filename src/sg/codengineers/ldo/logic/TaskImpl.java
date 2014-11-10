package sg.codengineers.ldo.logic;
//@author A0119541J
import java.text.ParseException;
import java.util.Date;

import sg.codengineers.ldo.model.Parser;
import sg.codengineers.ldo.model.Task;
import sg.codengineers.ldo.parser.ParserImpl;

public class TaskImpl implements Task {
	public static String CLASS_NAME = "TASK";
	public static Parser parser = new ParserImpl();
	
	public static int FIELD_ID_INDEX = 0;
	public static int FIELD_NAME_INDEX = 1;
	public static int FIELD_DESCRIPTION_INDEX = 2;
	public static int FIELD_TAG_INDEX = 3;
	public static int FIELD_TIMESTART_INDEX = 4;
	public static int FIELD_TIMEEND_INDEX = 5;
	public static int FIELD_PRIORITY_INDEX = 6;
	public static int FIELD_DELETED_INDEX = 7;
	
	public static int FIELD_COUNT = 7;
	public static int FIELD_COUNT_DELETED = 8;
	
	private int _id;
	private String _name, _description, _tag;
	private Date _timeStart, _timeEnd;
	private Priority _priority;
	
	private static int _accumulateId = -1;
	
	public TaskImpl(String name) {
		_id = getNextId();
		_name = name;
		_description = _tag = "";
		_timeStart = _timeEnd = null;
		_priority = Priority.NORMAL;
	}
	
	/**
	 * Construct a new task by copying all information from the input task.
	 * @param task The {@link Task} object from which the information will be copied
	 */
	public TaskImpl(Task task){
		this._id = task.getId();
		this._name = task.getName();
		this._tag = task.getTag();
		this._timeStart = task.getStartTime();
		this._timeEnd = task.getEndTime();
		this._description = task.getDescription();
		this._priority = task.getPriority();
	}
	
	/**
	 * Constructs a Task object for manipulation
	 */
	public TaskImpl() {
		
	}
	
	/**
	 * Generate a unique ID for a new task.
	 * @return the unique ID in integer format.
	 */
	public static int getNextId(){
		_accumulateId ++;
		return _accumulateId;
	}
	
	@Override
	public int getId() {
		return _id;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public String getTag() {
		return _tag;
	}
	
	@Override
	public String getDescription() {
		return _description;
	}

	@Override
	public Date getStartTime() {
		return _timeStart;
	}

	@Override
	public Date getEndTime() {
		return _timeEnd;
	}

	@Override
	public Date getDeadline() {
		// Deadline tasks have the same start and end time
		if(_timeStart!=null){
			if(_timeStart.equals(_timeEnd)){
				return _timeStart;
			}		
		}

		return null;
	}
	
	public void setId(int id) {
		_id = id;	
	}
	
	public void setName(String name){
		_name = name;
	}
	
	public void setTag(String tag) {
		_tag = tag;
	}
	
	public void setDescription(String description) {
		_description = description;
	}
	
	public void setTimeStart(Date timeStart) {
		_timeStart = timeStart;
	}
	
	public void setTimeEnd(Date timeEnd) {
		_timeEnd = timeEnd;
	}
	
	public void setDeadline(Date deadline) {
		_timeStart = deadline;
		_timeEnd = deadline;
	}
	
	@Override
	public String toString(){	
		StringBuilder builder = new StringBuilder();
		builder.append(_id+"<;>");
		builder.append(_name+"<;>");
		builder.append(_description+"<;>");
		builder.append(_tag+"<;>");
		if(_timeStart != null && _timeEnd !=null){
			builder.append(parser.parseDateToString(_timeStart)+"<;>");
			builder.append(parser.parseDateToString(_timeEnd)+"<;>");		
		}else{
			builder.append("<;>");
			builder.append("<;>");
		}

		builder.append(_priority);
		return builder.toString();
	}
	
	public static Task valueOf(String s) throws ParseException{
		if(s == null || s.isEmpty()) return null;

		Task task = null;
		
		String taskArgs[] = s.split("<;>");
		
		if(taskArgs.length != FIELD_COUNT && taskArgs.length!= FIELD_COUNT_DELETED){
			throw new ParseException("Cannot parse file texts", taskArgs.length);
		} else {
			
			int id = Integer.valueOf(taskArgs[FIELD_ID_INDEX]);
			
			if(_accumulateId <= id){
				_accumulateId = id + 1;
			}
			
			if(taskArgs.length == FIELD_COUNT_DELETED){
				return null;
			}
			
			String name = taskArgs[FIELD_NAME_INDEX];
			task = new TaskImpl(name);
			task.setId(id);
			
			String description = taskArgs[FIELD_DESCRIPTION_INDEX];
			if(description!= null && !description.isEmpty()){
				task.setDescription(description);
			}
			
			String tag = taskArgs[FIELD_TAG_INDEX];
			if(tag!= null && !tag.isEmpty()){
				task.setTag(tag);
			}				
			
			Date sTime = null;
			Date eTime = null;
			
			String timeStart = taskArgs[FIELD_TIMESTART_INDEX];
			if(timeStart!=null && !timeStart.isEmpty()){
				sTime = parser.parseToDate(timeStart);
			}
			
			String timeEnd = taskArgs[FIELD_TIMEEND_INDEX];
			if(timeEnd!=null && !timeEnd.isEmpty()){
				eTime = parser.parseToDate(timeEnd);
			}	
			
			if(sTime!=null && eTime!=null && sTime.equals(eTime)){
				task.setDeadline(sTime);
			} else {
				task.setTimeEnd(eTime);
				task.setTimeStart(sTime);
			}
			
			Priority priority = Priority.fromString(taskArgs[FIELD_PRIORITY_INDEX]);
			if(priority != null){
				task.setPriority(priority);
			}

		}
		
		return task;
	}

	@Override
	public Priority getPriority() {
		return _priority;
	}

	@Override
	public void setPriority(Priority priority) {
		this._priority = priority;
	}

	@Override
	public void setParams(Task task) {
		this._id = task.getId();
		this._name = task.getName();
		this._tag = task.getTag();
		this._timeStart = task.getStartTime();
		this._timeEnd = task.getEndTime();
		this._description = task.getDescription();
		this._priority = task.getPriority();
	}
}
