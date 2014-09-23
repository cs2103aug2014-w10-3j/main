package sg.codengineers.ldo.logic;

/**
 * This interface exposes public functionality for a task
 *
 * @author Sean
 */
public interface Task {

	/**
	 * Gets the unique ID of each task
	 * @return Unique ID of the task
	 */
	public int getId();

	/**
	 * Gets the name of the task
	 * @return Name of task
	 */
	public String getName();

	/**
	 * Gets current status tagged with the task
	 * @return Status tagged to task
	 */
	public String getTag();

	/**
	 * Gets the time that the task should start
	 * @return Start time of the task
	 */
	public String getStartTime();

	/**
	 * Gets the time that the task should end
	 * @return The end time of the task
	 */
	public String getEndTime();

	/**
	 * Gets the deadline of the task for the user
	 * @return The task deadline
	 */
	public String getDeadline();

	/**
	 * Gets the description of the task
	 * @return The task description
	 */
	public String getDesc();

	/**
	 * Gets the priority of the task
	 * @return The task priority
	 */
	public String getPriority();
}
