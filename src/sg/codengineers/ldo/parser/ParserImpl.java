package sg.codengineers.ldo.parser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import sg.codengineers.ldo.model.AdditionalArgument;
import sg.codengineers.ldo.model.AdditionalArgument.ArgumentType;
import sg.codengineers.ldo.model.Command;
import sg.codengineers.ldo.model.Command.CommandType;
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
	private static final boolean				DEBUG_MODE					= false;

	/* Messages to show to user for Exceptions */
	private static String						CODE_FAULT					= "%1s in %2s component is not behaving according to how it should be.\n";
	private static String						BLANK_INPUT					= "Blank input not acceptable.\n";
	private static String						INVALID_COMMAND				= "%1s has an Invalid command type entered.\n";
	private static String						EMPTY_OPR_FOR_CMD			= "Empty operand for %s command not supported.\n";
	private static String						NUMBER_EXPECTED_FOR_CMD		= "Primary operand for %s should contain numbers.\n";
	private static String						INVALID_ARGUMENT			= "Invalid additional argument entered.\n";
	private static String						OPERAND_EXPECTED			= "Operand should follow additional argument %s.\n";
	private static String						INVALID_INDEX				= "Primary operand should not be less than 1.\n";
	private static String						INVALID_OPERAND				= "%s is not a valid operand for %s.\n";
	private static String						INVALID_ARG_FOR_CMD			= "%s argument is invalid for %s command type.\n";
	private static final String					DEADLINE_AND_TIME			= "Not possible to set both deadline and time range.\n";

	/* Static Variables */
	private static Map<String, CommandType>		_cmdMap;
	private static Map<String, ArgumentType>	_argsMap;
	private static List<DateFormat>				_dateTimeFormats;
	private static List<DateFormat>				_dateFormats;
	private static List<DateFormat>				_timeFormats;
	private static boolean						_isInitialised;

	/* Member Variables */
	private String								_userInput;
	private Command								_resultingCommand;
	private boolean								_isEmptyPriOp;
	private boolean								_isHelpRequest;
	private boolean								_hasDeadline;
	private boolean								_hasTimeRange;

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
					e.getMessage());
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
					e.getMessage());
		}

		if (_isHelpRequest) {
			priOp = cmdType.toString().toLowerCase();
			cmdType = CommandType.HELP;
			addArgs = new ArrayList<AdditionalArgument>();
		}

		_resultingCommand = new CommandImpl(_userInput, cmdType, priOp, addArgs);
		return _resultingCommand;
	}

	/**
	 * Parses an input string to an AdditionalArgument Object.
	 * 
	 * In the event of an unsuccessful parse, an AdditionalArgument with an
	 * INVALID argument type will be returned. The rationale behind the
	 * unsuccessful parsing will be stored as the operand.
	 */
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

	/**
	 * Parses an input string to a Date object. The list of acceptable formats
	 * is specified by the lists _dateFormats, _dateTimeFormats and
	 * _timeFormats.
	 * 
	 * In the event of an unsuccessful parse, a null object is returned instead.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public Date parseToDate(String userInput) {
		initialise(userInput);
		Date resultingDate = null;

		try {
			checkForBlankInput();
		} catch (Exception e) {
			if (DEBUG_MODE) {
				e.printStackTrace();
			}
			return null;
		}

		for (DateFormat format : _dateTimeFormats) {
			try {
				resultingDate = format.parse(userInput);
				return resultingDate;
			} catch (Exception e) {
				// Do nothing, move to next format
			}
		}

		for (DateFormat format : _dateFormats) {
			try {
				resultingDate = format.parse(userInput);
				resultingDate.setHours(23);
				resultingDate.setMinutes(59);
				return resultingDate;
			} catch (Exception e) {
				// Do nothing, move to next format.
			}
		}

		for (DateFormat format : _timeFormats) {
			try {
				resultingDate = format.parse(userInput);
				return resultingDate;
			} catch (Exception e) {
				if (DEBUG_MODE) {
					e.printStackTrace();
				}
				// Do nothing, fail to parse
			}
		}

		return resultingDate;
	}

	/* Private Methods */

	/**
	 * Initialises the static variables if it has yet to be done.
	 */
	private void initialise(String userInput) {
		_userInput = userInput;
		_isEmptyPriOp = false;
		_isHelpRequest = false;
		_hasDeadline = false;
		_hasTimeRange = false;
		if (!_isInitialised) {
			populateCmdMap();
			populateArgsMap();
			populateDateTimeFormats();
			populateDateFormats();
			populateTimeFormats();
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
		_cmdMap = new TreeMap<String, CommandType>();

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
		_argsMap.put("deadline", ArgumentType.DEADLINE);
		_argsMap.put("dd", ArgumentType.DEADLINE);
		_argsMap.put("dl", ArgumentType.DEADLINE);

		// Possible keywords for Time
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
		_argsMap.put("ds", ArgumentType.DESCRIPTION);
		_argsMap.put("a", ArgumentType.DESCRIPTION);
	}

	/**
	 * Populates the list with all acceptable date time formats
	 */
	private void populateDateTimeFormats() {
		_dateTimeFormats = new ArrayList<DateFormat>();

		_dateTimeFormats.add(new SimpleDateFormat("dd MMM yy hha"));
		_dateTimeFormats.add(new SimpleDateFormat("dd MMM yyyyy hha"));

		_dateTimeFormats.add(new SimpleDateFormat("dd MMM yy hh:mma"));
		_dateTimeFormats.add(new SimpleDateFormat("dd MMM yyyy hh:mma"));
		_dateTimeFormats.add(new SimpleDateFormat("dd MMM yy HH:mm"));
		_dateTimeFormats.add(new SimpleDateFormat("dd MMM yyyy HH:mm"));

		// using slashes as delimiter
		_dateTimeFormats.add(new SimpleDateFormat("dd/MM/yy hha"));
		_dateTimeFormats.add(new SimpleDateFormat("dd/MM/yyyy hha"));
		_dateTimeFormats.add(new SimpleDateFormat("dd/MM/yy hh:mma"));
		_dateTimeFormats.add(new SimpleDateFormat("dd/MM/yyyy hh:mma"));
		_dateTimeFormats.add(new SimpleDateFormat("dd/MM/yy HH:mm"));
		_dateTimeFormats.add(new SimpleDateFormat("dd/MM/yyyy HH:mm"));
	}

	/**
	 * Populates the list with all acceptable date formats
	 */
	private void populateDateFormats() {
		_dateFormats = new ArrayList<DateFormat>();

		_dateFormats.add(new SimpleDateFormat("dd MMM yy"));
		_dateFormats.add(new SimpleDateFormat("dd MMM yyyy"));

		// using slashes as delimiter
		_dateFormats.add(new SimpleDateFormat("dd/MM/yy"));
		_dateFormats.add(new SimpleDateFormat("dd/MM/yyyy"));
	}

	/**
	 * populates the list with all acceptable time formats
	 */
	private void populateTimeFormats() {
		_timeFormats = new ArrayList<DateFormat>();

		_timeFormats.add(new SimpleDateFormat("hha"));
		_timeFormats.add(new SimpleDateFormat("hh:mma"));
		_timeFormats.add(new SimpleDateFormat("HH:mm"));
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

	/**
	 * Gets the primary operand of the command from the user input
	 * 
	 * @return a String object representing the primary operand entered by user.
	 */
	private String getPrimaryOperand() {
		return removeFirstWord(_userInput).split("--|-", 2)[PRIMARY_OPERAND_POSITION]
				.trim();
	}

	private String removePrimaryArgument(String priOp) {
		return removeFirstWord(_userInput).replaceFirst(priOp, "").trim();
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

				if (argument[0].equalsIgnoreCase("done")) {
					toReturn.add(new AdditionalArgumentImpl(
							getArgumentType(argument[0]), "done"));
				} else {
					toReturn.add(new AdditionalArgumentImpl(
							getArgumentType(argument[0]), ""));
				}
			} else {
				toReturn.add(new AdditionalArgumentImpl(
						getArgumentType(argument[0]), argument[1]));
			}
		}

		return toReturn;
	}

	/**
	 * Validates input from user to be parsed into a Command object. The list of
	 * acceptable commandTypes is specified by the commands in the cmdMap. The
	 * list of acceptable argumentTypes is specified by the arguments in the
	 * argsMap. Depending on each command type, the acceptable primary operands
	 * are specified. The acceptable additional arguments are also verified
	 * depending on the command types. The acceptable additional operands are
	 * dependent on the argument types.
	 * 
	 * @param cmdType
	 *            Command Type
	 * @param priOp
	 *            Primary Operand
	 * @param addArgs
	 *            List of additional arguments
	 * @throws Exception
	 *             if any of the parameters do not clear the validation rules.
	 */
	private void validateInput(CommandType cmdType, String priOp,
			List<AdditionalArgument> addArgs)
			throws Exception {
		validateCommandType(cmdType);
		validateAdditionalArguments(addArgs, cmdType);
		validatePrimaryOperand(cmdType, priOp, addArgs);
	}

	/**
	 * Checks that a valid command type is entered by the user. The list of
	 * acceptable command types is specified by the cmdMap
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
	 * SYNC, SEARCH, HELP, UNDO, EXIT will be acceptable for empty primary
	 * commands. CREATE, RETRIEVE, SEARCH and HELP will also be the only
	 * CommandTypes that accepts non digits for its primary operand.
	 * 
	 * @param cmdType
	 *            commandType of current command.
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

		// To capture empty primary operands
		if (_isEmptyPriOp) {
			if (!_isHelpRequest && !isUnaryCommand(cmdType)) {
				throw new IllegalArgumentException(String.format(
						EMPTY_OPR_FOR_CMD, cmdType.toString().toLowerCase()));
			}
		}

		/* Checking for acceptable primary operand for each command type */

		// CREATE accepts all

		// Update
		if (cmdType == CommandType.UPDATE && !_isHelpRequest) {
			if (!isDigit(priOp)) {
				throw new IllegalArgumentException(String.format(
						NUMBER_EXPECTED_FOR_CMD, cmdType.toString()
								.toLowerCase()));
			}

			int index = Integer.parseInt(priOp);
			if (index <= 0) {
				throw new IllegalArgumentException(INVALID_INDEX);
			}
		}

		// DELETE
		if (cmdType == CommandType.DELETE && !priOp.equalsIgnoreCase("all")
				&& !_isHelpRequest) {

			if (!isDigit(priOp)) {
				throw new IllegalArgumentException(String.format(
						NUMBER_EXPECTED_FOR_CMD, cmdType.toString()
								.toLowerCase()));
			}

			int index = Integer.parseInt(priOp);
			if (index <= 0) {
				throw new IllegalArgumentException(INVALID_INDEX);
			}
		}

		// RETRIEVE
		if (cmdType == CommandType.RETRIEVE && !priOp.equalsIgnoreCase("all")
				&& !_isHelpRequest && !_isEmptyPriOp) {

			if (!isDigit(priOp)) {
				throw new IllegalArgumentException(String.format(
						NUMBER_EXPECTED_FOR_CMD, cmdType.toString()
								.toLowerCase()));
			}

			int i = Integer.parseInt(priOp);
			if (i <= 0) {
				throw new IllegalArgumentException(INVALID_INDEX);
			}
		}

		// SEARCH accepts all

		// UNDO accepts none
		if (cmdType == CommandType.UNDO && !_isEmptyPriOp) {
			throw new IllegalArgumentException(String.format(INVALID_OPERAND,
					priOp, cmdType.toString().toLowerCase()));
		}
		// EXIT accepts none
		if (cmdType == CommandType.EXIT && !_isEmptyPriOp) {
			throw new IllegalArgumentException(String.format(INVALID_OPERAND,
					priOp, cmdType.toString().toLowerCase()));
		}
	}

	/**
	 * Helper method to check if all the characters in a string is numeric
	 * 
	 * @param message
	 *            String to be checked
	 * @return True if all the contents are numeric and false otherwise. Note
	 *         that method returns false for negative values as the character
	 *         '-' is considered not numeric
	 */
	private boolean isDigit(String message) {
		for (char c : message.toCharArray()) {
			if (!Character.isDigit(c)) {
				return false;
			}
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
	private void validateAdditionalArguments(List<AdditionalArgument> addArgs,
			CommandType cmdType)
			throws Exception {
		if (addArgs == null) {
			throw new Exception(String.format(CODE_FAULT,
					"populateAdditionalArguments", "ParserImpl"));
		}
		for (AdditionalArgument addArg : addArgs) {
			validateArgument(addArg, cmdType);
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
	private void validateArgument(AdditionalArgument arg, CommandType cmdType)
			throws Exception {
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
				throw new Exception(String.format(OPERAND_EXPECTED, argType
						.toString().toLowerCase()));
			}

			// Parsing priority to enums
			if (argType == ArgumentType.PRIORITY) {
				if (!operand.equalsIgnoreCase("low")
						&& !operand.equalsIgnoreCase("normal")
						&& !operand.equalsIgnoreCase("high")) {
					throw new Exception(String.format(INVALID_OPERAND, operand,
							argType.toString().toLowerCase()));
				}
			}

			// Parsing deadline
			if (argType == ArgumentType.DEADLINE) {
				if (_hasTimeRange) {
					throw new Exception(DEADLINE_AND_TIME);
				}
				_hasDeadline = true;
				Date date = parseToDate(operand);
				if (date == null) {
					throw new Exception(String.format(INVALID_OPERAND, operand,
							argType.toString().toLowerCase()));
				}
			}

			// Parsing time range
			if (argType == ArgumentType.TIME) {
				if (_hasDeadline) {
					throw new Exception(DEADLINE_AND_TIME);
				}
				_hasTimeRange = true;
				String temp = operand;
				// check for "from" and remove if present
				if (operand.contains("from")) {
					temp = operand.replace("from", " ").trim();
				}
				String[] time = temp.split("to");

				if (time.length == 1) {	// only end time
					Date endTime = parseToDate(time[0].trim());
					if (endTime == null) {
						throw new Exception(String.format(INVALID_OPERAND,
								operand, argType.toString().toLowerCase()));
					}
				} else if (time.length == 2) {	// start and end time
					Date startTime = parseToDate(time[0]);
					Date endTime = parseToDate(time[1]);
					if (startTime == null || endTime == null) {
						throw new Exception(String.format(INVALID_OPERAND,
								operand, argType.toString().toLowerCase()));
					}
				}
			}

			/* Checking for acceptable additional argument for each command type */
			// CREATE
			if (cmdType == CommandType.CREATE) {
				if (argType == ArgumentType.NAME) {
					throw new Exception(String.format(INVALID_ARG_FOR_CMD,
							argType.toString().toLowerCase(), cmdType
									.toString().toLowerCase()));
				}
			}

			// UPDATE accepts all

			// DELETE accepts only help
			if (cmdType == CommandType.DELETE) {
				throw new Exception(String.format(INVALID_ARG_FOR_CMD, argType
						.toString().toLowerCase(), cmdType.toString()
						.toLowerCase()));
			}

			// RETRIEVE accepts only help
			if (cmdType == CommandType.RETRIEVE) {
				throw new Exception(String.format(INVALID_ARG_FOR_CMD, argType
						.toString().toLowerCase(), cmdType.toString()
						.toLowerCase()));
			}

			// SEARCH accepts all

			// UNDO accepts only help
			if (cmdType == CommandType.UNDO) {
				throw new Exception(String.format(INVALID_ARG_FOR_CMD, argType
						.toString().toLowerCase(), cmdType.toString()
						.toLowerCase()));
			}

			// EXIT accepts only help
			if (cmdType == CommandType.EXIT) {
				throw new Exception(String.format(INVALID_ARG_FOR_CMD, argType
						.toString().toLowerCase(), cmdType.toString()
						.toLowerCase()));
			}
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
			// Only help addArg should have empty operand
			if (operand.isEmpty()) {
				throw new Exception(String.format(OPERAND_EXPECTED, argType
						.toString().toLowerCase()));
			}

			// Parsing priority to enums
			if (argType == ArgumentType.PRIORITY) {
				if (!operand.equalsIgnoreCase("low")
						&& !operand.equalsIgnoreCase("normal")
						&& !operand.equalsIgnoreCase("high")) {
					throw new Exception(String.format(INVALID_OPERAND, operand,
							argType.toString().toLowerCase()));
				}
			}

			// Parsing deadline or time
			if (argType == ArgumentType.DEADLINE
					|| argType == ArgumentType.TIME) {
				Date date = parseToDate(operand);
				if (date == null) {
					throw new Exception(String.format(INVALID_OPERAND, operand,
							argType.toString().toLowerCase()));
				}
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

	/**
	 * Helper method to get the operand from a string.
	 * 
	 * This is assuming that the operand will be the remaining of the string
	 * after removing the first word.
	 * Used to extract the operand from a string representing the additional
	 * argument.
	 * 
	 * @return a String object representing the operand in an additional
	 *         argument.
	 */
	private String getOperand() {
		return removeFirstWord(_userInput);
	}

	/**
	 * Validates if the command is a unary Command.
	 * Unary commands are commands that do not require a primary operand.
	 * Commands that are unary are: retrieve/show/view, sync, search, help, undo
	 * and exit
	 * 
	 * @return true if the command type is unary, false otherwise
	 */
	private boolean isUnaryCommand(CommandType cmdType) {
		return (cmdType == CommandType.RETRIEVE || cmdType == CommandType.SYNC
				|| cmdType == CommandType.SEARCH || cmdType == CommandType.HELP
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
