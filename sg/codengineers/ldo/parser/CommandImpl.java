package sg.codengineers.ldo.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

public class CommandImpl implements Command {

	/* Constants */
	private static final int	PRIMARY_OPERAND_POSITION	= 0;

	public enum CommandType {
		CREATE, RETRIEVE, UPDATE, DELETE, SHOW, INVALID
	};

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
		userInput=userInput.toLowerCase();
		String commandWord = getFirstWord(userInput);
		_commandType = getCommandType(commandWord);
		String[] parameters = getParameters(removeFirstWord(userInput));
		_primaryOperand = getPrimaryOperand(parameters);
		populateAdditionalArguments();
	}

	/* Public Methods */
	public CommandImpl.CommandType getCommandType() {
		return _commandType;
	}

	public String getPrimaryOperand() {
		return _primaryOperand;
	}

	/* Private Methods */
	private void initialise() {
		if(!_isInitialised){
			populateCmdMap();
			_isInitialised=true;
		}
	}
	
	private void populateCmdMap() {
		
		_cmdMap.put("add", CommandType.CREATE);
		_cmdMap.put("retrieve", CommandType.RETRIEVE);
		_cmdMap.put("update",CommandType.UPDATE);
		_cmdMap.put("delete", CommandType.DELETE);
		_cmdMap.put("show", CommandType.SHOW);
	}
	
	private String getPrimaryOperand(String[] parmeters){
		//TODO auto-generated stub
		
		return null;
	}
	
	private void populateAdditionalArguments() {
		// TODO Auto-generated method stub
		
	}


	private CommandType getCommandType(String commandWord) {
		return _cmdMap.get(commandWord);
	}
	
	private String[] getParameters(String inputs){
		return inputs.split("\\s+");
	}

	private String getFirstWord(String userInput) {
		return userInput.trim().split("\\s+")[0];
	}

	private String removeFirstWord(String userInput) {
		return userInput.replace(getFirstWord(userInput),"").trim();
	}
}
