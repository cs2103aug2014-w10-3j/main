package sg.codengineers.ldo.logic;

import java.sql.Time;
import java.util.Iterator;
import java.util.List;

import sg.codengineers.ldo.model.AdditionalArgument;
import sg.codengineers.ldo.model.Result;
import sg.codengineers.ldo.model.Task;
import sg.codengineers.ldo.model.Command.CommandType;
import sg.codengineers.ldo.parser.ResultImpl;

public class ShowHandler extends Handler {
	public ShowHandler(List<Task> taskList) {
		super(taskList);
	}

	public Result execute(int index) {
		if (index == -1) {
			List<Task> showList = eliminateDoneTasks(_taskList);
			populateIndexMap(showList);
			return new ResultImpl(CommandType.RETRIEVE, "all", new Time(
					System.currentTimeMillis()),
					showList);
		}
		Task task = null;
		try {
			task = _taskList.get(index - 1);
		} catch (Exception e) {
			if (Logic.DEBUG) {
				e.printStackTrace();
			}
			return new ResultImpl(CommandType.INVALID, "Task " + index
					+ " doesn't exist.", new Time(System.currentTimeMillis()));
		}
		return new ResultImpl(CommandType.RETRIEVE, String.valueOf(index),
				new Time(System.currentTimeMillis()), task);
	}

	@Override
	public Result execute(String primaryOperand,
			Iterator<AdditionalArgument> iterator)
			throws IllegalArgumentException {
		return execute(Integer.valueOf(primaryOperand));
	}
}
