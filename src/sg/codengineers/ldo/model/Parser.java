//@author A0110741X

package sg.codengineers.ldo.model;

import java.util.Date;

/**
 * This interface specifies the public methods available to the Parser Class
 */
public interface Parser {

	/**
	 * Parses the user input from a String into a Command Object
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
	 * Parses the user input from a String into an AdditionalArgument Object
	 * 
	 * @param userInput
	 *            The String object containing user input.
	 * @return An AdditionalArgument Object based on the user's Input. An
	 *         Invalid AdditionalArgument will be returned should there be any
	 *         issues with the user's input. The rationale behind the Invalid
	 *         AdditionalArgument will be placed within the operand field of the
	 *         AdditionalArgument.
	 */
	public AdditionalArgument parseToAddArg(String userInput);

	/**
	 * Parses the user input from a String into a Date Object
	 * 
	 * @param userInput
	 *            The String object containing user input.
	 * @return A Date Object based on the user's Input.
	 */
	public Date parseToDate(String userInput);

	/**
	 * parses a Date object into a string format in the following format:
	 * "dd/mm/yyyy HH:mm"
	 * 
	 * @param date
	 *            Date object to be parsed
	 * @return a String object containing the hour, minute, date, month and year
	 *         of the date object.
	 */
	public String parseDateToString(Date date);
}
