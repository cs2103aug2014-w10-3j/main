package sg.codengineers.ldo.logic;

import java.util.ArrayList;

import sg.codengineers.ldo.model.Command;
import sg.codengineers.ldo.model.Handler;
import sg.codengineers.ldo.model.Result;
import sg.codengineers.ldo.model.Task;

public class DeleteHandler extends Handler {

	
	public DeleteHandler(ArrayList<Task> taskList) {
		super(taskList);
	}

	@Override
	public Result execute(Command command) {
		// TODO Auto-generated method stub
		return null;
	}

}
