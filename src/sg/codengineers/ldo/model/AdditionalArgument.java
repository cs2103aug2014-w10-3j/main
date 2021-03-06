//@author A0110741X

package sg.codengineers.ldo.model;

/**
 * This interface specifies the public methods of the Additional Argument.
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
	public String toString();
}
