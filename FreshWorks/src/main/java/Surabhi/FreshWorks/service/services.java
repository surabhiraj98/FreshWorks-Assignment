package Surabhi.FreshWorks.service;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public interface services {
	
	public Boolean createRecord(String file) throws ParseException;
	
	public void writeRecord(String file, JSONObject object) throws IOException;
	
	public JSONObject getRecord(String file) throws IOException;
	
}
