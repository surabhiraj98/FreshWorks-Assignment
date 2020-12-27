package Surabhi.FreshWorks.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class serviceImpl implements services{

	public Boolean createRecord(String file) throws ParseException {
		try {
		      File myObj = new File(file);
		      if (myObj.createNewFile()) {
		        System.out.println("File created: " + myObj.getName());
		        JSONParser parser = new JSONParser();  
		        JSONObject json = (JSONObject) parser.parse("{\"2\":{\"prodName\":\"Pizza\",\"prodPrice\":12000}}");
		        writeRecord(file,json);
		      } else {
		        System.out.println("File already exists.");
		      }
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		return null;
	}

	public void writeRecord(String file, JSONObject obj) throws IOException {
		OutputStream os = null;
		try {
			if(checkFileSize(file)) {
				os = new FileOutputStream(new File(file), false);
				os.write(obj.toJSONString().getBytes(), 0, obj.toJSONString().length());
				}
			} 
			catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				os.flush();
				os.close();
			}	
	}
	
	public boolean checkFileSize(String file) {
		final long SIZE_1GB = 1073741824L;
		boolean check = true;
		if(getFileSize(file)>SIZE_1GB && getFileSize(file)>0) {
			System.out.println("File Size more than 1GB.");
			check = false;
		}
		return check;
	}
	public long getFileSize(String file) {
		File myObj = new File(file);
	      if (!myObj.exists() || !myObj.isFile()) {
	         System.out.println("File doesn't exist");
	         return -1;
	      }
	      return myObj.length();
	   }



	public JSONObject getRecord(String file) throws IOException {
		JSONParser parser = new JSONParser();
		FileReader reader = null;
	      try {
	    	 reader = new FileReader(file);
	         
	         if(!reader.toString().isEmpty() && !reader.toString().equals("")) {
	        	 Object obj = parser.parse(reader);
	        	 JSONObject jsonObject = (JSONObject)obj;
	        	 return jsonObject;
	         }
	      } catch(Exception e) {
	         e.printStackTrace();
	      } finally {
	    	  parser.reset();
	    	  //reader.close();
	      }
		return null;
	}
}
