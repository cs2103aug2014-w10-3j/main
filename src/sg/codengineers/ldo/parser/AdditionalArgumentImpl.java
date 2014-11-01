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

	/* Member variables */
	private ArgumentType						_argumentType;
	private String								_operand;

	/* Constructors */
	public AdditionalArgumentImpl(String argumentType, String value) {
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
}