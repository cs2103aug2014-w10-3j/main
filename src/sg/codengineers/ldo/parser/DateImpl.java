package sg.codengineers.ldo.parser;

import sg.codengineers.ldo.model.Date;

/**
 * This class specifies the implementation of Date as specified by the Date
 * interface
 * 
 * @author Victor Hazali
 * 
 */
public class DateImpl implements Date {

	/* Constants */
	private static final int	EMPTY_VALUE	= -1;

	/* Fields */
	private int					_year;
	private int					_month;
	private int					_day;
	private int					_hour;
	private int					_minute;

	/* Constructors */
	public DateImpl(int year, int month, int day, int hour, int minute) {
		setYear(year);
		setMonth(month);
		setDay(day);
		setHour(hour);
		setMinute(minute);
	}

	public DateImpl(int year, int month, int day) {
		setYear(year);
		setMonth(month);
		setDay(day);
		setHour(EMPTY_VALUE);
		setMinute(EMPTY_VALUE);
	}

	public DateImpl(int hour, int minute) {
		setYear(EMPTY_VALUE);
		setMonth(EMPTY_VALUE);
		setDay(EMPTY_VALUE);
		setHour(hour);
		setMinute(minute);
	}

	/* Getters and Setters */
	@Override
	public int getYear() {
		return _year;
	}

	public void setYear(int year) {
		_year = year;
	}

	@Override
	public int getMonth() {
		return _month;
	}

	public void setMonth(int month) {
		_month = month;
	}

	@Override
	public int getDay() {
		return _day;
	}

	public void setDay(int day) {
		_day = day;
	}

	@Override
	public int getHour() {
		return _hour;
	}

	public void setHour(int hour) {
		_hour = hour;
	}

	@Override
	public int getMinute() {
		return _minute;
	}

	public void setMinute(int minute) {
		_minute = minute;
	}

}
