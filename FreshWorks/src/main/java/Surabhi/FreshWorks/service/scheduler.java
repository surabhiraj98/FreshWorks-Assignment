package Surabhi.FreshWorks.service;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.TimerTask;

import org.json.simple.JSONObject;

public class scheduler extends TimerTask{
	//private Thread t;
	private String file = null;
	public scheduler(String file){
		this.file=file;
	}
	public void run() {
		serviceImpl srv = new serviceImpl();
		try {
			JSONObject data = srv.getRecord(file);
			for(Object key : data.keySet()) {
				JSONObject prodObject = (JSONObject)data.get(key);
				if(prodObject.get("TTL") != null) {
					JSONObject prod = (JSONObject)prodObject.get("TTL");
					Long seconds = (Long) prod.get("seconds");
					LocalDateTime saveingtime = LocalDateTime.parse((CharSequence) prod.get("Savingtime"));
					LocalDateTime instant = LocalDateTime.now();
					Duration duration = Duration.between(saveingtime, instant);
					if(duration.getSeconds()>=seconds) {
						if(data.get(key)!=null) {
							data.remove(key);
							srv.writeRecord(file, data);
							System.out.println("Key Timeout: "+key);
						}
					}
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
