package sg.codengineers.ldo.model;

import java.util.Date;

/**
 * This interface exposes public functionality for a task
 *
 * @author Sean
 */
public interface Task {
	
	public enum Priority {
		  LOW("low"),
		  MEDIUM("medium"),
		  HIGH("high");

		  private String text;

		  Priority(String text) {
		    this.text = text;
		  }

		  public String getText() {
		    return this.text;
		  }

		  public static Priority fromString(String text) {
		    if (text != null) {
		      for (Priority b : Priority.values()) {
		        if (text.equalsIgnoreCase(b.text)) {
		          return b;
		        }
		      }
		    }
		    return null;
		  }
		}
	
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
	 * Gets the description of the task
	 * @return The task description
	 */
	public String getDescription();
	
	/**
	 * Gets the time that the task should start
	 * @return Start time of the task
	 */
	public Date getStartTime();

	/**
	 * Gets the time that the task should end
	 * @return The end time of the task
	 */
	public Date getEndTime();

	/**
	 * Gets the deadline of the task for the user
	 * @return The task deadline
	 */
	public Date getDeadline();
	
	public Priority getPriority();
	
	public void setId(int id);
	
	public void setName(String name);
	
	public void setTag(String tag);
	
	public void setDescription(String description);
	
	public void setTimeStart(Date timeEnd);
	
	public void setTimeEnd(Date timeEnd);
	
	public void setDeadline(Date deadline);
	
	public void setPriority(Priority priority);
}
