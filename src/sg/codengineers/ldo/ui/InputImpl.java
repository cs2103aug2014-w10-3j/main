//@author A0110741X

package sg.codengineers.ldo.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import sg.codengineers.ldo.model.Input;

/**
 * This Class implements the Input class as specified by the Input interface
 * 
 */
public class InputImpl implements Input {

	/* Member Variables */
	private Scanner	_scanner;
	private String	_userInput;

	/* Constructors */
	/**
	 * Constructor to read the input from a file
	 * 
	 * @param inputFile
	 *            File to be read from
	 * @throws FileNotFoundException
	 */
	public InputImpl(File inputFile) throws FileNotFoundException {
		_scanner = new Scanner(inputFile);
	}

	/**
	 * Constructor to read the input from system I/O such as keyboard
	 */
	public InputImpl() {
		_scanner = new Scanner(System.in);
	}

	/* Public Methods */
	@Override
	/**
	 * Reads the input from user
	 * @return a String containing the user input
	 */
	public String readFromUser() {
		_userInput = _scanner.nextLine();
		return _userInput;
	}

}
