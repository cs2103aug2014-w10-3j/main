package sg.codengineers.ldo.logic;

import java.util.Date;

import sg.codengineers.ldo.model.Task;

public class TaskImpl implements Task {
	private int _id;
	private String _name, _description, _tag;
	private Date _timeStart, _timeEnd;
	
	private static int _accumulateId = -1;
	
	public TaskImpl(int id, String name) {
		_id = id;
		_name = name;
		_description = _tag = "";
		_timeStart = _timeEnd = new Date();
	}
	
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
		// Deadlined tasks have the same start and end time
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
}
