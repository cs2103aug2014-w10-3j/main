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

	@Override
	public boolean create(String data) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean update(String data) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public List<String> read() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(String data) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean clear() {
		// TODO Auto-generated method stub
		return false;
	}

}
