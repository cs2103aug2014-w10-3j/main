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

public class HelpHandler{
	public static final String MSG_AA_TIME="\t\t-t/--time <start time> <end time>\t\t\t\t set the start time and end time for the task.\n";
	public static final String MSG_AA_DEADLINE = "\t\t-d/--deadline <time>\t\t\t\t set the deadline time for the task.\n";
	public static final String MSG_AA_NAME = "\t\t-n/--name <task name>\t\t\t\t set the name for the task.\n";
	public static final String MSG_AA_PRIORITY = "\t\t-p/--priority <priority level>\t\t\t\t set the priority level for the task. Can only be LOW, MEDIUM or HIGH.\n";
	public static final String MSG_AA_TAG = "\t\t--tag <tag name>\t\t\t\t set the tag for the task.\n";
	public static final String MSG_AA_DESCRIPTION = "\t\t-a/--description <description text> set the description for the task.\n";
	public static final String MSG_AA_HELP = "\t\t-h/--help \t\t\t\t display the help message for the command.";
	public static final String MSG_AA_ALL =MSG_AA_TIME + MSG_AA_DEADLINE+MSG_AA_NAME + MSG_AA_PRIORITY+MSG_AA_TAG+MSG_AA_DESCRIPTION + MSG_AA_HELP;
	public static final String HELP_MSG_CREATE = "Help message for command *add*\n\tUsage: add <taskname> [<parameters>]\n\tParameters:\n"+MSG_AA_ALL;
	public static final String HELP_MSG_SEARCH = "Help message for command *search*\n\tUsage: search <taskname> \n\t\t search [<parameters>]\n\tParameters:\n"+MSG_AA_ALL;
	public static final String HELP_MSG_UPDATE = "Help message for command *update*\n\tUsage: update <task index> [<parameters>]\n\tParameters:\n"+MSG_AA_ALL;
	public static final String HELP_MSG_DELETE = "Help message for command *delete*\n\tUsage: delete <task index>";
	public static final String HELP_MSG_SHOW = "Help mesasge for command *show*\n\tUsage: show\n\t\tshow <task index>";
	public static final String HELP_MSG_HELP = "Help message for command *help*\n\tUsage: help <command> [<parameters>]\n\t"
												+"Note: Currently support add, update, delete, show, search, help.\n\tParameters:\n" + MSG_AA_HELP;
	
	private Map<CommandType, String> _map = new HashMap<CommandType, String>();
	
	public HelpHandler(List<Task> _taskList) {
		_map.put(CommandType.CREATE, HELP_MSG_CREATE);
		_map.put(CommandType.UPDATE, HELP_MSG_UPDATE);
		_map.put(CommandType.SEARCH, HELP_MSG_SEARCH);
		_map.put(CommandType.DELETE, HELP_MSG_DELETE);
		_map.put(CommandType.RETRIEVE, HELP_MSG_SHOW);
		_map.put(CommandType.HELP, HELP_MSG_HELP);
		
	}

	public Result execute(CommandType type) {
		Result result;
		try{
			result =  new ResultImpl(type, _map.get(type), new Time(System.currentTimeMillis()));
		} catch(Exception e){
			if(Logic.DEBUG){
				e.printStackTrace();
			}
			result = new ResultImpl(CommandType.INVALID, "Invalid command type "+type+" for help.", new Time(System.currentTimeMillis()));
		}
		
		return result;
		
	}

}
