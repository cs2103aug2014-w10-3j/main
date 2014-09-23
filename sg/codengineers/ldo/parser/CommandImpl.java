package sg.codengineers.ldo.parser;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

public class CommandImpl implements Command {

	/* Constants */
	private static final int	PRIMARY_OPERAND_POSITION	= 0;

	public enum CommandType {
		CREATE, RETRIEVE, UPDATE, DELETE, SHOW, INVALID
	};

	public enum ArgumentType {
		HELP, NAME, DEADLINE, TIME, TAG, DONE, PRIORITY, DESCRIPTION
	};

	/* Static Variables */
	private static TreeMap<String, ArgumentType>	_argsMap;
	private static TreeMap<String, CommandType>		_cmdMap;

	/* Member Variables */
	private CommandType								_commandType;
	private String									_primaryOperand;

	/* Constructors */
	public CommandImpl(String userInput) {
		String commandWord = getFirstWord(userInput);
		_commandType = getCommandType(commandWord);
		String[] parameters = getParameters(userInput);
		_primaryOperand = parameters[PRIMARY_OPERAND_POSITION];

	}

	/* Public Methods */
	public CommandImpl.CommandType getCommandType() {
		return _commandType;
	}

	public String getPrimaryOperand() {
		return _primaryOperand;
	}

	public Iterator<Entry<String, ArgumentType>> getArgsMapIterator() {
		return _argsMap.entrySet().iterator();
	}

	public void initialise() {
		populateMaps();
	}

	/* Private Methods */
	private static void populateMaps() {
		populateArgsMap();
		populateCmdMap();
	}

	private static void populateArgsMap() {
		_argsMap = new TreeMap<String, ArgumentType>();

		// Possible keywords for Help
		_argsMap.put("--help", ArgumentType.HELP);
		_argsMap.put("-h", ArgumentType.HELP);

		// Possible keywords for Name
		_argsMap.put("--name", ArgumentType.NAME);
		_argsMap.put("-n", ArgumentType.NAME);

		// Possible keywords for Deadline
		_argsMap.put("-d", ArgumentType.DEADLINE);
		_argsMap.put("--deadline", ArgumentType.DEADLINE);

		// Possible keywords for Time
		_argsMap.put("-t", ArgumentType.TIME);
		_argsMap.put("--time", ArgumentType.TIME);
	}

	private static void populateCmdMap() {

	}

	private CommandType getCommandType(String commandWord) {
		return _cmdMap.get(commandWord);
	}

	private String getFirstWord(String userInput) {
		return userInput.trim().split("\\s+")[0];
	}

	private String[] removeFirstWord(String userInput) {
		// TODO Auto-generated method stub
		String firstWord = getFirstWord(userInput);

		return null;
	}
}
