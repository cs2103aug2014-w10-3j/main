package sg.codengineers.ldo.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

import sg.codengineers.ldo.model.AdditionalArgument;
import sg.codengineers.ldo.model.Command;

public class CommandImpl implements Command {

	/* Constants */
	private static final int	PRIMARY_OPERAND_POSITION	= 0;

	/* Static Variables */
	private static TreeMap<String, CommandType>	_cmdMap;
	private static boolean						_isInitialised;

	/* Member Variables */
	private CommandType							_commandType;
	private String								_primaryOperand;
	private ArrayList<AdditionalArgument>		_additionalArguments;

	/* Constructors */
	public CommandImpl(String userInput) {
		initialise();
		userInput = userInput.toLowerCase();
		String commandWord = getFirstWord(userInput);
		_commandType = getCommandType(commandWord);
		String[] parameters = getParameters(removeFirstWord(userInput));
		_primaryOperand = getPrimaryOperand(parameters);
		populateAdditionalArguments(parameters);
	}

	/* Public Methods */

	public CommandType getCommandType() {
		return _commandType;
	}

	/**
	 * Gets the primary operand of this command.
	 * 
	 * @return a String containing the primary operand
	 */
	public String getPrimaryOperand() {
		return _primaryOperand;
	}

	/**
	 * Gets an iterator which is able to iterate through all of this command's
	 * additional arguments
	 * 
	 * @return an iterator containing all the additional arguments
	 */
	public Iterator<AdditionalArgument> getIterator() {
		return _additionalArguments.iterator();
	}

	/* Private Methods */

	/**
	 * Initialises the command map if it has yet to be done.
	 */
	private void initialise() {
		_cmdMap = new TreeMap<String, CommandType>();
		_additionalArguments = new ArrayList<AdditionalArgument>();

//		if (!_isInitialised) {
			populateCmdMap();
//			_isInitialised = true;
//		}
	}

	/**
	 * Populates the command map with all the possible user input keywords and
	 * their corresponding command types
	 */
	private void populateCmdMap() {
		_cmdMap.put("add", CommandType.CREATE);
		_cmdMap.put("retrieve", CommandType.RETRIEVE);
		_cmdMap.put("update", CommandType.UPDATE);
		_cmdMap.put("delete", CommandType.DELETE);
		_cmdMap.put("show", CommandType.SHOW);
	}

	/**
	 * Gets the primary operand of this command from the array of parameters
	 * from the user
	 * 
	 * @param parameters
	 *            Parameters input by user
	 * @return A String containing the primary operand
	 */
	private String getPrimaryOperand(String[] parameters) {
		if (_commandType == CommandType.SHOW) {
			return null;
		} else {
			return parameters[PRIMARY_OPERAND_POSITION];
		}
	}

	/**
	 * Populates the list of additional arguments for this command
	 * 
	 * @param parameters
	 *            Parameters input by user from which the additional arguments
	 *            and operands are extracted
	 */
	private void populateAdditionalArguments(String[] parameters) {
		int length = parameters.length;
		if (_commandType != CommandType.SHOW) {
			for (int i = 0; i < length-1; i++) {
				parameters[i] = parameters[1 + i];
			}
			length--;
		}
		for (int i = 0; i < length / 2; i += 2) {
			_additionalArguments.add(new AdditionalArgumentImpl(parameters[i],
					parameters[i + 1]));
		}
	}

	/**
	 * Gets the CommandType of the command based on user input
	 * 
	 * @param commandWord
	 *            command word input by user
	 * @return The equivalent CommandType, or INVALID if user has entered an
	 *         invalid command
	 */
	private CommandType getCommandType(String commandWord) {
		return _cmdMap.get(commandWord);
	}

	/**
	 * Gets the parameters input by user. This parameters encompasses all values
	 * namely the primary operand and additional arguments, except for the
	 * command type.
	 * 
	 * @param userInput
	 *            Input from user
	 * @return An Array of String each representing the parameters
	 */
	private String[] getParameters(String userInput) {
		return userInput.split("\\s+");
	}

	/**
	 * Gets the first word from a String of message
	 * 
	 * @param message
	 *            A string of message
	 * @return The first word of the message
	 */
	private String getFirstWord(String message) {
		return message.trim().split("\\s+")[0];
	}

	/**
	 * Removes the first word from a String of message
	 * 
	 * @param message
	 *            A string of message
	 * @return The rest of the message after removing the first word
	 */
	private String removeFirstWord(String message) {
		return message.replace(getFirstWord(message), "").trim();
	}
}
