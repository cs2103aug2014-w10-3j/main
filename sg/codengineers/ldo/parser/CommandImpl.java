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
	private String							_userInput;
	private CommandType						_commandType;
	private String							_primaryOperand;
	private List<AdditionalArgument>		_additionalArguments;

	/* Constructors */
	public CommandImpl(String userInput) {
		initialise();
		assignMemberVariables(userInput);
	}

	/* Public Methods */

	/**
	 * Gets the initial string which contains the user input
	 * 
	 * @return a String object representing the initial user input string
	 */
	public String getUserInput() {
		return _userInput;
	}

	/**
	 * gets the Command type of this command.
	 * 
	 * @return a CommandType object representing the command type of this
	 *         Command object.
	 */
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
	public Iterator<AdditionalArgument> getAdditionalArguments() {
		return _additionalArguments.iterator();
	}

	@Override
	/**
	 * Checks for equality with another object
	 * 
	 * It will return true if and only if 
	 * The object is a command object and
	 * The object has the same user input string
	 */
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (o == this) {
			return true;
		}
		if (!(o instanceof Command)) {
			return false;
		}
		Command other = (Command) o;
		return other.getUserInput().equalsIgnoreCase(_userInput);

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
	 * Assigns values to all the member variables based on user input.
	 * 
	 * @param userInput
	 *            Input string received from user
	 */
	private void assignMemberVariables(String userInput) {
		_userInput = userInput;
		String commandWord = getFirstWord(userInput);
		_commandType = getCommandType(commandWord);

		if (_commandType != CommandType.INVALID) {
			_primaryOperand = getPrimaryOperand(removeFirstWord(userInput));
			String[] additionalArguments = splitToArguments(userInput);
			populateAdditionalArguments(additionalArguments);
		}
	}

	/**
	 * Populates the command map with all the possible user input keywords and
	 * their corresponding command types
	 */
	private void populateCmdMap() {
		_cmdMap.put("add", CommandType.CREATE);
		_cmdMap.put("update", CommandType.UPDATE);
		_cmdMap.put("delete", CommandType.DELETE);
		_cmdMap.put("show", CommandType.RETRIEVE);
		_cmdMap.put("retrieve", CommandType.RETRIEVE);
		_cmdMap.put("view", CommandType.RETRIEVE);
		_cmdMap.put("sync", CommandType.SYNC);
		_cmdMap.put("exit", CommandType.EXIT);
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
	 * Gets the primary operand of this command from the array of parameters
	 * from the user
	 * 
	 * @param parameters
	 *            Parameters input by user
	 * @return A String containing the primary operand
	 */
	private String getPrimaryOperand(String userInput) {
		String primaryOperand = userInput.split("--|-", 2)[PRIMARY_OPERAND_POSITION];
		return primaryOperand.trim();
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
		userInput = userInput.replaceFirst(_primaryOperand, "").trim();

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
		return message.replaceFirst(getFirstWord(message), "").trim();
	}
}
