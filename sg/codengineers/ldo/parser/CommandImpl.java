package sg.codengineers.ldo.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import sg.codengineers.ldo.model.AdditionalArgument;
import sg.codengineers.ldo.model.Command;

/**
 * This class specifies the implementation of Command as specified by the
 * Command interface
 * 
 * @author Victor Hazali
 * 
 */
public class CommandImpl implements Command {

	/* Constants */
	private static final int				PRIMARY_OPERAND_POSITION	= 0;

	/* Static Variables */
	private static Map<String, CommandType>	_cmdMap;
	private static boolean					_isInitialised;

	/* Member Variables */
	private CommandType						_commandType;
	private String							_primaryOperand;
	private List<AdditionalArgument>		_additionalArguments;

	/* Constructors */
	public CommandImpl(String userInput) {
		initialise();
		String commandWord = getFirstWord(userInput);
		_commandType = getCommandType(commandWord);
		if (_commandType != CommandType.SHOW
				&& _commandType != CommandType.INVALID) {
			_primaryOperand = getPrimaryOperand(removeFirstWord(userInput));
			String[] additionalArguments = splitToArguments(userInput);
			populateAdditionalArguments(additionalArguments);
		}
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
		if (!_isInitialised) {
			_cmdMap = new TreeMap<String, CommandType>();
			populateCmdMap();
			_isInitialised = true;
		}
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
	private String getPrimaryOperand(String userInput) {
		return userInput.split("--|-", 2)[PRIMARY_OPERAND_POSITION];
	}

	/**
	 * Populates the list of additional arguments for this command
	 * 
	 * @param additionalArguments
	 *            Parameters input by user from which the additional arguments
	 *            and operands are extracted
	 */
	private void populateAdditionalArguments(String[] additionalArguments) {
		_additionalArguments = new ArrayList<AdditionalArgument>();
		String[] argument = new String[2];
		int length = additionalArguments.length;
		for (int i = 0; i < length; i++) {
			argument = additionalArguments[i].split(" ", 2);
			_additionalArguments.add(new AdditionalArgumentImpl(argument[0],
					argument[1]));
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
		CommandType commandType = _cmdMap.get(commandWord.toLowerCase());
		if (commandType == null) {
			return CommandType.INVALID;
		}
		return commandType;
	}

	/**
	 * Gets the parameters input by user. This parameters encompasses all values
	 * namely the primary operand and additional arguments, except for the
	 * command type. The method will split the user input by detecting dashes,
	 * which is used to indicate a keyword for an additional argument from user
	 * 
	 * @param userInput
	 *            Input from user
	 * @return An Array of String each representing the parameters. The string
	 *         is trimmed to ensure that there will be no leading or trailing
	 *         white spaces.
	 */
	private String[] splitToArguments(String userInput) {
		// Removing primary command and argument
		userInput = removeFirstWord(userInput);
		userInput = userInput.replace(_primaryOperand, "").trim();

		// Splitting string into additional argument along with operands
		String[] additionalArguments = userInput.split("--+|-+");
		ArrayList<String> toReturn = new ArrayList<String>();
		int length = additionalArguments.length;
		for (int i = 0; i < additionalArguments.length; i++) {
			if (!additionalArguments[i].equals("")) {
				toReturn.add(additionalArguments[i].trim());
			}
			else {
				length--;
			}
		}

		return toReturn.toArray(new String[length]);
	}

	/**
	 * Gets the first word from a String of message
	 * 
	 * @param message
	 *            A string of message
	 * @return The first word of the message
	 */
	private String getFirstWord(String message) {
		return message.trim().split("\\s+")[0].toLowerCase();
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
