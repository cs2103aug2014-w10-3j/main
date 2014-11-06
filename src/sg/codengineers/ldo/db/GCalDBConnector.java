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
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	private static final JacksonFactory JSON_FACTORY = new JacksonFactory();
	
	// Or your redirect URL for web based applications.
	private static final String REDIRECT_URL = "urn:ietf:wg:oauth:2.0:oob";
	private static final String SCOPE = "https://www.googleapis.com/auth/calendar";
	private static final GoogleAuthorizationCodeFlow FLOW = new GoogleAuthorizationCodeFlow.Builder(
			HTTP_TRANSPORT, JSON_FACTORY, CLIENT_ID, CLIENT_SECRET,
			Arrays.asList(CalendarScopes.CALENDAR))
			.setAccessType("online").setApprovalPrompt("auto").build();

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
	
	public static String getAuthURL() {
		String url = FLOW.newAuthorizationUrl().setRedirectUri(REDIRECT_URL)
				.build();
		return url;
	}
	
	/**
	 * Convert the object passed in to a task object
	 * This breaks some abstraction and might seem unclean
	 * but it is a good way of really separating the components
	 * and make the database layer really able to handle any
	 * model class
	 * 
	 * @param obj
	 * @return
	 */
	private static Task convertToTask(Object obj) {
		assert(obj instanceof Task);
		Task task = (Task) obj;
		return task;
	}
	
	private static Event taskToEvent(Task task) {
		Event event = new Event();
		
		event.setId(String.valueOf(task.getId()));
		event.setSummary(task.getName());
		event.setDescription(task.getDescription());
		
	    DateTime start = new DateTime(task.getStartTime(), TimeZone.getTimeZone("UTC"));
	    DateTime end = new DateTime(task.getEndTime(), TimeZone.getTimeZone("UTC"));

		event.setStart(new EventDateTime().setDateTime(start));
		event.setEnd(new EventDateTime().setDateTime(end));
		
		return event;
	}
	
	private static Task eventToTask(Event event) {
		Task task = new TaskImpl();
		
		task.setId(Integer.parseInt(event.getId()));
		task.setName(event.getSummary());
		task.setDescription(event.getDescription());
		
		task.setTimeStart(new Date(event.getStart().getDateTime().getValue()));
		task.setTimeStart(new Date(event.getEnd().getDateTime().getValue()));
		
		return task;
	}
}
