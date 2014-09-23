package sg.codengineers.ldo.parser;

import java.util.TreeMap;

public class AdditionalArgumentImpl {

	enum ArgumentType {
		HELP, NAME, DEADLINE, TIME, TAG, DONE, PRIORITY, DESCRIPTION
	};

	private ArgumentType							_argumentType;
	private String									_value;
	private static TreeMap<String, ArgumentType>	_argsMap;

	public AdditionalArgumentImpl(String argumentType, String value) {
		_argumentType = getArgumentType(argumentType);
		_value = value;
	}

	private ArgumentType getArgumentType(String argument) {

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
}
