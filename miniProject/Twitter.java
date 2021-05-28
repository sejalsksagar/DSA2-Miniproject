package miniProject;

import java.util.*;

public class Twitter
{
	// graph using Adjacency list
	static ArrayList<AccGNode> GHead;
	static AccTNode accRoot;// avl

	Twitter()
	{
		GHead = new ArrayList<AccGNode>();
		accRoot = null;
	}

	int height(AccTNode root)
	{
		int lh, rh;
		if (root == null)
			return 0;

		if (root.left == null)
			lh = 0;
		else
			lh = 1 + root.left.h;

		if (root.right == null)
			rh = 0;
		else
			rh = 1 + root.right.h;

		if (lh > rh)
			return lh;
		else
			return rh;
	}

	int balanceFactor(AccTNode root)
	{
		int bf, lh, rh;

		if (root == null)
			return 0;

		if (root.left == null)
			lh = 0;
		else
			lh = 1 + height(root.left);

		if (root.right == null)
			rh = 0;
		else
			rh = 1 + height(root.right);

		bf = lh - rh;
		return bf;
	}

	AccTNode LL(AccTNode root)
	{
		// Right Rotation
		AccTNode tmp = root.left;
		root.left = tmp.right;
		tmp.right = root;
		tmp.h = height(tmp);
		root.h = height(root);
		return tmp;
	}

	AccTNode RR(AccTNode root)
	{
		// Left Rotation
		AccTNode tmp = root.right;
		root.right = tmp.left;
		tmp.left = root;
		tmp.h = height(tmp);
		root.h = height(root);
		return tmp;
	}

	AccTNode LR(AccTNode root)
	{
		// 1) Left Rotation around child(alpha)
		// 2) Right Rotation around alpha
		root.left = RR(root.left);
		root = LL(root);
		return root;
	}

	AccTNode RL(AccTNode root)
	{
		// 1) Right Rotation around child(alpha)
		// 2) Left Rotation around alpha
		root.right = LL(root.right);
		root = RR(root);
		return root;
	}

	AccTNode insert(AccTNode root, Account A)
	{
		int bf;
		if (root == null)
		{
			root = new AccTNode(A);
			return root;
		}

		if (A.username.compareToIgnoreCase(root.A.username) < 0)
		{
			root.left = insert(root.left, A);
			bf = balanceFactor(root);

			if (bf == 2)
			{
				if (A.username.compareToIgnoreCase(root.left.A.username) < 0)
					root = LL(root);
				else
					root = LR(root);
			}
		}
		else
		{
			root.right = insert(root.right, A);
			bf = balanceFactor(root);

			if (bf == -2)
			{
				if (A.username.compareToIgnoreCase(root.right.A.username) > 0)
					root = RR(root);
				else
					root = RL(root);
			}
		}
		root.h = height(root);
		return root;
	}

	static Account searchUsername(String un)
	{
		AccTNode ptr = accRoot;
		boolean flag = false;

		while (ptr != null)
		{
			if (un.compareToIgnoreCase(ptr.A.username) == 0)
			{
				flag = true;
				break;
			}
			if (un.compareToIgnoreCase(ptr.A.username) < 0)
				ptr = ptr.left;
			else
				ptr = ptr.right;
		}

		if (flag)
			return ptr.A;
		else
			return null;
	}

	static AccGNode searchUsernameGraph(String un)
	{
		Iterator<AccGNode> ptr = GHead.iterator();

		while (ptr.hasNext())
		{
			AccGNode AG = ptr.next();
			if (un.compareToIgnoreCase(AG.A.username) == 0)
				return AG;
		}

		return null;
	}

	void addAccount(Account A)
	{
		// add account to graph
		AccGNode a = new AccGNode(A);
		// A.gHead = a;
		GHead.add(a);

		// add account to avl tree
		accRoot = insert(accRoot, A);
	}

	void createAccount(Scanner sc)
	{
		Account A = new Account();
		String un;
		do
		{
			System.out.print("Enter username: ");
			un = sc.nextLine();
			if (searchUsername(un) != null)
				System.out.println("Entered username already exits.");
		} while (searchUsername(un) != null);
		A.username = un;
		A.accept(sc);
		addAccount(A);
		System.out.println("Your account has been successfully created!");
	}

	// default users for debugging
	void defaultUsers()
	{
		Account A1 = new Account();
		A1.username = "sejal09";
		A1.name = "Sejal Kshirsagar";
		A1.setPassword("111");
		addAccount(A1);

		Account A2 = new Account();
		A2.username = "ketaki09";
		A2.name = "Ketaki Kothale";
		A2.setPassword("111");
		addAccount(A2);

		Account A3 = new Account();
		A3.username = "akanksha09";
		A3.name = "Akanksha Kulkarni";
		A3.setPassword("111");
		addAccount(A3);

		Account A4 = new Account();
		A4.username = "amruta09";
		A4.name = "Amruta Kotgire";
		A4.setPassword("111");
		addAccount(A4);
	}

	void login(Scanner sc)
	{
		String un;
		String pw;
		AccGNode AG;
		boolean loggedIn = false;
		do
		{
			System.out.print("Enter username: ");
			un = sc.nextLine();
			System.out.print("Enter password: ");
			pw = sc.nextLine();
			AG = searchUsernameGraph(un);
			if (AG == null)
			{
				char tryAgain;
				System.out.println("Username doesn't exist");
				System.out.println("Do you want to try again(y/n) : ");
				tryAgain = sc.nextLine().charAt(0);
				if (tryAgain == 'n')
					return;
			}
			else
			{
				if (AG.A.getPassword().equals(pw))
				{
					System.out.println("Login successful.");
					loggedIn = true;
					break;
				}
				else
				{
					char tryAgain;
					System.out.println("Incorrect password.");
					System.out.println("Do you want to try again(y/n) : ");
					tryAgain = sc.nextLine().charAt(0);
					if (tryAgain == 'n')
						return;
				}
			}
		} while (true);

		if (loggedIn)
			AG.A.activity(sc, AG, GHead);
	}

	// ignore this
	// this is to debug
	static void display()
	{
		for (AccGNode ii : GHead)
		{
			AccGNode pp = ii;
			while (pp != null)
			{
				System.out.print(pp.A.username + "->");
				pp = pp.link;
			}
			System.out.println();
		}
	}

	static void explore(Scanner sc, AccGNode AG, ArrayList<AccGNode> GHead)
	{
		byte ch;
		do
		{
			System.out.println("____________________________________");
			System.out.println("********* EXPLORE **********");
			System.out.println("0. Back");
			System.out.println("1. Search Username ");
			System.out.println("2. Follow a user");
			System.out.println("3. Unfollow a user");
			System.out.println("8.Debug Graph");
			// most popular user
			System.out.print("Enter your choice: ");
			ch = sc.nextByte();
			sc.nextLine();
			System.out.println("____________________________________");
			switch (ch)
			{
				case 0:
					break;

				case 1:
					System.out.print("Enter username to search: ");
					Account S = searchUsername(sc.nextLine());
					if (S == null)
						System.out.println("Username not found");
					else
					{
						System.out.println("Username found");
						S.viewProfile();
						// follow? / unfollow? / following?
					}
					break;

				case 2:
					System.out.print("Enter username to follow: ");
					String search = sc.nextLine();
					AccGNode F = searchUsernameGraph(search);
					if (F == null)
						System.out.println("Username not found");
					else
					{
						F.A.viewProfile();
						boolean isAlreadyFollow = false;

						AccGNode ptr = AG;
						while (ptr != null)
						{
							if (ptr.A.username == F.A.username)
							{
								System.out.println("You already follow this account");
								isAlreadyFollow = true;
								break;
							}
							ptr = ptr.link;
						}

						if (!isAlreadyFollow)
						{
							AccGNode temp = new AccGNode(F.A);
							if (AG.link == null)
								AG.link = temp;
							else
							{
								temp.link = AG.link;
								AG.link = temp;
							}
							AG.A.friendsCount++;
							F.A.followersCount++;
							System.out.println("Username found");
							System.out.println("You now follow @" + search);
						}
					}
					break;

				case 3:
					System.out.print("Enter username to unfollow: ");
					String search1 = sc.nextLine();

					AccGNode locate = AG.link;
					AccGNode prev = AG.link;
					for (locate = AG.link, prev = AG.link; locate != null
							&& locate.A.username.compareTo(search1) != 0; prev = locate, locate = locate.link)
					{

					}
					if (locate == null)
						System.out.println("You dont follow " + search1);
					else if (locate.A.username.compareTo(AG.link.A.username) == 0)// first node
					{
						AG.link = locate.link;
						AG.A.friendsCount--;
						//decrement follower count 
						System.out.println("You unfollowed " + search1);
					}
					else
					{
						prev.link = locate.link;
						AG.A.friendsCount--;
						//decrement follower count 
						System.out.println("You unfollowed " + search1);
					}
					break;

				case 8:
					display();
			}
		} while (ch != 0);
	}
}