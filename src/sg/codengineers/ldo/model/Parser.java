package sg.codengineers.ldo.model;

/**
 * This interface specifies the public methods available to the Parser Class
 * 
 * @author Victor Hazali
 * 
 */
public interface Parser {

	/**
	 * Parses the user input from a String into a Command Class
	 * 
	 * @param userInput
	 *            The String object containing user input.
	 * @return A Command Object based on the user's Input. An Invalid command
	 *         will be returned should there be any issues with the user's
	 *         input. The rationale behind the Invalid command will be placed
	 *         within the primary operand field of the command.
	 */
	public Command parse(String userInput);

	/**
	 * Parses the user input from a String into an AdditionalArgument Class
	 * 
	 * @param userInput
	 *            The String object containing user input
	 * @return An AdditionalArgument Object based on the user's INput. An
	 *         Invalid AdditionalArgument will be returned should there be any
	 *         issues with the user's input. The rationale behind the Invalid
	 *         AdditionalArgument will be placed within the operand field of the
	 *         AdditionalArgument.
	 */
	public AdditionalArgument parseToAddArg(String userInput);
}
