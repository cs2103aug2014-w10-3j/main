package sg.codengineers.ldo.logic;

import java.sql.Time;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import sg.codengineers.ldo.model.AdditionalArgument;
import sg.codengineers.ldo.model.Command.CommandType;
import sg.codengineers.ldo.model.Result;
import sg.codengineers.ldo.model.Task;
import sg.codengineers.ldo.parser.ResultImpl;

public class HelpHandler extends Handler {
	public static final String HELP_MSG_CREATE = "This is the help message for CREATE";
	public static final String HELP_MSG_RETRIEVE = "This is the help message for RETRIEVE";
	public static final String HELP_MSG_UPDATE = "This is the help message for UPDATE";
	public static final String HELP_MSG_DELETE = "This is the help message for DELETE";
	public static final String HELP_MSG_HELP = "This is the help message for HELP";
	
	
	
	private Map<CommandType, String> _map = new HashMap<CommandType, String>();
	
	
	public HelpHandler(List<Task> _taskList) {
		super(_taskList);
		_map.put(CommandType.CREATE, HELP_MSG_CREATE);
		_map.put(CommandType.UPDATE, HELP_MSG_UPDATE);
		_map.put(CommandType.RETRIEVE, HELP_MSG_RETRIEVE);
		_map.put(CommandType.DELETE, HELP_MSG_DELETE);
		_map.put(CommandType.HELP, HELP_MSG_HELP);
		
	}

	public Result execute(CommandType type) throws IllegalArgumentException {
		return new ResultImpl(type, _map.get(type), new Time(System.currentTimeMillis()));
	}

	@Override
	public Result execute(String primaryOperand,
			Iterator<AdditionalArgument> iterator)
			throws IllegalArgumentException {
		return null;
	}

}
