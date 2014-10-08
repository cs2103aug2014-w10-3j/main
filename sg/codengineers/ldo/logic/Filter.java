package sg.codengineers.ldo.logic;

public interface Filter<E> {
	public boolean call(E item);
}
