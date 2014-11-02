package sg.codengineers.ldo.parser;

// import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import sg.codengineers.ldo.model.AdditionalArgument;
import sg.codengineers.ldo.model.Command;

// import sg.codengineers.ldo.model.Parser;

/**
 * This class specifies the implementation of Command as specified by the
 * Command interface
 * 
 * @author Victor Hazali
 * 
 */
public class CommandImpl implements Command {

	/* Member Variables */
	private String						_userInput;
	private CommandType					_commandType;
	private String						_primaryOperand;
	private List<AdditionalArgument>	_additionalArguments;

	/* Constructors */

	public CommandImpl(String userInput, CommandType commandType,
			String primaryOperand, List<AdditionalArgument> additionalArguments) {
		setUserInput(userInput);
		setCommandType(commandType);
		setPrimaryOperand(primaryOperand);
		setAdditionalArguments(additionalArguments);
	}

	/* Public Methods */

	/**
	 * Gets the initial string which contains the user input
	 * 
	 * @return a String object representing the initial user input string
	 */
	@Override
	public String getUserInput() {
		return _userInput;
	}

	/**
	 * gets the Command type of this command.
	 * 
	 * @return a CommandType object representing the command type of this
	 *         Command object.
	 */
	@Override
	public CommandType getCommandType() {
		return _commandType;
	}

	/**
	 * Gets the primary operand of this command.
	 * 
	 * @return a String containing the primary operand
	 */
	@Override
	public String getPrimaryOperand() {
		return _primaryOperand;
	}

	/**
	 * Gets an iterator which is able to iterate through all of this command's
	 * additional arguments
	 * 
	 * @return an iterator containing all the additional arguments
	 */
	@Override
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

	/* Protected Methods */
	protected void setUserInput(String userInput) {
		_userInput = userInput;
	}

	protected void setCommandType(CommandType cmdType) {
		_commandType = cmdType;
	}

	protected void setPrimaryOperand(String priOp) {
		_primaryOperand = priOp;
	}

	protected void setAdditionalArguments(List<AdditionalArgument> addArgs) {
		_additionalArguments = addArgs;
	}
}
