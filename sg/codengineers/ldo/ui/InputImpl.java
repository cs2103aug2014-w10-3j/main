package sg.codengineers.ldo.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import sg.codengineers.ldo.model.Input;

public class InputImpl implements Input {

	/* Member Variables */
	private Scanner	_scanner;
	private String	_userInput;

	/* Constructors */
	public InputImpl(File inputFile) throws FileNotFoundException {
		_scanner = new Scanner(inputFile);
	}

	public InputImpl() {
		_scanner = new Scanner(System.in);
	}

	/* Public Methods */
	@Override
	public String readFromUser() {
		_userInput = _scanner.nextLine();
		return _userInput;
	}

}
