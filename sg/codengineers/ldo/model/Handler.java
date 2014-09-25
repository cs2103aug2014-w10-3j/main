package sg.codengineers.ldo.model;

public interface Handler {
	public Result execute(Command command);
}
