package sg.codengineers.ldo.model;

/**
 * This interface specifies the public methods of the Additional Argument.
 * 
 * @author Victor Hazali
 * 
 */
public interface AdditionalArgument {

	// List of acceptable argument types
	enum ArgumentType {
		HELP, NAME, DEADLINE, TIME, TAG, PRIORITY, DESCRIPTION, INVALID
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
