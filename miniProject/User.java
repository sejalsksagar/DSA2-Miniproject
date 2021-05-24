package miniProject;

//import java.util.*;

public class User {
	
	static int counter = -1;
	int id; //vertex
	String name;
	String username;
	int followersCount; 	//no. of followers this account currently has.
	int friendsCount; 	//no. of users this account is following (AKA their “followings”).
	int favouritesCount; 	//no. of Tweets this user has liked in the account’s lifetime.
	int statusesCount; 	//no. of Tweets (including retweets) issued by the user.

	User(){
		counter++;
		id = counter;
		name = "";
		username = "";
		followersCount = 0;
		friendsCount = 0;
		favouritesCount = 0;
		statusesCount = 0;
	}
	
//	void accept(Scanner sc) {
//		System.out.print("Enter name: ");
//		name = sc.nextLine();
//		System.out.print("Enter username: ");
//		username = sc.nextLine();
//	}
}
