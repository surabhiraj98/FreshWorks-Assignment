package Surabhi.FreshWorks.dao;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;
import org.json.simple.JSONObject;

import Surabhi.FreshWorks.service.serviceImpl;

public class ProductImpl {
	
	JSONObject obj = new JSONObject();
	JSONObject prod = new JSONObject();
	serviceImpl srv = new serviceImpl();
	Scanner sc = new Scanner(System.in);
	
	@SuppressWarnings("unchecked")
	public void writeToFile(String file) {	
		try {
			JSONObject data = srv.getRecord(file);
			System.out.println("Enter Key ");
			String key = sc.next();
			if(key.toCharArray().length < 32 && srv.checkFileSize(file)) {
				
				if (data.get(key) == null)
	            {
					
					System.out.println("Enter Product Name ");
					obj.put("prodName", sc.next()); 
					System.out.println("Enter Product Price ");
					obj.put("prodPrice", sc.nextInt()); 
					
					
					// TODO : Here verify if the the JsonObject prod is less than 16KB
					System.out.println("Time to Live Property:Y/N ");
					if(sc.next().equalsIgnoreCase("Y")) {
						JSONObject time = new JSONObject();
						System.out.println("Enter Time in Seconds: ");
						LocalDateTime instant= LocalDateTime.now();
						time.put("seconds", sc.nextInt());
						time.put("Savingtime",""+instant);
						time.put("key", key);
						obj.put("TTL", time);
					}
					prod.put(key, obj);
					data.putAll(prod);
					if(prod.toString().length()>=16384) {
						System.out.println("Value Exceeds the Json object limit");
					}else {
						srv.writeRecord(file, data);
					}
					
	            } else {
	                System.out.println("Duplicate Key");
	            }
			}
			else {
				System.out.println("Key cannot be more than 32 chars");	
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			obj.clear();
			prod.clear();
		}
	}


	public void readFromFile(String fileName) {
		try {
			JSONObject data = srv.getRecord(fileName);
			System.out.println("Enter Key ");
			String key = sc.next();
			if(key.toCharArray().length < 32) {
				if (data.get(key) != null)
	            {
					JSONObject prodObject = (JSONObject)data.get(key);
					String productName = (String) prodObject.get("prodName");
			        Long prodPrice = (Long) prodObject.get("prodPrice");
			        System.out.println("Key: " + key);
			        System.out.println("Product Name  -->>" + productName + " \nProduct Price -->>" + prodPrice);
	            } else
	                System.out.println("Data Not Present");
			} else {
				System.out.println("Key cannot be more than 32 chars");	
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public void deleteFromFile(String fileName) throws IOException {
		JSONObject data = srv.getRecord(fileName);
		
		System.out.println("Enter Key ");
		String key = sc.next();
		if (data.get(key) != null)
        {
			data.remove(key);
			srv.writeRecord(fileName, data);
        } else {
        	System.out.println("No Such Element Present");
        }
	}
}
