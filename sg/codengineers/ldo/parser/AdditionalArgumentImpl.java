package sg.codengineers.ldo.parser;

import java.util.Map;
import java.util.TreeMap;

import sg.codengineers.ldo.model.AdditionalArgument;

/**
 * This class implements Additional Argument as specified by the Additional
 * Argument interface
 * 
 * @author Victor Hazali
 * 
 */
public class AdditionalArgumentImpl implements AdditionalArgument {

	/* Static Variables */
	private static Map<String, ArgumentType>	_argsMap;
	private static boolean						_isInitialised;

	/* Member variables */
	private ArgumentType						_argumentType;
	private String								_operand;

	/* Constructors */
	public AdditionalArgumentImpl(String argumentType, String value) {
		initialise();
		_argumentType = getArgumentType(argumentType);
		_operand = value;
	}

	/* public methods */

	/**
	 * Gets the Operand of the argument
	 * 
	 * @return A String containing the value of the argument
	 */
	public String getOperand() {
		return _operand;
	}

	/**
	 * Gets the argument type of the argument
	 * 
	 * @return An ArgumentType of the argument
	 */
	public ArgumentType getArgumentType() {
		return _argumentType;
	}

	/* Private Methods */

	/**
	 * initialises by populating the argument map
	 */
	private void initialise() {
		if (!_isInitialised) {
			populateArgsMap();
			_isInitialised = true;
		}
	}

	/**
	 * Takes the input of the user and returns the argument type
	 * 
	 * @param argument
	 *            Input string from user
	 * @return The argument type of the argument.
	 */
	private ArgumentType getArgumentType(String argument) {
		ArgumentType argumentType = _argsMap.get(argument);
		if (argumentType == null) {
			return ArgumentType.INVALID;
		}
		return argumentType;
	}

	/**
	 * populate the argsMap with all the possible input keywords for the
	 * respective argument types
	 */
	private void populateArgsMap() {
		_argsMap = new TreeMap<String, ArgumentType>();

		// Possible keywords for Help
		_argsMap.put("help", ArgumentType.HELP);
		_argsMap.put("h", ArgumentType.HELP);

		// Possible keywords for Name
		_argsMap.put("name", ArgumentType.NAME);
		_argsMap.put("n", ArgumentType.NAME);

		// Possible keywords for Deadline
		_argsMap.put("d", ArgumentType.DEADLINE);
		_argsMap.put("deadline", ArgumentType.DEADLINE);

		// Possible keywords for Time
		_argsMap.put("t", ArgumentType.TIME);
		_argsMap.put("time", ArgumentType.TIME);

		// Possible keywords for Tag
		_argsMap.put("tag", ArgumentType.TAG);
		_argsMap.put("done", ArgumentType.TAG);
		_argsMap.put("mark", ArgumentType.TAG);

		// Possible keywords for Priority
		_argsMap.put("priority", ArgumentType.PRIORITY);
		_argsMap.put("p", ArgumentType.PRIORITY);

		// Possible keywords for Description
		_argsMap.put("description", ArgumentType.DESCRIPTION);
		_argsMap.put("desc", ArgumentType.DESCRIPTION);
		_argsMap.put("information", ArgumentType.DESCRIPTION);
		_argsMap.put("info", ArgumentType.DESCRIPTION);
		_argsMap.put("note", ArgumentType.DESCRIPTION);
		_argsMap.put("a", ArgumentType.DESCRIPTION);
	}

}