package Surabhi.FreshWorks;

import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;

import org.json.simple.parser.ParseException;

import Surabhi.FreshWorks.dao.ProductImpl;
import Surabhi.FreshWorks.service.scheduler;
import Surabhi.FreshWorks.service.serviceImpl;

public class App 
{
    public static void main( String[] args ) throws IOException, ParseException
    {
    	Scanner sc = new Scanner(System.in);
        serviceImpl srv = new serviceImpl();
        ProductImpl prodImpl = new ProductImpl();
        String fileName = "Dummy.json";
        System.out.println("Provide a custom file path : Y/N");
        String yn = sc.next();
        if(yn.equalsIgnoreCase("Y")) {
        	fileName = sc.next() + fileName;
        }
        srv.createRecord(fileName);
        boolean loop = true;
        
        Timer t = new Timer();
        try {
			scheduler sed = new scheduler(fileName);
			t.scheduleAtFixedRate(sed, 10, 10);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Something with Timer");
		}
		while (loop) {
			System.out.println( "----------------FreshWorks---------------" );
			
			System.out.println("0. Exit \n1. Read \n2. Write \n3. Delete");
			int choice = sc.nextInt();
			
			switch (choice) {
			case 0:
				loop = false;
				break;
			case 1:
				prodImpl.readFromFile(fileName);
				break;
			case 2:
				prodImpl.writeToFile(fileName);
				System.out.println("File wrote successfully");
				break;
			case 3:
				prodImpl.deleteFromFile(fileName);
				break;
			default:
				srv.createRecord(fileName);
			}
		} 
		
		System.out.println("You Have Successfully Exited the Application \nThank You for using this application");
		sc.close();
		t.cancel();
    }
}
