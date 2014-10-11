package sg.codengineers.ldo.ui;

import sg.codengineers.ldo.model.Input;
import sg.codengineers.ldo.model.Output;
import sg.codengineers.ldo.model.Result;
import sg.codengineers.ldo.model.UI;

public class UIImpl implements UI {

	private Input	_input;
	private Output	_output;

	@Override
	public String readFromUser() {
		_input = new InputImpl();
		return _input.readFromUser();
	}

	@Override
	public void showToUser(Result result) {
		_output = new OutputImpl();
		_output.displayResult(result);
	}

	@Override
	public void showToUser(String message) {
		System.out.println(message);
	}

	@Override
	public void showWelcomeMessage() {
		_output = new OutputImpl();
		_output.displayWelcome();
	}
}
