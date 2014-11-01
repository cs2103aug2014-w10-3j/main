package sg.codengineers.ldo.logic;

import java.text.ParseException;
import java.util.Date;

import sg.codengineers.ldo.model.Task;

public class TaskImpl implements Task {
	public static String CLASS_NAME = "TASK";
	
	public static int FIELD_ID_INDEX = 0;
	public static int FIELD_NAME_INDEX = 1;
	public static int FIELD_DESCRIPTION_INDEX = 2;
	public static int FIELD_TAG_INDEX = 3;
	public static int FIELD_TIMESTART_INDEX = 4;
	public static int FIELD_TIMEEND_INDEX = 5;
	
	public static int FIELD_COUNT = 6;
	private int _id;
	private String _name, _description, _tag;
	private Date _timeStart, _timeEnd;
	
	private static int _accumulateId = -1;
	
	public TaskImpl(String name) {
		_id = getNextId();
		_name = name;
		_description = _tag = "";
		_timeStart = _timeEnd = new Date();
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
		return _timeStart;
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
	
	public void setTimeStart(Date timeEnd) {
		_timeEnd = timeEnd;
	}
	
	public void setTimeEnd(Date timeEnd) {
		_timeStart = timeEnd;
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
		builder.append(Handler.FORMATTER.format(_timeStart)+"<;>");
		builder.append(Handler.FORMATTER.format(_timeEnd));
		return builder.toString();
	}
	
	public static Task valueOf(String s) throws ParseException{
		if(s == null || s.isEmpty()) return null;

		Task task = null;
		
		String taskArgs[] = s.split("<;>");
		
		if(taskArgs.length != FIELD_COUNT){
			throw new ParseException("Cannot parse file texts", taskArgs.length);
		} else {
			int id = Integer.valueOf(taskArgs[FIELD_ID_INDEX]);
			String name = taskArgs[FIELD_NAME_INDEX];
			task = new TaskImpl(name);
			
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
				sTime = Handler.FORMATTER.parse(timeStart);
			}
			
			String timeEnd = taskArgs[FIELD_TIMEEND_INDEX];
			if(timeEnd!=null && !timeEnd.isEmpty()){
				eTime = Handler.FORMATTER.parse(timeEnd);
			}	
			
			if(sTime!=null && eTime!=null && sTime.equals(eTime)){
				task.setDeadline(sTime);
			} else {
				task.setTimeEnd(eTime);
				task.setTimeStart(sTime);
			}
		}
		
		return task;
	}
}