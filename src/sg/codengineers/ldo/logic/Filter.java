package sg.codengineers.ldo.logic;
//@author A0119541J
/**
 * An interface that wraps a filter method to simulate "pass in functions as parameters".<br>
 * You are strongly suggested to use anonymous classes to implement this interface.
 * @param <E> the type of input that {@link Filter#call(Object)} method checks.
 */
public interface Filter<E> {
	public boolean call(E item);
}
