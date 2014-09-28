package sg.codengineers.ldo.logic;

import java.util.Date;

import sg.codengineers.ldo.model.Task;

public class TaskImpl implements Task {
	private int _id;
	private String _name, _description, _tag;
	private Date _timeStart, _timeEnd;
	
	public TaskImpl(int id, String name) {
		_id = id;
		_name = name;
		_description = _tag = "";
		_timeStart = _timeEnd = new Date();
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
	
	private void setName(String name){
		_name = name;
	}
	
	private void setTag(String tag) {
		_tag = tag;
	}
	
	private void setDescription(String description) {
		_description = description;
	}
	
	private void setTimeStart(Date timeEnd) {
		_timeEnd = timeEnd;
	}
	
	private void setTimeEnd(Date timeEnd) {
		_timeStart = timeEnd;
	}
	
	private void setDeadline(Date deadline) {
		_timeStart = deadline;
		_timeEnd = deadline;
	}
	
}
