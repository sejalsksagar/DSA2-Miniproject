package MiniProject;
import java.util.*;

public class Tweet extends User{
	String createdAt;	//UTC time when this Tweet was created.
	String text;
			//The user who posted this Tweet.
	Tweet retweetedStatus; //This attribute contains a representation of the original Tweet that was retweeted.
	int favoriteCount;	//Indicates approximately how many times this Tweet has been liked by Twitter users.
	boolean favorited; //Indicates whether this Tweet has been liked by the authenticating user.
	ArrayList<String>str1 = new ArrayList<String>();
	Scanner sc = new Scanner(System.in);
	//Tweet t = new Tweet();

	public void tweet() {
		
		char ch='n';
		System.out.println("**COMPOSE BOX**");
		
		do {
			System.out.println("Write your tweet : ");
			text = sc.next();
			
			str1.add(text);
			//System.out.println(str1);
			tweetCount++;
			System.out.println("Do you want tweet again (y/n) :");
			ch = sc.next().charAt(0);
			
		}while(ch != 'n');
		
		System.out.println("Your tweets are : ");
    	for(int i = 0; i < str1.size();i++) {
    		System.out.println(i+1  + "  " + str1.get(i));
    	}
		
	}
	
	
//	public void printTweet(ArrayList<String> str1) {
//		boolean ans = str1.isEmpty();
//		
//        if (ans == true) {
//        	System.out.println("Tweeted nothing!");
//        }else {
//        	System.out.println("Your tweets are : ");
//        	for(int i = 0; i < str1.size();i++) {
//        		System.out.println(i+1  + "  " + str1.get(i));
//        	}
//        }
//	}
	
	
		
	
}
