package sg.codengineers.ldo.logic;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import sg.codengineers.ldo.model.AdditionalArgument;
import sg.codengineers.ldo.model.Parser;
import sg.codengineers.ldo.model.Result;
import sg.codengineers.ldo.model.Task;
import sg.codengineers.ldo.model.Command.CommandType;
import sg.codengineers.ldo.model.Task.Priority;
import sg.codengineers.ldo.parser.ParserImpl;
import sg.codengineers.ldo.parser.ResultImpl;
import sg.codengineers.ldo.logic.Filter;

/**
 * Abstract class for logic operations. <br>
 * It provides some basic utility functions for task manipulations. Concrete
 * logic operation classes need to implement {@link #execute(String, Iterator)}
 * method and fill in the actual execution there.
 * 
 * @author Wenhao
 *
 */
public abstract class Handler {
	// Difference between display index which starts from 1 and system index
	// which starts from 0
	protected static int DIFFERENCE_DIPSLAY_INDEX_AND_SYSTEM_INDEX = 1;
	protected Parser _parser;

	public static boolean DEBUG_MODE = false;
	/**
	 * SimpleDateFormat no longer in use. Changed to Parser parseToDate method.
	 */
	// public final static SimpleDateFormat FORMATTER = new
	// SimpleDateFormat("dd/MM/yyyy");
	protected List<Task> _taskList;

	/**
	 * The actual operation needs to be implemented in concrete classes.
	 * 
	 * @param primaryOperand
	 * @param iterator
	 * @return a {@link sg.codengineers.ldo.model.Result Result} object
	 *         containing the retrieval result or feedbacks from other
	 *         operations. Will return <code>null</code> if the execution is
	 *         unsuccessful.
	 * @throws IllegalArgumentException
	 *             Thrown when the handler cannot parse the input parameters.
	 */
	abstract public Result execute(String primaryOperand,
			Iterator<AdditionalArgument> iterator)
			throws IllegalArgumentException;

	public Handler(List<Task> taskList) {
		this._taskList = taskList;
		this._parser = new ParserImpl();
	}

	/**
	 * Modify a task according to the input {@link AdditionalArgument}. <br>
	 * 
	 * <ul>
	 * <li>Argument type <code>HELP</code> is not implemented yet.</li>
	 * <li>Argument type <code>PRIORITY DONE TAG</code> are written into a same
	 * field <code>_tag</code> in {@link TaskImpl}.</li>
	 * <li>No exception will be thrown!</li>
	 * </ul>
	 * 
	 * @param task
	 *            the {@link Task} object to be modified.
	 * @param arg
	 *            the {@link AdditionalArgument} containing modification
	 *            information.
	 */
	protected void modifyTask(Task task, AdditionalArgument arg)
			throws ParseException, IllegalArgumentException {
		String operand = arg.getOperand();

		switch (arg.getArgumentType()) {
		case NAME:
			task.setName(operand);
			break;
		case DEADLINE:
			Date d = _parser.parseToDate(operand);
			if (d != null) {
				task.setDeadline(d);
			} else {
				throw new IllegalArgumentException("Wrong date format: "
						+ operand);
			}
			break;
		case TIME:
			String[] sOperand = operand.split("\\s+");
			if (sOperand.length != 2) {
				break;
			} else {
				Date startDate = _parser.parseToDate(sOperand[0]);
				Date endDate = _parser.parseToDate(sOperand[1]);
				if (startDate != null && endDate != null) {
					task.setTimeStart(startDate);
					task.setTimeEnd(endDate);
				} else {
					throw new IllegalArgumentException("Wrong date format: "
							+ operand);
				}
			}
			break;
		case PRIORITY:
			Task.Priority priority = Priority.fromString(operand);
			if (priority != null) {
				task.setPriority(priority);
			} else {
				throw new IllegalArgumentException();
			}
			break;
		case TAG:
			task.setTag(operand);
			break;
		case DESCRIPTION:
			task.setDescription(operand);
			break;
		case HELP:
		case INVALID:
		default:
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Search through a {@link List} of {@link Task} according to the input
	 * {@link AdditionalArgument}.
	 * 
	 * <ul>
	 * <li><code>name</code> field and <code>description</code> field are
	 * checked using {@link String#contains(String)} method.</li>
	 * <li><code>tag</code> field is checked using
	 * {@link String#equalsIgnoreCase(String)} method.</li>
	 * <li><code>deadline</code> field is checked using
	 * {@link Date#compareTo(Date)} method. A task will be taken as a valid
	 * return value if and only if:
	 * <ol>
	 * <li><code>taskDeadline >= today</code> and,</li>
	 * <li><code>taskDeadline <= inputDeadline</code></li>
	 * </ol>
	 * <li><code>time</code> field is checked using {@link Date#compareTo(Date)}
	 * method. A task will be taken as a valid return value if and only if its
	 * <code>time</code> field overlaps with the input time.</li>
	 * </ul>
	 * 
	 * @param list
	 *            a {@link List} of {@link Task} objects from which the
	 *            searching result will be constructed.
	 * @param arg
	 *            a {@link AdditionalArgument} object containing the search
	 *            parameter.
	 * @return a {@link List} of {@link Task} objects containing the search
	 *         result. Note that this return list contains the original
	 *         references to the task objects which are directly obtained from
	 *         the input list.
	 */
	protected List<Task> searchTask(List<Task> list, AdditionalArgument arg) {
		List<Task> newList = new ArrayList<Task>(list);
		final String operand = arg.getOperand();

		try {
			switch (arg.getArgumentType()) {
			case NAME:
				newList = filter(newList, new Filter<Task>() {
					@Override
					public boolean call(Task task) {
						boolean x = task.getName().contains(operand);
						// System.out.println(x);
						return x;
					}
				});
				break;
			case DEADLINE:
				final Date deadline = _parser.parseToDate(operand);
				if (deadline == null) {
					throw new IllegalArgumentException("Wrong date format: "
							+ operand);
				}
				newList = filter(newList, new Filter<Task>() {
					@Override
					public boolean call(Task task) {
						Date dd = task.getDeadline();
						return dd.compareTo(deadline) <= 0
								&& dd.compareTo(new Date()) >= 0;
					}
				});
				break;
			case TIME:
				String[] sOperand = operand.split("\\s+");
				if ((sOperand.length == 2 && !sOperand[0].isEmpty() && sOperand[1]
						.isEmpty())
						|| (sOperand.length == 1 && !sOperand[0].isEmpty())) {
					final Date date = _parser.parseToDate(sOperand[0]);
					if (date == null) {
						throw new IllegalArgumentException(
								"Wrong date format: " + operand);
					}
					newList = filter(newList, new Filter<Task>() {
						@Override
						public boolean call(Task task) {
							Date ts = task.getStartTime();
							Date te = task.getEndTime();
							Date ddl = task.getDeadline();
							if (ddl != null) {
								if (ddl.compareTo(date) <= 0
										&& ddl.compareTo(new Date()) >= 0) {
									return true;
								}
							}
							if (ts != null && te != null) {
								if (te.compareTo(date) >= 0
										&& ts.compareTo(date) <= 0) {
									return true;
								}
							}
							return false;
						}
					});
				} else if (sOperand.length == 2 && !sOperand[0].isEmpty()
						&& !sOperand[1].isEmpty()) {

					final Date startDate = _parser.parseToDate(sOperand[0]);
					final Date endDate = _parser.parseToDate(sOperand[1]);
					if (startDate == null || endDate == null) {
						throw new IllegalArgumentException(
								"Wrong date format: " + operand);
					}
					newList = filter(newList, new Filter<Task>() {
						@Override
						public boolean call(Task task) {
							Date ts = task.getStartTime();
							Date te = task.getEndTime();
							if (ts == null || te == null) {
								return false;
							} else {
								return (ts.compareTo(endDate) <= 0 && ts
										.compareTo(startDate) >= 0)
										|| (te.compareTo(startDate) >= 0 && te
												.compareTo(endDate) <= 0);
							}
						}
					});
				} else {
					throw new IllegalArgumentException(
							"Please specify both start date and end date");
				}
				break;
			case PRIORITY:
				final Task.Priority priority = Priority.fromString(operand);
				if (priority != null) {
					newList = filter(newList, new Filter<Task>() {
						@Override
						public boolean call(Task task) {
							if (task.getPriority() == priority) {
								return true;
							}
							return false;
						}
					});
				}
				break;
			case TAG:
				newList = filter(newList, new Filter<Task>() {
					@Override
					public boolean call(Task task) {
						return task.getTag().equalsIgnoreCase(operand);
					}
				});
				break;
			case DESCRIPTION:
				newList = filter(newList, new Filter<Task>() {
					@Override
					public boolean call(Task task) {
						return task.getDescription().contains(operand);
					}
				});
				break;
			case HELP:
			case INVALID:
			default:
				break;
			}
		} catch (Exception e) {
			if (Logic.DEBUG)
				e.printStackTrace();
			throw e;
		}
		return newList;
	}

	/**
	 * Apply a {@link Filter} to all input {@link List} of {@link Task} objects
	 * and construct a return {@link List} of tasks that are evaluated to
	 * <code>true</code> by the {@link Filter}.
	 * 
	 * @param list
	 *            a {@link List} of {@link Task} objects.
	 * @param f
	 *            a {@link Filter} that implements a {@link Filter#call(Object)}
	 *            method.
	 * @return a {@link List} of {@link Task} objects. Note that the task
	 *         references inside the list refer to the same tasks in the input
	 *         list.
	 */
	private List<Task> filter(List<Task> list, Filter<Task> f) {
		List<Task> newList = new ArrayList<Task>();

		for (Task task : list) {
			if (f.call(task)) {
				newList.add(task);
			}
		}
		return newList;
	}

	/**
	 * Construct a {@link Result} object.
	 * 
	 * @param operand
	 *            the primary operand of a {@link Command}
	 * @param task
	 *            the targeted {@link Task} of the command.
	 * @return a {@link Result} object wrapping the input and current time
	 *         information.
	 * @see Handler#constructResult(String, List)
	 */
	protected Result constructResult(String operand, Task task) {
		return new ResultImpl(CommandType.RETRIEVE, operand, new Time(
				System.currentTimeMillis()), task);
	}

	/**
	 * Construct a {@link Result} object.
	 * 
	 * @param operand
	 *            the primary operand of a {@link Command}
	 * @param list
	 *            the targeted {@link List} of {@link Task} of the command.
	 * @return a {@link Result} object wrapping the input and current time
	 *         information.
	 * @see Handler#constructResult(String, Task)
	 */
	protected Result constructResult(String operand, List<Task> list) {
		return new ResultImpl(CommandType.RETRIEVE, operand, new Time(
				System.currentTimeMillis()), list);
	}

	protected Result constructResult(String operand) {
		return new ResultImpl(CommandType.RETRIEVE, operand, new Time(
				System.currentTimeMillis()));
	}

	public static List<Task> eliminateDoneTasks(List<Task> taskList) {
		List<Task> newList = new ArrayList<Task>();
		if (taskList != null) {
			for (int i = 0; i < taskList.size(); i++) {
				if (!taskList.get(i).getTag().equalsIgnoreCase("Done")) {
					newList.add(taskList.get(i));
				}
			}
		}
		return newList;
	}

	protected boolean isTagDone(AdditionalArgument arg) {
		if (arg == null) {
			return false;
		} else {
			if (arg.getArgumentType() == AdditionalArgument.ArgumentType.TAG
					&& arg.getOperand() != null
					&& arg.getOperand().equalsIgnoreCase("Done")) {
				return true;
			}
		}
		return false;
	}
}
