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
	 * @return A Command Object based on the user's Input.
	 * @throws Exception
	 *             Throws an IllegalArgumentException when user input is not a
	 *             valid command.
	 */
	public Command Parse(String userInput) throws Exception;
}
