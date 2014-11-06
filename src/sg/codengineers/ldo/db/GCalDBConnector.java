package sg.codengineers.ldo.db;

/**
 * This class is the implementation of the DBConnector
 * interface. Specifically, it is the connection between
 * the program and google calendar. It handles the synchronization 
 * of data between the program and google calendar. It is only able
 * handle objects of type "Task". This breaks a little abstraction but
 * we are left clear components
 * 
 * @author Sean
 */

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.*;
import com.google.api.services.calendar.model.Calendar;

import sg.codengineers.ldo.logic.TaskImpl;
import sg.codengineers.ldo.model.Task;

import java.io.*;
import java.util.*;

public class GCalDBConnector implements DBConnector {

	// The clientId and clientSecret can be found in Google Developers
	// Console
	private static final String CLIENT_ID = "300670791643-aqcjcpka4r18bnr53rl5vtvj2h88l9ga.apps.googleusercontent.com";
	private static final String CLIENT_SECRET = "pp9dS5SHzSEl_pu5hXw0ZFDk";

	private static final String CALENDAR_SUMMARY = "L'Do";
	private static final String CALENDAR_ID = "L-DO@cs2103.nus.edu.sg";

	// No way around this since there is a name clash in the imported libraries
	private com.google.api.services.calendar.Calendar service = null;
	private List<Event> gCalEvents = null;

	@Override
	public boolean create(Object data) {
		try {			
			Task task = convertToTask(data);			
			Event event = taskToEvent(task);

			Event createdEvent = service.events().insert(CALENDAR_ID, event).execute();
			gCalEvents.add(createdEvent);

			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;	
		}
	}

	@Override
	public boolean update(Object data) {
		try {			
			Task task = convertToTask(data);			
			Event event = taskToEvent(task);

			Event updatedEvent = service.events().patch(CALENDAR_ID, event.getId(), event).execute();

			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;	
		}
	}

	@Override
	public List<String> read() {
		try {
			Events events = service.events().list(CALENDAR_ID).execute();
			gCalEvents = events.getItems();

			List<String> taskList = new ArrayList<String>();

			for (Event e : gCalEvents) {
				taskList.add(eventToTask(e).toString());
			}

			return taskList;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean delete(Object data) {
		try {			
			Task task = convertToTask(data);			
			Event event = taskToEvent(task);

			service.events().delete(CALENDAR_ID, event.getId()).execute();

			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;	
		}
	}

	@Override
	public boolean clear() {
		// TODO Auto-generated method stub
		return false;
	}

}
