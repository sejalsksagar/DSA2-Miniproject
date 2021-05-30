package MiniProject;


import java.time.Instant;
import java.time.ZoneId;
import java.time.LocalDateTime;
import java.util.*;

public class Account extends User
{
	AccGNode gHead;
	String password;
	String createdOn; // UTC datetime that the user account was created on Twitter.
	
	Account()
	{
		Instant instant = Instant.now();
		createdOn = instant.toString();
		LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		super.joined = "Joined " + ldt.getMonth() + " " + ldt.getYear();
	}

	void accept(Scanner sc)
	{

		System.out.print("Enter name: ");
		name = sc.nextLine();

		System.out.print("Enter password: ");
		setPassword(sc.nextLine());
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	void activity(Scanner sc, ArrayList<AccGNode> GHead)
	{
		Tweet tw = new Tweet();
		byte ch;
		do
		{
			System.out.println("____________");
			System.out.println("*** Welcome @" + username + " ****");
			System.out.println("0. Log out");
			System.out.println("1. Home");
			System.out.println("2. Explore");
			System.out.println("3. Notifications");
			System.out.println("4. Profile");
			//System.out.println("5. Post Tweets");
			System.out.println("6. More");
			System.out.print("Enter your choice: ");
			ch = sc.nextByte();
			System.out.println("____________");

			switch (ch)
			{
				case 0:
					break;

				case 1:
					break;

				case 2: explore(sc, GHead);
					break;

				case 3:
					break;

				case 4: profile(sc,GHead);
					break;
				
//				case 5:tw.tweet();
//					break;
				
			}
		} while (ch != 0);
	}
	
	void explore(Scanner sc, ArrayList<AccGNode> GHead) {
		byte ch;
		Account F;
		String search;
		boolean done = false;
		
		do {
			System.out.println("____________");
			System.out.println("*** EXPLORE ****");
			System.out.println("0. Back");
			System.out.println("1. Search Username ");
			System.out.println("2. Follow a user");
			System.out.println("3. Unfollow a user");
			System.out.println("8. Debug Graph");
			System.out.print("Enter your choice: ");
			ch = sc.nextByte();
			sc.nextLine();
			System.out.println("____________");
			switch(ch) {
			case 0: break;
			
			case 1: System.out.print("Enter username to search: ");
					F = Twitter.searchUsername(sc.nextLine());
					if (F == null) System.out.println("Entered username doesn't exist.");
					else
					{
						System.out.println("Username found.");
						F.viewProfile();
					}
					break;
			
			case 2: System.out.print("Enter username to follow: ");
					search = sc.nextLine();
					F = Twitter.searchUsername(search);
					if (F == null) System.out.println("Entered username doesn't exist.");
					else {
						done = followAnAccount(F);
						if(done) {
							F.viewProfile();
							System.out.println("You now follow @" + search);
						}
						else System.out.println("You already follow this account");
					}
					break;
					
			case 3: System.out.print("Enter username to unfollow: ");
					search = sc.nextLine();
					F = Twitter.searchUsername(search);
					if (F == null) System.out.println("Entered username doesn't exist.");
					else {
						done = unfollowAnAccount(F);
						if(done) {
							F.viewProfile();
							System.out.println("You unfollowed @" + search);
						}
						else System.out.println("You don't follow this account");
					}
					break;
					
			case 8: Twitter.display();
					 break;
					 
			default: System.out.println("Invalid choice.");
			}
		}while(ch!=0);
	}
	
	boolean followAnAccount(Account F) {
		AccGNode ptr = gHead;
		while (ptr != null)
		{
			if (ptr.A.username == F.username)
				return false; //already following
			ptr = ptr.link;
		}
		
		AccGNode temp = new AccGNode(F);
		if (gHead.link == null)
				gHead.link = temp;
		else
		{
			temp.link = gHead.link;
			gHead.link = temp;
		}
		friendsCount++;
		F.followersCount++;
		return true; //successfully followed 
	}
	
	boolean unfollowAnAccount(Account F) {
		AccGNode prev = gHead;
		AccGNode ptr = gHead.link;
		while(ptr!=null) {
			if(ptr.A.equals(F)) {
				prev.link = ptr.link;
				ptr = null;
				friendsCount--;
				F.followersCount--;
				return true; //successfully unfollowed
			}
			prev = ptr;
			ptr = ptr.link;
		}
		return false; //not already following
	}
	
	
}