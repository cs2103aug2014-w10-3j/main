package sg.codengineers.ldo.parser;

public interface AdditionalArgument {

	/**
	 * Gets the value of the additional argument field.
	 * 
	 * @return A String containing the value
	 */
	public String getValue();

	/**
	 * Gets the argument type of the additional argument field.
	 * 
	 * @return an ArgumentType of the field.
	 */
	public AdditionalArgumentImpl.ArgumentType getArgumentType();
}
