package sg.codengineers.ldo.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import sg.codengineers.ldo.model.AdditionalArgument;
import sg.codengineers.ldo.model.AdditionalArgument.ArgumentType;
import sg.codengineers.ldo.model.Command;
import sg.codengineers.ldo.model.Command.CommandType;
import sg.codengineers.ldo.model.Date;
import sg.codengineers.ldo.model.Parser;

/**
 * This class implements a Parser as implemented by the Parser interface.
 * 
 * @author Victor Hazali
 * 
 */
public class ParserImpl implements Parser {

	/* Constants */
	private static final int					PRIMARY_OPERAND_POSITION	= 0;
	private static final int					EMPTY_VALUE					= -1;
	private static final boolean				DEBUG_MODE					= false;

	/* Messages to show to user for Exceptions */
	private static String						CODE_FAULT					= "%1s in %2s component is not behaving according to how it should be";
	private static String						BLANK_INPUT					= "Blank input not acceptable";
	private static String						INVALID_COMMAND				= "%1s has an Invalid command type entered";
	private static String						HELP_EXPECTED				= "Help argument expected";
	private static String						NUMBER_EXPECTED				= "Primary operand should contain numbers!";
	private static String						INVALID_ARGUMENT			= "Invalid additional argument entered";
	private static String						OPERAND_EXPECTED			= "Operand should follow additional argument %1s";

	/* Static Variables */
	private static Map<String, CommandType>		_cmdMap;
	private static Map<String, ArgumentType>	_argsMap;
	private static boolean						_isInitialised;

	/* Member Variables */
	private String								_userInput;
	private Command								_resultingCommand;
	private boolean								_isEmptyPriOp;
	private boolean								_isHelpRequest;

	/* Public methods */

	/**
	 * Parses an input string into a Command Object.
	 * 
	 * In the event of an unsuccessful parsing, a Command Object with the
	 * command type INVALID is returned instead. The rationale behind the
	 * unsuccessful parsing is stored within the command as it's primary
	 * operand.
	 */
	@Override
	public Command parse(String userInput) {
		initialise(userInput);
		try {
			checkForBlankInput();
		} catch (Exception e) {
			if (DEBUG_MODE) {
				e.printStackTrace();
			}
			return new CommandImpl(userInput, CommandType.INVALID,
					e.getMessage(), new ArrayList<AdditionalArgument>());
		}
		CommandType cmdType = getCommandType();
		String priOp = getPrimaryOperand();
		String[] splitInput = splitToArguments(removePrimaryArgument(priOp));
		List<AdditionalArgument> addArgs = populateAdditionalArguments(splitInput);
		try {
			validateInput(cmdType, priOp, addArgs);
		} catch (Exception e) {
			if (DEBUG_MODE) {
				e.printStackTrace();
			}
			return new CommandImpl(userInput, CommandType.INVALID,
					e.getMessage(), new ArrayList<AdditionalArgument>());
		}
		if (_isHelpRequest) {
			cmdType = CommandType.HELP;
			priOp = getFirstWord(userInput).toLowerCase();
			addArgs = new ArrayList<AdditionalArgument>();
		}
		_resultingCommand = new CommandImpl(_userInput, cmdType, priOp, addArgs);
		return _resultingCommand;
	}

	@Override
	public AdditionalArgument parseToAddArg(String userInput) {
		initialise(userInput);

		try {
			checkForBlankInput();
		} catch (Exception e) {
			if (DEBUG_MODE) {
				e.printStackTrace();
			}
			return new AdditionalArgumentImpl(ArgumentType.INVALID,
					e.getMessage());
		}
		ArgumentType argType = getArgumentType(getFirstWord(userInput));
		String operand = getOperand();
		try {
			validateArgument(new AdditionalArgumentImpl(argType, operand));
		} catch (Exception e) {
			if (DEBUG_MODE) {
				e.printStackTrace();
			}
			return new AdditionalArgumentImpl(ArgumentType.INVALID,
					e.getMessage());
		}
		return new AdditionalArgumentImpl(argType, operand);
	}

	@Override
	public Date parseToDate(String userInput) {
		_userInput = userInput;
		int date;
		Date _resultingDate = new DateImpl(EMPTY_VALUE, EMPTY_VALUE,
				EMPTY_VALUE, EMPTY_VALUE, EMPTY_VALUE);
		try {
			checkForBlankInput();
		} catch (Exception e) {
			if (DEBUG_MODE) {
				e.printStackTrace();
			}
			return new DateImpl(EMPTY_VALUE, EMPTY_VALUE, EMPTY_VALUE,
					EMPTY_VALUE, EMPTY_VALUE);
		}

		String[] tokens = userInput.split("([\\s+:\\.-)");
		for (String s : tokens) {
			if (!s.isEmpty()) {

			}
		}

		return _resultingDate;
	}

	/* Private Methods */

	/**
	 * Initialises the command map if it has yet to be done.
	 */
	private void initialise(String userInput) {
		_userInput = userInput;
		_isEmptyPriOp = false;
		_isHelpRequest = false;
		if (!_isInitialised) {
			_cmdMap = new TreeMap<String, CommandType>();
			populateCmdMap();
			populateArgsMap();
			_isInitialised = true;
		}
	}

	/**
	 * Checks if the input given by user is blank
	 * 
	 * @throws Exception
	 *             throws an IllegalArgumentException if the user entered
	 *             nothing but whitespace characters.
	 */
	private void checkForBlankInput() throws Exception {
		if (_userInput.trim().isEmpty()) {
			throw new IllegalArgumentException(BLANK_INPUT);
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
		_cmdMap.put("search", CommandType.SEARCH);
		_cmdMap.put("help", CommandType.HELP);
		_cmdMap.put("undo", CommandType.UNDO);
		_cmdMap.put("exit", CommandType.EXIT);
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

	/**
	 * Extracts the word representing the command type based on user input
	 * 
	 * @return a String object representing the command type entered by user.
	 */
	private CommandType getCommandType() {
		String commandWord = _userInput.trim().split("\\s+")[0].toLowerCase();
		CommandType commandType = _cmdMap.get(commandWord.toLowerCase());
		if (commandType == null) {
			return CommandType.INVALID;
		}
		return commandType;
	}

	/* Private methods */

	/**
	 * Gets the primary operand of the command from the user input
	 * 
	 * @return a String object representing the primary operand entered by user.
	 */
	private String getPrimaryOperand() {
		return removeFirstWord(_userInput).split("--|-", 2)[PRIMARY_OPERAND_POSITION];
	}

	private String removePrimaryArgument(String priOp) {
		return removeFirstWord(_userInput).replaceFirst(priOp, "").trim();
	}

	/* Private methods */

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
	private String[] splitToArguments(String addArgs) {
		String[] additionalArguments = addArgs.split("--+|-+");
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
	 * TODO
	 * 
	 * @param cmdType
	 * @param priOp
	 * @param addArgs
	 * @throws Exception
	 */
	private void validateInput(CommandType cmdType, String priOp,
			List<AdditionalArgument> addArgs)
			throws Exception {
		validateCommandType(cmdType);
		validatePrimaryOperand(cmdType, priOp, addArgs);
		validateAdditionalArguments(addArgs);
	}

	/**
	 * Checks that a valid command type is entered by the user
	 * 
	 * @param cmdType
	 *            String object containing command type entered by user
	 * 
	 * @throws Exception
	 *             If the command type is null, throws an exception to indicate
	 *             errors in the code.
	 *             If the command type is invalid, throws an
	 *             IllegalArgumentException.
	 */
	private void validateCommandType(CommandType cmdType) throws Exception {
		if (cmdType == null) {
			throw new Exception(String.format(CODE_FAULT, "getCommandType",
					"ParserImpl"));
		}
		if (cmdType == CommandType.INVALID) {
			throw new IllegalArgumentException(String.format(INVALID_COMMAND,
					_userInput));
		}
	}

	/**
	 * Checks that a valid primary operand is entered by the user. For all
	 * commands, an empty primary command is acceptable if it is followed by a
	 * --help or -h argument. Otherwise, only unary commands such as RETRIEVE,
	 * UNDO, SYNC or EXIT will be acceptable for empty primary commands. CREATE
	 * and RETRIEVE will also be the only CommandTypes that accepts non digits
	 * for its primary operand.
	 * 
	 * @param cmdType
	 *            commandType of current command.
	 * 
	 * @param priOp
	 *            primary operand from user to be checked.
	 * @param addArgs
	 *            additional argument(s) of current command.
	 * @throws Exception
	 *             If the primary operand is null, throws an exception to
	 *             indicate errors in the code.
	 *             If the primary operand is invalid, throws an
	 *             IllegalArgumentException
	 */
	private void validatePrimaryOperand(CommandType cmdType, String priOp,
			List<AdditionalArgument> addArgs)
			throws Exception {

		if (priOp == null) {
			throw new Exception(String.format(CODE_FAULT, "getPrimaryOperand",
					"CommandImpl"));
		}

		if (priOp.trim().isEmpty()) {
			_isEmptyPriOp = true;
		}
		else {
			_isEmptyPriOp = false;
		}

		if (_isEmptyPriOp) {
			if (!hasHelpArgument(addArgs) && !isUnaryCommand(cmdType)) {
				throw new IllegalArgumentException(HELP_EXPECTED);
			}
		}
		if (cmdType == CommandType.UPDATE) {
			for (char c : priOp.toCharArray()) {
				if (!Character.isDigit(c)) {
					throw new IllegalArgumentException(NUMBER_EXPECTED);
				}
			}
		}
		if (cmdType == CommandType.DELETE && !priOp.equalsIgnoreCase("all")) {
			for (char c : priOp.toCharArray()) {
				if (!Character.isDigit(c)) {
					throw new IllegalArgumentException(NUMBER_EXPECTED);
				}
			}
		}
	}

	/**
	 * Populates the list of additional arguments for this command
	 * 
	 * @param additionalArguments
	 *            Parameters input by user from which the additional arguments
	 *            and operands are extracted
	 */
	private List<AdditionalArgument> populateAdditionalArguments(
			String[] additionalArguments) {
		List<AdditionalArgument> toReturn = new ArrayList<AdditionalArgument>();
		String[] argument = new String[2];
		int length = additionalArguments.length;
		for (int i = 0; i < length; i++) {
			argument = additionalArguments[i].split(" ", 2);
			if (argument.length == 1) {
				toReturn.add(new AdditionalArgumentImpl(
						getArgumentType(argument[0]), ""));
			} else {
				toReturn.add(new AdditionalArgumentImpl(
						getArgumentType(argument[0]), argument[1]));
			}
		}

		return toReturn;
	}

	/**
	 * Checks if the user entered the help argument as the first argument
	 * 
	 * @param addArgs
	 *            Additional Arguments of this command.
	 * 
	 * @return True if the first additional argument is of ArgumentType HELP
	 *         false otherwise
	 * @throws Exception
	 *             If the AdditionalArgument Iterator is null, throws an
	 *             exception to indicate errors in code.
	 *             If the firstArg is a null with a .next() call to the
	 *             Iterator, throws an exception to indicate errors
	 *             in the code.
	 */
	private boolean hasHelpArgument(List<AdditionalArgument> addArgs)
			throws Exception {
		if (addArgs == null) {
			throw new Exception(String.format(CODE_FAULT,
					"populateAdditionalArguments", "ParserImpl"));
		}
		if (addArgs.size() == 0) {
			return false;
		}
		AdditionalArgument firstArg = addArgs.get(0);
		if (firstArg == null) {
			throw new Exception(String.format(CODE_FAULT, "hasHelpArgument",
					"ParserImpl"));
		}
		if (firstArg.getArgumentType() != ArgumentType.HELP) {
			return false;
		}
		return true;
	}

	/**
	 * Validates all the additional Arguments entered by the user.
	 * 
	 * @param addArgs
	 *            additionalArguments to check
	 * 
	 * @throws Exception
	 *             If the AdditionalArgument Iterator is null, throws an
	 *             exception to indicate error in code.
	 * 
	 */
	private void validateAdditionalArguments(List<AdditionalArgument> addArgs)
			throws Exception {
		if (addArgs == null) {
			throw new Exception(String.format(CODE_FAULT,
					"populateAdditionalArguments", "ParserImpl"));
		}
		for (AdditionalArgument addArg : addArgs) {
			validateArgument(addArg);
		}
	}

	/**
	 * Validates each additional argument entered by user
	 * 
	 * @param arg
	 *            The argument to be validated
	 * @throws Exception
	 *             If the argument is null, throws an exception to indicate
	 *             error in code.
	 *             If the ArgumentType is null, throws an exception to indicate
	 *             error in code.
	 *             If the ArgumentType is INVALID, throws an
	 *             IllegalArgumentException.
	 *             If the operand is null, throws an exception to indicate
	 *             error in code.
	 *             If the operand is empty, throws an IllegalArgumentException
	 *             if it's not help.
	 *             If the ArgumentType is HELP, and there is an operand, throws
	 *             an IllegalArgumentException
	 */
	private void validateArgument(AdditionalArgument arg) throws Exception {
		if (arg == null) {
			throw new Exception(String.format(CODE_FAULT,
					"getAdditionalArguments", "commandImpl"));
		}
		ArgumentType argType = arg.getArgumentType();
		String operand = arg.getOperand();
		if (argType == null) {
			throw new Exception(String.format(CODE_FAULT,
					"getAdditionalArguments", "ParserImpl"));
		}
		if (argType == ArgumentType.INVALID) {
			throw new IllegalArgumentException(INVALID_ARGUMENT);
		}
		if (operand == null) {
			throw new Exception(String.format(CODE_FAULT,
					"assignMemberVariables", "commandImpl"));
		}
		if (argType == ArgumentType.HELP) {
			_isHelpRequest = true;
		}
		else {
			if (operand.isEmpty()) {
				throw new Exception(String.format(OPERAND_EXPECTED, argType));
			}
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

	private String getOperand() {
		return removeFirstWord(_userInput);
	}

	/**
	 * Validates if the command is a unary Command.
	 * Unary commands are commands that do not require a primary operand.
	 * Commands that are unary are: retrieve/show/view, sync and exit
	 * 
	 * @return true if the command type is unary, false otherwise
	 */
	private boolean isUnaryCommand(CommandType cmdType) {
		return (cmdType == CommandType.RETRIEVE || cmdType == CommandType.SYNC
				|| cmdType == CommandType.UNDO || cmdType == CommandType.EXIT);
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
