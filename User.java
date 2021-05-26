package MiniProject;
import java.util.*;
import MiniProject.Twitter;
public class User {
	static int counter = -1;
	int id; //vertex
	String name;
	String username;
	String joined;
	String tweetStr;  //stores tweet
	//String retweetStr;  //stores retweet
	int followersCount; 	//no. of followers this account currently has.
	int friendsCount; 	//no. of users this account is following (AKA their “followings”).
	int favouritesCount; 	//no. of Tweets this user has liked in the account’s lifetime.
	int tweetCount; 	//no. of Tweets issued by the user.
	ArrayList<String>str1 = new ArrayList<String>();
	
	User(){
		counter++;
		id = counter;
		name = "";
		username = "";
		joined = " ";
		followersCount = 0;
		friendsCount = 0;
		favouritesCount = 0;
		tweetCount = 0;
	}
	
	public void tweet(Scanner sc) {
		//User u = new User();
		char ch='n';
		do {
			System.out.println("Write your tweet : ");
			tweetStr = sc.next();
			
			str1.add(tweetStr);
			tweetCount = tweetCount + 1;
			System.out.println("Do you want tweet again (y/n) :");
			ch = sc.next().charAt(0);
		}while(ch != 'n');
	}
	
	public void printTweet() {
		 boolean ans = str1.isEmpty();
		
        if (ans == true) {
        	System.out.println("Tweeted nothing!");
        }else {
        	System.out.println("Your tweets are :");
        	for(int i = 0; i < str1.size();i++) {
        		System.out.println(i+1  + "  " + str1.get(i));
        	}
        }
		 
		
	}
	
	public void printRetweet() {
		 boolean ans = str1.isEmpty();
	        if (ans == true) {
	        	System.out.println("You tweeted nothing!");
	        }else {
	        	System.out.println("Your tweets are :");
	        	System.out.println(str1);
	        }
		
	}
		
	
	void viewProfile() {
		System.out.println("------------------------------------------------");
		System.out.println("****** "+name.toUpperCase()+" ******");
		System.out.println("@"+username);
		System.out.println(joined);
		System.out.println(tweetCount+"Tweets");
		System.out.println(favouritesCount+" Likes");
		System.out.println(friendsCount+" Following   "+followersCount+" Followers");
		System.out.println("------------------------------------------------");
	}
	
	void profile(Scanner sc) {
		byte ch;
		viewProfile();
		do {
			System.out.println("____________________________________");
			System.out.println("********** VIEW **********");
			System.out.println("0. Back");
			System.out.println("1. Following");
			System.out.println("2. Followers");
			System.out.println("3. Tweet"); //for writing tweet
			System.out.println("4. Likes");
			System.out.print("Enter your choice: ");
			ch = sc.nextByte();
			System.out.println("____________________________________");
			switch(ch) {
			case 0 :System.out.println("Exit");
			break;
			case 1:
			break;
			case 2:
			break;
			case 3:tweet(sc);
			break;
			
			
			}
		}while(ch != 0);
	}
	
	//void notification()
	
    
}
