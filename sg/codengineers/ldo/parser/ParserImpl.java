package sg.codengineers.ldo.parser;

import java.util.Iterator;

import sg.codengineers.ldo.model.AdditionalArgument;
import sg.codengineers.ldo.model.Command;
import sg.codengineers.ldo.model.Parser;
import sg.codengineers.ldo.model.AdditionalArgument.ArgumentType;
import sg.codengineers.ldo.model.Command.CommandType;

public class ParserImpl implements Parser {

	private static String	CODE_FAULT		= "%1s in %2s component is not behaving according to how it should be";
	private static String	BLANK_INPUT		= "Blank input not acceptable";
	private static String	INVALID_COMMAND	= "Invalid command type entered";
	private static String	HELP_EXPECTED	= "Help argument expected";
	private static String	HELP_PLACEMENT	= "Help argument misplaced";
	private static String	ARG_AFT_HELP	= "There should not be arguments after help";
	private static String	NUMBER_EXPECTED	= "Primary operand should contain numbers!";
	
	private String			_userInput;
	private Command			_resultingCommand;
	private boolean			_isEmptyPriOp;

	@Override
	public Command parse(String userInput) throws Exception {
		_userInput = userInput;
		validateInput();
		_resultingCommand = new CommandImpl(userInput);
		validateCommand();
		return _resultingCommand;
	}

	private void validateInput() throws Exception {
		if (_userInput.trim().equals("")) {
			throw new IllegalArgumentException(BLANK_INPUT);
		}
	}

	private void validateCommand() throws Exception {
		validateCommandType();
		validatePrimaryOperand();
		validateAdditionalArguments();
	}

	private void validateCommandType() throws Exception {
		if (_resultingCommand.getCommandType() == null) {
			throw new Exception(String.format(CODE_FAULT, "getCommandType",
					"CommandImpl"));
		}
		if (_resultingCommand.getCommandType() == CommandType.INVALID) {
			throw new IllegalArgumentException(INVALID_COMMAND);
		}
	}

	private void validatePrimaryOperand() throws Exception {
		String priOp = _resultingCommand.getPrimaryOperand();
		if (priOp == null) {
			throw new Exception(String.format(CODE_FAULT, "getPrimaryOperand",
					"CommandImpl"));
		}
		
		if (priOp.trim().equals("")) {
			_isEmptyPriOp = true;
		}
		else {
			_isEmptyPriOp = false;
		}
		
		if (_isEmptyPriOp) {
			if(!hasHelpArgument()){
				throw new IllegalArgumentException(HELP_EXPECTED);
			}
			/*
			* TODO in case we are not allowing
			* <commandType> --help --<add arg> <operand>
			
			else{
				if(hasMoreArguments()){
					throw new IllegalArgumentException(ARG_AFT_HELP);
				}
			}
			
			*/
		}
		
		if(hasHelpArgument()){
			throw new IllegalArgumentException(HELP_PLACEMENT);
		}
		
		if(_resultingCommand.getCommandType()!=CommandType.CREATE){
			for(char c: priOp.toCharArray()){
				if(!Character.isDigit(c)){
					throw new IllegalArgumentException(NUMBER_EXPECTED);
				}
			}
		}
	}

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

	private void validateAdditionalArguments() {

	}

	private void validateArgument() throws Exception {
		if (_resultingCommand.getCommandType() == CommandType.INVALID) {
			throw new IllegalArgumentException(INVALID_COMMAND);
		}
	}

}
