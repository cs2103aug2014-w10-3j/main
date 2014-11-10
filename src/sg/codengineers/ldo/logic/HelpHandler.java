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

public class HelpHandler extends Handler{
//	public  String MSG_AA_TIME = "\t\t-t/--time <start time> <end time>\t\t set the start time and end time for the task.\n";
//	public  String MSG_AA_DEADLINE = "\t\t-d/--deadline <time>\t\t\t\t set the deadline time for the task.\n";
//	public  String MSG_AA_NAME = "\t\t-n/--name <task name>\t\t\t\t set the name for the task.\n";
//	public  String MSG_AA_PRIORITY = "\t\t-p/--priority <priority level>\t\t\t set the priority level for the task. Can only be LOW, MEDIUM or HIGH.\n";
//	public  String MSG_AA_TAG = "\t\t--tag <tag name>\t\t\t\t set the tag for the task.\n";
//	public  String MSG_AA_DESCRIPTION = "\t\t-a/--description <description text>\t\t set the description for the task.\n";
//	public  String MSG_AA_HELP = "\t\t-h/--help \t\t\t\t\t display the help message for the command.";
//	public  String MSG_AA_ALL = MSG_AA_TIME + MSG_AA_DEADLINE
//			+ MSG_AA_NAME + MSG_AA_PRIORITY + MSG_AA_TAG + MSG_AA_DESCRIPTION
//			+ MSG_AA_HELP;
//	public  String HELP_MSG_CREATE = "Help message for command *add*\n\tUsage: add <taskname> [<parameters>]\n\tParameters:\n"
//			+ MSG_AA_ALL;
//	public  String HELP_MSG_SEARCH = "Help message for command *search*\n\tUsage: search <taskname> \n\t\t search [<parameters>]\n\tParameters:\n"
//			+ MSG_AA_ALL;
//	public  String HELP_MSG_UPDATE = "Help message for command *update*\n\tUsage: update <task index> [<parameters>]\n\tParameters:\n"
//			+ MSG_AA_ALL;
//	public  String HELP_MSG_DELETE = "Help message for command *delete*\n\tUsage: delete <task index>";
//	public  String HELP_MSG_SHOW = "Help mesasge for command *show*\n\tUsage: show\n\t\tshow <task index>";
//	public  String HELP_MSG_HELP = "Help message for command *help*\n\tUsage: help <command> [<parameters>]\n\t"
//			+ "Note: Currently support add, update, delete, show, search, help.\n\tParameters:\n"
//			+ MSG_AA_HELP;
	public  String HELP_MSG_CREATE = "Help message for command *add*\n\tUsage: add <taskname> [<parameters>]\n\tParameters:\n\t\t-t/--time <start time> <end time>\t\t set the start time and end time for the task.\n\t\t-d/--deadline <time>\t\t\t\t set the deadline time for the task.\n\t\t-n/--name <task name>\t\t\t\t set the name for the task.\n\t\t-p/--priority <priority level>\t\t\t set the priority level for the task. Can only be LOW, NORMAL or HIGH.\n\t\t--tag <tag name>\t\t\t\t set the tag for the task. note that once a task is tagged \"done\", it will never appear in your task list unless you search for \"--tag done\"\n\t\t --done \t\t\t\t\t alias of \"--tag done\".\n\t\t-a/--description <description text>\t\t set the description for the task.\n\t\t-h/--help \t\t\t\t\t display this help message\n.";
	public  String HELP_MSG_SEARCH = "Help message for command *search*\n\tUsage: search <taskname> \n\t\t search [<parameters>]\n\tParameters:\n\t\t-t/--time <start time> <end time>\t\t search using the start time and end time.\n\t\t-t/--time <time>\t\t\t\t\tsearch both the time and deadline.\n\t\t-d/--deadline <time>\t\t\t\t search the deadline time.\n\t\t-n/--name <task name>\t\t\t\t search the name.\n\t\t-p/--priority <priority level>\t\t\t search the priority level. Can only be LOW, NORMAL or HIGH.\n\t\t--tag <tag name>\t\t\t\t search the tag.\n\t\t --done \t\t\t\t\t alias of \"--tag done\".\n\t\t-a/--description <description text>\t\t search the description.\n\t\t-h/--help \t\t\t\t\t display this help message\n.";
	public  String HELP_MSG_UPDATE = "Help message for command *update*\n\tUsage: update <task index> [<parameters>]\n\tParameters:\n\t\t-t/--time <start time> <end time>\t\t set the start time and end time for the task.\n\t\t-d/--deadline <time>\t\t\t\t set the deadline time for the task.\n\t\t-n/--name <task name>\t\t\t\t set the name for the task.\n\t\t-p/--priority <priority level>\t\t\t set the priority level for the task. Can only be LOW, NORMAL or HIGH.\n\t\t--tag <tag name>\t\t\t\t set the tag for the task. note that once a task is tagged \"done\", it will never appear in your task list unless you search for \"--tag done\"\n\t\t--done \t\t\t\t\t alias of \"--tag done\".\n\t\t-a/--description <description text>\t\t set the description for the task.\n\t\t-h/--help \t\t\t\t\t display this help message\n.";
	public String HELP_MSG_UNDO = "Help message for command *undo*\n\tUsage: undo\n\tDescription: It will recover the previous command. If this is the first command, nothing will be done.";
	public  String HELP_MSG_DELETE = "Help message for command *delete*\n\tUsage: delete <task index>";
	public  String HELP_MSG_SHOW = "Help mesasge for command *show*\n\tUsage: show [<task index>]\t\t\t\tshow all available tasks or view a tasks with a specific index.";
	public  String HELP_MSG_HELP = "Help message for command *help*\n\tUsage: help <command> [<parameters>]\n\tNote: Currently support add, update, delete, show, search, help, undo.\n\tParameters:\n\t\t-h/--help \t\t\t\t\t display this help message\n.";
	private Map<CommandType, String> _map = new HashMap<CommandType, String>();

	public HelpHandler(List<Task> taskList) {
		super(taskList);
		_map.put(CommandType.CREATE, HELP_MSG_CREATE);
		_map.put(CommandType.UPDATE, HELP_MSG_UPDATE);
		_map.put(CommandType.SEARCH, HELP_MSG_SEARCH);
		_map.put(CommandType.DELETE, HELP_MSG_DELETE);
		_map.put(CommandType.RETRIEVE, HELP_MSG_SHOW);
		_map.put(CommandType.HELP, HELP_MSG_HELP);
		_map.put(CommandType.UNDO, HELP_MSG_UNDO);
	}

	public Result execute(CommandType type) {
		Result result;
		try {
			if(type == null){
				throw new Exception();
			}
			//System.out.println("My result string is: " + _map.get(type));
			result = new ResultImpl(CommandType.HELP, _map.get(type), new Time(
					System.currentTimeMillis()));
		} catch (Exception e) {
			if (Logic.DEBUG) {
				e.printStackTrace();
			}
			result = new ResultImpl(CommandType.INVALID,
					"Invalid command type " + type + " for help.", new Time(
							System.currentTimeMillis()));
		}

		return result;

	}

	@Override
	public Result execute(String primaryOperand,
			Iterator<AdditionalArgument> iterator)
			throws IllegalArgumentException {
		//System.out.println(primaryOperand);
		//System.out.println(CommandType.fromString(primaryOperand));
		return execute(CommandType.fromString(primaryOperand));
	}

}
