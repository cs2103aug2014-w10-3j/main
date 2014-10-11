package sg.codengineers.ldo.parser;

import sg.codengineers.ldo.model.Command;
import sg.codengineers.ldo.model.Parser;
import sg.codengineers.ldo.model.Command.CommandType;

public class ParserImpl implements Parser {

	private static String	BLANK_INPUT					= "Blank input not accepted!";
	private static String	NO_INPUT					= "No input detected!";
	private static String	ERRONEOUS_PRIMARY_OPERAND	= "%1s should not have a primary operand";
	private static String	INVALID_COMMAND				= "Invalid command type entered";
	
	private String _userInput;
	private Command _toParse;

	
	@Override
	public Command Parse(String userInput) throws Exception {
		_userInput=userInput;
		validateInput(_userInput);
		_toParse = new CommandImpl(_userInput);
		return _toParse;
	}

	private void validateInput(String userInput) throws Exception {
		if (userInput.equals("")) {
			throw new IllegalArgumentException(NO_INPUT);
		}
		if (userInput.equals(" ")) {
			throw new IllegalArgumentException(BLANK_INPUT);
		}
		if (_toParse.getCommandType()==CommandType.SHOW){
			String parameters = removeFirstWord(userInput);
			if(parameters.charAt(0)!='-'){
				throw new IllegalArgumentException(String.format(ERRONEOUS_PRIMARY_OPERAND, "show"));
			}
		}
		if(_toParse.getCommandType()==CommandType.INVALID){
			throw new IllegalArgumentException(INVALID_COMMAND);
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
		return message.replace(getFirstWord(message), "").trim();
	}
}
