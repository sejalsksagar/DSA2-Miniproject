package miniProject;

import java.time.Instant;
import java.time.ZoneId;
import java.time.LocalDateTime;
import java.util.*;

public class Account extends User
{
	AccGNode gHead;
	private String password;
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
		char ch;
		Account F;
		String search;
		boolean done = false;
		do
		{
			System.out.println("_________________________________________");
			System.out.println("\n******** Welcome @" + username + " *********\n");
			System.out.println("0. Log out");
			System.out.println("1. View your profile");
			System.out.println("2. Search a user");
			System.out.println("3. Alphabetically sorted list of usernames"); // avl tree inorder
			System.out.println("4. Follow an user");
			System.out.println("5. Unfollow an user");
			System.out.println("6. Followings at k-levels"); // BFS
			System.out.println("7. Most popular users"); // heap-sort
			System.out.println("8. Tweet");
			System.out.println("9. Display users graph adjacency list");
			System.out.print("Enter your choice: ");
			ch = sc.next().charAt(0);
			sc.nextLine(); // '\n'
			System.out.println("_________________________________________");

			switch (ch)
			{
				case '0':
					break;

				case '1':
					profile(sc, GHead);
					break;

				case '2':
					System.out.print("Enter username to search: ");
					F = Twitter.searchUsername(sc.nextLine());
					if (F == null)
						System.out.println("Entered username doesn't exist.");
					else
					{
						System.out.println("Username found.");
						// F.viewProfile();
						F.profile(sc, GHead);
					}
					break;

				case '3':
					System.out.println("Alphabetically sorted list of usernames (AVL Tree Inorder Traversal)");
					Twitter.displayAVL();
					break;

				case '4':
					System.out.print("Enter username to follow: ");
					search = sc.nextLine();
					F = Twitter.searchUsername(search);
					if (F == null)
						System.out.println("Entered username doesn't exist.");
					else
					{
						done = followAnAccount(F);
						if (done)
						{
							F.viewProfile();
							System.out.println("You now follow @" + search);
						}
						else
							System.out.println("You already follow this account");
					}
					break;

				case '5':
					System.out.print("Enter username to unfollow: ");
					search = sc.nextLine();
					F = Twitter.searchUsername(search);
					if (F == null)
						System.out.println("Entered username doesn't exist.");
					else
					{
						done = unfollowAnAccount(F);
						if (done)
						{
							F.viewProfile();
							System.out.println("You unfollowed @" + search);
						}
						else
							System.out.println("You don't follow this account");
					}
					break;

				case '6':
					bfs(GHead);
					break;

				case '7':
					heapSort(GHead);
					break;

				case '8':
					tweet(sc);
					break;

				case '9':
					Twitter.display();
					break;

				default:
					System.out.println("Invalid choice.");
			}
		} while (ch != '0');
	}

	void profile(Scanner sc, ArrayList<AccGNode> GHead)
	{
		char ch;
		AccGNode ptr;
		boolean found;
		viewProfile();
		do
		{
			System.out.println("____________________________________");
			System.out.println(" ************ VIEW ************");
			System.out.println("0. Back");
			System.out.println("1. Following");
			System.out.println("2. Followers");
			System.out.println("3. Tweets");
			System.out.print("Enter your choice: ");
			ch = sc.next().charAt(0);
			System.out.println("____________________________________");
			switch (ch)
			{
				case '0':
					break;

				case '1':
					found = false;
					ptr = gHead.link;
					while (ptr != null)
					{
						if (!found)
							System.out.println("FOLLOWING : ");
						System.out.println(ptr.A.username);
						found = true;
						ptr = ptr.link;
					}
					if (!found)
						System.out.println("Not following anyone yet.");
					break;

				case '2':
					found = false;
					for (AccGNode gN : GHead)
					{
						ptr = gN.link;
						while (ptr != null)
						{
							if (ptr.A.username.compareTo(username) == 0)
							{
								if (!found)
									System.out.println("FOLLOWERS : ");
								System.out.println(gN.A.username);
								found = true;
							}
							ptr = ptr.link;
						}
					}
					if (!found)
						System.out.println("No followers yet.");
					break;

				case '3':
					printTweet();
					break;

				default:
					System.out.println("Invalid choice.");
			}
		} while (ch != '0');
	}

	boolean followAnAccount(Account F)
	{
		AccGNode ptr = gHead;
		while (ptr != null)
		{
			if (ptr.A.username == F.username)
				return false; // already following
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
		return true; // successfully followed
	}

	boolean unfollowAnAccount(Account F)
	{
		AccGNode prev = gHead;
		AccGNode ptr = gHead.link;
		while (ptr != null)
		{
			if (ptr.A.equals(F))
			{
				prev.link = ptr.link;
				ptr = null;
				friendsCount--;
				F.followersCount--;
				return true; // successfully unfollowed
			}
			prev = ptr;
			ptr = ptr.link;
		}
		return false; // not already following
	}

	void bfs(ArrayList<AccGNode> GHead)
	{
		int L = 0;
		AccGNode v;
		Account A = null;
		AccGNode nullv = new AccGNode(A);
		Queue<AccGNode> Q = new ArrayDeque<AccGNode>();
		boolean visited[] = new boolean[GHead.size()];

		v = gHead; // start vertex
		Q.add(v);
		Q.add(nullv);
		visited[GHead.indexOf(v)] = true;

		System.out.println("Followings at k-levels (BFS) : \n");
		while (!Q.isEmpty())
		{
			v = Q.poll();
			if (v.equals(nullv))
				System.out.print("\n");
			else
			{
				v = v.A.gHead;
				System.out.print("    " + v.A.username);
			}

			while (v != null && !v.equals(nullv))
			{
				int i = GHead.indexOf(v.A.gHead);
				if (!visited[i])
				{
					visited[i] = true;
					Q.add(v);
				}
				v = v.link;
			}
			if (v == null && !Q.isEmpty() && Q.peek().equals(nullv))
			{
				Q.add(nullv);
				System.out.println("            ...Level " + (L++));
			}
		}
		System.out.println();
	}
}