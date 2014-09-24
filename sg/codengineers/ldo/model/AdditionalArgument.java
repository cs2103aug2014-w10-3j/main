package sg.codengineers.ldo.model;

public interface AdditionalArgument {
	enum ArgumentType {
		HELP, NAME, DEADLINE, TIME, TAG, DONE, PRIORITY, DESCRIPTION, INVALID
	};
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
	public ArgumentType getArgumentType();
}
