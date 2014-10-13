package sg.codengineers.ldo.parser;

import java.util.Iterator;

import sg.codengineers.ldo.model.AdditionalArgument;
import sg.codengineers.ldo.model.Command;
import sg.codengineers.ldo.model.Parser;
import sg.codengineers.ldo.model.AdditionalArgument.ArgumentType;
import sg.codengineers.ldo.model.Command.CommandType;

public class ParserImpl implements Parser {

	/* Messages to show to user for Exceptions */
	private static String	CODE_FAULT			= "%1s in %2s component is not behaving according to how it should be";
	private static String	BLANK_INPUT			= "Blank input not acceptable";
	private static String	INVALID_COMMAND		= "Invalid command type entered";
	private static String	HELP_EXPECTED		= "Help argument expected";
	private static String	HELP_PLACEMENT		= "Help argument misplaced";
	private static String	ARG_AFT_HELP		= "There should not be arguments after help";
	private static String	NUMBER_EXPECTED		= "Primary operand should contain numbers!";
	private static String	INVALID_ARGUMENT	= "Invalid additional argument entered";
	private static String	INVALID_OPERAND		= "Operand should not be present after help";
	private static String	OPERAND_EXPECTED	= "Operand should follow additional argument %1s";

	/* Member Variables */
	private String			_userInput;
	private Command			_resultingCommand;
	private boolean			_isEmptyPriOp;

	/* Public methods */
	@Override
	public Command parse(String userInput) throws Exception {
		_userInput = userInput;
		validateInput();
		_resultingCommand = new CommandImpl(userInput);
		validateCommand();
		return _resultingCommand;
	}

	/* Private methods */

	/**
	 * Checks if the input given by user is blank
	 * 
	 * @throws Exception
	 *             throws and IllegalArgumentException if the user entered
	 *             nothing but whitespace characters.
	 */
	private void validateInput() throws Exception {
		if (_userInput.trim().isEmpty()) {
			throw new IllegalArgumentException(BLANK_INPUT);
		}
	}

	/**
	 * Validates the Command object that is created based on user's input
	 * 
	 * @throws Exception
	 *             Throws an exception if it fails any of the validation tests
	 */
	private void validateCommand() throws Exception {
		validateCommandType();
		validatePrimaryOperand();
		validateAdditionalArguments();
	}

	/**
	 * Checks that a valid command type is entered by the user
	 * 
	 * @throws Exception
	 *             If the command type is null, throws an exception to indicate
	 *             errors in the code.
	 *             If the command type is invalid, throws an
	 *             IllegalArgumentException.
	 */
	private void validateCommandType() throws Exception {
		if (_resultingCommand.getCommandType() == null) {
			throw new Exception(String.format(CODE_FAULT, "getCommandType",
					"CommandImpl"));
		}
		if (_resultingCommand.getCommandType() == CommandType.INVALID) {
			throw new IllegalArgumentException(INVALID_COMMAND);
		}
	}

	/**
	 * Checks that a valid primary operand is entered by the user. For all
	 * commands, an empty primary command is acceptable if it is followed by a
	 * --help or -h argument. Otherwise, only unary commands such as show, undo
	 * will be acceptable for empty primary commands.CREATE and SHOW will also
	 * be the only CommandTypes that accepts non digits for its primary operand.
	 * 
	 * @throws Exception
	 *             If the primary operand is null, throws an exception to
	 *             indicate errors in the code.
	 *             If the primary operand is invalid, throws an 
	 *             IllegalArgumentException
	 */
	private void validatePrimaryOperand() throws Exception {
		String priOp = _resultingCommand.getPrimaryOperand();

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
			if (!hasHelpArgument()) {
				throw new IllegalArgumentException(HELP_EXPECTED);
			}
			/*
			 * TODO in case we are not allowing <commandType> --help --<add arg>
			 * <operand>
			  
			else {
				if (hasMoreArguments()) {
					throw newIllegalArgumentException(ARG_AFT_HELP); 
				}
			}
			
			 */
		}
		/*
		 * TODO in case we are not allowing <commandType> <primary operand>
		 * --help
		  
		 if (hasHelpArgument()) { 
		 	throw new IllegalArgumentException(HELP_PLACEMENT); 
		 }
		 
		 */
		if (_resultingCommand.getCommandType() != CommandType.CREATE
				&& _resultingCommand.getCommandType() != CommandType.RETRIEVE) {
			for (char c : priOp.toCharArray()) {
				if (!Character.isDigit(c)) {
					throw new IllegalArgumentException(NUMBER_EXPECTED);
				}
			}
		}
	}

	/**
	 * Checks if the user entered the help argument as the first argument
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
	private boolean hasHelpArgument() throws Exception {
		Iterator<AdditionalArgument> addArgs = _resultingCommand
				.getAdditionalArguments();
		if (addArgs == null) {
			throw new Exception(String.format(CODE_FAULT,
					"getAdditionalArguments", "CommandImpl"));
		}
		AdditionalArgument firstArg = addArgs.next();
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
	 * @throws Exception
	 *             If the AdditionalArgument Iterator is null, throws an
	 *             exception to indicate error in code.
	 * 
	 */
	private void validateAdditionalArguments() throws Exception {
		Iterator<AdditionalArgument> addArgs = _resultingCommand
				.getAdditionalArguments();
		if (addArgs == null) {
			throw new Exception(String.format(CODE_FAULT,
					"getAdditionalArguments", "CommandImpl"));
		}
		while (addArgs.hasNext()) {
			validateArgument(addArgs.next());
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
					"getAdditionalArguments", "commandImpl"));
		}
		if (argType == ArgumentType.INVALID) {
			throw new IllegalArgumentException(INVALID_ARGUMENT);
		}
		if(operand == null) {
			throw new Exception(String.format(CODE_FAULT,
					"assignMemberVariables","commandImpl"));
		}
		if (argType == ArgumentType.HELP) {
			if (!operand.isEmpty()) {
				throw new IllegalArgumentException(INVALID_OPERAND);
			}
		}
		else {
			if (operand.isEmpty()) {
				throw new Exception(String.format(OPERAND_EXPECTED, argType));
			}
		}
	}

}
