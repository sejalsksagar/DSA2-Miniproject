package MiniProject;
import java.util.*;

import MiniProject.Twitter;
public class User {
	static int counter = -1;
	int id; //vertex
	String name;
	String username;
	String joined;
	int followersCount; 	//no. of followers this account currently has.
	int friendsCount; 	//no. of users this account is following (AKA their “followings”).
	int favouritesCount; 	//no. of Tweets this user has liked in the account’s lifetime.
	int tweetCount; 	//no. of Tweets issued by the user.

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
	
	
	void viewProfile() {
		System.out.println("------------------------------------------------");
		System.out.println("* "+name.toUpperCase()+" *");
		System.out.println("@"+username);
		System.out.println(joined);
		System.out.println(tweetCount+"Tweets");
		System.out.println(favouritesCount+" Likes");
		System.out.println(friendsCount+" Following   "+followersCount+" Followers");
		System.out.println("------------------------------------------------");
	}
	
	void profile(Scanner sc, ArrayList<AccGNode> GHead) {
		byte ch;
		
		viewProfile();
		do {
			System.out.println("____");
			System.out.println("** VIEW **");
			System.out.println("0. Back");
			System.out.println("1. Following");
			System.out.println("2. Followers");
			System.out.println("3. Post Tweets"); 
			System.out.println("4. Likes");
			System.out.print("Enter your choice: ");
			ch = sc.nextByte();
			System.out.println("____");
			switch(ch) {
			case 0 :System.out.println("Exit");
			break;
			
			case 1:
				boolean found = false;
				for (AccGNode gN : GHead)
				{
					if (gN.A.username.compareTo(username) == 0)
					{
						AccGNode ptr = gN.link;
						while (ptr != null)
						{
							if (found == false)
								System.out.println("Accounts you follow are : ");
							System.out.println(ptr.A.username);
							found = true;
							ptr = ptr.link;
						}
						break;
					}
				}
				if (found == false)
					System.out.println("You don't follow anyone");
				break;

			case 2:
				boolean found1 = false;
				for (AccGNode gN : GHead)
				{
					AccGNode ptr = gN.link;
					while (ptr != null)
					{
						if (ptr.A.username.compareTo(username) == 0)
						{
							if (found1 == false)
								System.out.println("You are followed by : ");
							System.out.println(gN.A.username);
							found1 = true;
						}
						ptr = ptr.link;
					}
				}
				if (found1 == false)
					System.out.println("You aren't followed by anyone");
				break;
			case 3:Tweet t = new Tweet();
				t.tweet();
			break;
			
			
			}
		}while(ch != 0);
	}
	
	void heapify(ArrayList<AccGNode> GHead,int n, int i) {  //min heap
		int smallest = i; // Initialize smalles as root
        int l = 2 * i + 1; // left = 2*i + 1
        int r = 2 * i + 2; // right = 2*i + 2
        if (l <  n && GHead.get(l).A.followersCount < GHead.get(smallest).A.followersCount)
            smallest = l;
 
        // If right child is smaller than smallest so far
        if (r < n && GHead.get(r).A.followersCount < GHead.get(smallest).A.followersCount)
            smallest = r;
 
        // If smallest is not root
        if (smallest != i) {

        	Collections.swap(GHead, i, smallest);
 
            // Recursively heapify the affected sub-tree
            heapify(GHead,GHead.size(), smallest);
        }
	}
	
	void sortArraylist(ArrayList<AccGNode> GHead) {
		for (int i = GHead.size() / 2 - 1; i >= 0; i--)
            heapify(GHead,GHead.size(), i);
 
        // One by one extract an element from heap
        for (int i = GHead.size()  - 1; i >= 0; i--) {
             
            // Move current root to end
        	Collections.swap(GHead, 0, i);
        	
        	
            // call max heapify on the reduced heap
            heapify(GHead,i, 0);
        }
	}
	
	
	
	void printMPU(ArrayList<AccGNode> GHead) {
		System.out.println("Username " + "\t" + " Followers");
		for (int i = 0; i < GHead.size(); ++i) {
			System.out.println(GHead.get(i).A.username + "   " + GHead.get(i).A.followersCount);
		}
            

	}
}
    
