package sg.codengineers.ldo.parser;

import sg.codengineers.ldo.model.Command;
import sg.codengineers.ldo.model.Parser;
import sg.codengineers.ldo.model.Command.CommandType;

public class ParserImpl implements Parser {

	private static String	BLANK_INPUT		= "Blank input not accepted!";
	private static String	INVALID_COMMAND	= "Invalid command type entered";

	private String			_userInput;
	private Command			_toParse;

	@Override
	public Command parse(String userInput) throws Exception {
		_userInput = userInput;
		validateCommandType(_userInput);
		_toParse = new CommandImpl(_userInput);
		validateArguments(_userInput);
		return _toParse;
	}

	private void validateCommandType(String userInput) throws Exception {
		if (userInput.trim().equals("")) {
			throw new IllegalArgumentException(BLANK_INPUT);
		}
	}

	private void validateArguments(String userInput) throws Exception {
		if (_toParse.getCommandType() == CommandType.INVALID) {
			throw new IllegalArgumentException(INVALID_COMMAND);
		}
	}

}
