package miniProject;


public class User
{

	static int counter = -1;
	int id; // vertex
	String name;
	String username;
	String joined;
	int followersCount; // no. of followers this account currently has.
	int friendsCount; // no. of users this account is following (AKA their “followings”).
	int favouritesCount; // no. of Tweets this user has liked in the account’s lifetime.
	int statusesCount; // no. of Tweets (including retweets) issued by the user.

	User()
	{
		counter++;
		id = counter;
		name = "";
		username = "";
		joined = "";
		followersCount = 0;
		friendsCount = 0;
		favouritesCount = 0;
		statusesCount = 0;
	}

	void viewProfile()
	{
		System.out.println("------------------------------------------------");
		System.out.println("****** " + name.toUpperCase() + " ******");
		System.out.println("@" + username);
		System.out.println(joined);
		System.out.println(statusesCount + " Tweets");
		System.out.println(favouritesCount + " Likes");
		System.out.println(friendsCount + " Following   " + followersCount + " Followers");
		System.out.println("------------------------------------------------");
	}

}