package sg.codengineers.ldo.parser;

import sg.codengineers.ldo.model.AdditionalArgument;

/**
 * This class implements Additional Argument as specified by the Additional
 * Argument interface
 * 
 * @author Victor Hazali
 * 
 */
public class AdditionalArgumentImpl implements AdditionalArgument {

	/* Member variables */
	private ArgumentType	_argumentType;
	private String			_operand;

	/* Constructors */
	public AdditionalArgumentImpl(ArgumentType argumentType, String value) {
		_argumentType = argumentType;
		_operand = value;
	}

	/* public methods */

	/**
	 * Gets the Operand of the argument
	 * 
	 * @return A String containing the value of the argument
	 */
	@Override
	public String getOperand() {
		return _operand;
	}

	/**
	 * Gets the argument type of the argument
	 * 
	 * @return An ArgumentType of the argument
	 */
	@Override
	public ArgumentType getArgumentType() {
		return _argumentType;
	}

	/**
	 * Displays the contents of the command class in the following format:
	 * 
	 * argument type:\t<argumentType>
	 * operand:\t<operand>
	 * 
	 * @return a string object containing the contents of the additional
	 *         argument object in the format specified
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("argument type:\t" + getArgumentType().toString());
		sb.append("operand:\t" + getOperand());
		return sb.toString();
	}
}