package miniProject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class User
{

	static int counter = -1;
	int id; // vertex
	String name;
	String username;
	String joined;
	int followersCount; // no. of followers this account currently has.
	int friendsCount; // no. of users this account is following (AKA their “followings”).
	int statusesCount; // no. of Tweets (including retweets) issued by the user.
	String tweetStr;  //stores tweet
	int tweetCount; 
	ArrayList<Tweet>T = new ArrayList<Tweet>();

	User()
	{
		counter++;
		id = counter;
		name = "";
		username = "";
		joined = "";
		followersCount = 0;
		friendsCount = 0;
		statusesCount = 0;
	}
	
	public void tweet(Scanner sc) {
		Tweet t = new Tweet(sc);
		T.add(t);
		statusesCount++;
	}
	
	public void printTweet() {
        if (T.isEmpty()) {
        	System.out.println("No tweets yet!");
        }else {
        	System.out.println("************ TWEETS ***************");
        	for(int i = 0; i < T.size();i++) {
        		System.out.println(i);
        		T.get(i).display(username);
        		System.out.println("-----------------------------------");
        	}
        }
	}

	void viewProfile()
	{
		System.out.println("------------------------------------------------");
		System.out.println("****** " + name.toUpperCase() + " ******");
		System.out.println("@" + username);
		System.out.println(joined);
		System.out.println("\n"+statusesCount + " Tweets");
		System.out.println(friendsCount + " Following   " + followersCount + " Followers");
		System.out.println("------------------------------------------------");
	}

	void heapify(ArrayList<Account> TMP, int n, int i) {  //min heap
		int smallest = i; // Initialize smallest as root
        int l = 2 * i + 1; // left = 2*i + 1
        int r = 2 * i + 2; // right = 2*i + 2
        if (l <  n && TMP.get(l).followersCount < TMP.get(smallest).followersCount)
            smallest = l;
 
        // If right child is smaller than smallest so far
        if (r < n && TMP.get(r).followersCount < TMP.get(smallest).followersCount)
            smallest = r;
 
        // If smallest is not root
        if (smallest != i) {
        	Collections.swap(TMP, i, smallest);
 
            // Recursively heapify the affected sub-tree
            heapify(TMP, n, smallest);
        }
	}
	
	void heapSort(ArrayList<AccGNode> GHead) {
		
		ArrayList<Account> TMP = new ArrayList<Account>();
		for(AccGNode g : GHead)
			TMP.add(g.A);
		
		for (int i = TMP.size()/2 - 1; i >= 0; i--)
            heapify(TMP, TMP.size(), i);
		
        // One by one extract an element from heap
        for (int i = TMP.size() - 1; i > 0; i--) {
             
            // Move current root to end
        	Collections.swap(TMP, i, 0);
        	
            // call max heapify on the reduced heap
            heapify(TMP, i, 0);
        }
        
        printMPU(TMP);
	}
	
	
	void printMPU(ArrayList<Account> TMP) {
		System.out.println("\tUsername " + "\t" + " Number of Followers");
		for (int i = 0; i < TMP.size(); i++) {
			System.out.format("\n %15s %10d", TMP.get(i).username, TMP.get(i).followersCount);
		}
		System.out.println();
	}
}