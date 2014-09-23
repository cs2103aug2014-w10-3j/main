package sg.codengineers.ldo.parser;

public interface AdditionalArgument {

	/**
	 * Gets the operand of the additional argument field.
	 * 
	 * @return A String containing the operand
	 */
	public String getOperand();

	/**
	 * Gets the argument type of the additional argument field.
	 * 
	 * @return an ArgumentType of the field.
	 */
	public AdditionalArgumentImpl.ArgumentType getArgumentType();
}
