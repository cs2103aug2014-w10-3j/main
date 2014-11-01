package sg.codengineers.ldo.logic;

/**
 * An interface that wraps a filter method to simulate "pass in functions as parameters".<br>
 * You are strongly suggested to use anonymous classes to implement this interface.
 * @author Wenhao
 *
 * @param <E> the type of input that {@link Filter#call(Object)} method checks.
 */
public interface Filter<E> {
	public boolean call(E item);
}
