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

	static Account searchUsernameGraph(String un)
	{
		Iterator<AccGNode> ptr = GHead.iterator();

		while (ptr.hasNext())
		{
			Account A1 = ptr.next().A;
			if (un.compareToIgnoreCase(A1.username) == 0)
				return A1;
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
		Account A;
		boolean loggedIn = false;
		do
		{
			System.out.print("Enter username: ");
			un = sc.nextLine();
			System.out.print("Enter password: ");
			pw = sc.nextLine();
			A = searchUsername(un);
			if (A == null)
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
				if (A.getPassword().equals(pw))
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
			A.activity(sc, A, GHead);
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

	static void explore(Scanner sc, Account A, ArrayList<AccGNode> GHead)
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
					Account F = searchUsernameGraph(search);
					if (F == null)
						System.out.println("Username not found");
					else
					{
						F.viewProfile();
						boolean isAlreadyFollow = false;
						for (AccGNode gNode : GHead)
						{
							if (gNode.A.username == A.username)
							{
								AccGNode ptr = gNode;
								while (ptr != null)
								{
									if (ptr.A.username == F.username)
									{
										System.out.println("You already follow this account");
										isAlreadyFollow = true;
										break;
									}
									ptr = ptr.link;
								}
							}
						}
						for (AccGNode gN : GHead)
						{
							if (gN.A.username == A.username && !isAlreadyFollow)
							{
								AccGNode temp = new AccGNode(F);
								if (gN.link == null)
									gN.link = temp;
								else
								{
									temp.link = gN.link;
									gN.link = temp;
								}
								A.friendsCount++;
								F.followersCount++;
								System.out.println("Username found");
								System.out.println("You now follow @" + search);
								break;
							}
						}
					}
					break;

				case 3:
					System.out.print("Enter username to unfollow: ");
					String search1 = sc.nextLine();
					for (AccGNode gN : GHead)
					{
						if (gN.A.username == A.username)
						{
							AccGNode locate = gN.link;
							AccGNode prev = gN.link;
							for (locate = gN.link, prev = gN.link; locate != null
									&& locate.A.username.compareTo(search1) != 0; prev = locate, locate = locate.link)
							{
								System.out.println(locate.A.username);
							}
							if (locate == null)
								System.out.println("You dont follow " + search1);
							else if (locate.A.username.compareTo(gN.link.A.username) == 0)// first node
							{
								gN.link = locate.link;
								System.out.println("You unfollowed " + search1);
							}
							else
							{
								prev.link = locate.link;
								System.out.println("You unfollowed " + search1);
							}
							break;
						}
					}
					break;

				case 8:
					display();
			}
		} while (ch != 0);
	}
}