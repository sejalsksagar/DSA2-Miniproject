package miniProject;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class Tweet {
	String createdAt;	//UTC time when this Tweet was created.
	String text;
	
	Tweet(Scanner sc){
		Instant instant = Instant.now();
		LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		createdAt = ldt.getDayOfMonth() + " " + ldt.getMonth() + " " + ldt.getYear();
		System.out.println("********** TWEET ***********");
		System.out.println("What's happening? : ");
		text = sc.next();
	}
	
	void display(String username){
		System.out.println("@"+username+" tweet on "+createdAt);
		System.out.println(text);
	}
}
