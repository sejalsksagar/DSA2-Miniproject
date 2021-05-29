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


	void addAccount(Account A)
	{
		// add account to graph
		AccGNode a = new AccGNode(A);
		A.gHead = a;
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

	// default users for debugging & demonstration
	void defaultUsers()
	{
		Account A0 = new Account();
		A0.username = "sejal00";
		A0.name = "Sejal Kshirsagar";
		A0.setPassword("111");
		addAccount(A0);

		Account A1 = new Account();
		A1.username = "ketaki01";
		A1.name = "Ketaki Kothale";
		A1.setPassword("111");
		addAccount(A1);

		Account A2 = new Account();
		A2.username = "akanksha02";
		A2.name = "Akanksha Kulkarni";
		A2.setPassword("111");
		addAccount(A2);

		Account A3 = new Account();
		A3.username = "amruta03";
		A3.name = "Amruta Kotgire";
		A3.setPassword("111");
		addAccount(A3);
		
		Account A4 = new Account();
		A4.username = "apoorva04";
		A4.name = "Apporva S.";
		A4.setPassword("111");
		addAccount(A4);

		Account A5 = new Account();
		A5.username = "shreya05";
		A5.name = "Shreya G.";
		A5.setPassword("111");
		addAccount(A5);
		
		Account A6 = new Account();
		A6.username = "nikita06";
		A6.name = "Nikita M.";
		A6.setPassword("111");
		addAccount(A6);

		Account A7 = new Account();
		A7.username = "saniya07";
		A7.name = "Saniya P.";
		A7.setPassword("111");
		addAccount(A7);
		
		//creating default graph
		A0.followAnAccount(GHead, A1);
		A0.followAnAccount(GHead, A2);
		A1.followAnAccount(GHead, A3);
		A1.followAnAccount(GHead, A4);
		A2.followAnAccount(GHead, A5);
		A2.followAnAccount(GHead, A6);
		A3.followAnAccount(GHead, A1);
		A3.followAnAccount(GHead, A7);
		A4.followAnAccount(GHead, A1);
		A4.followAnAccount(GHead, A7);
		A5.followAnAccount(GHead, A2);
		A5.followAnAccount(GHead, A7);
		A6.followAnAccount(GHead, A2);
		A6.followAnAccount(GHead, A7);
		A7.followAnAccount(GHead, A4);
		A7.followAnAccount(GHead, A5);
		A7.followAnAccount(GHead, A6);
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
				System.out.print("Do you want to try again(y/n) : ");
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
					System.out.print("Do you want to try again(y/n) : ");
					tryAgain = sc.nextLine().charAt(0);
					if (tryAgain == 'n')
						return;
				}
			}
		} while (true);

		if (loggedIn)
			A.activity(sc, GHead);
	}
	
	static void displayAVL() {
		if (accRoot == null)
        { 
        	System.out.println("NO ACCOUNTS AVAILABLE");
            return;
        }
		System.out.println("\tUSERNAME" +"\t\t"+ "NAME");
		System.out.println("-----------------------------------------");
		inorder(accRoot);
	}
	//AVL Tree Inorder traversal,L-N-R
	static void inorder(AccTNode root)             
	{
		if(root!=null) {
			inorder(root.left);
	        System.out.format("%15s %20s",root.A.username,root.A.name);
	        System.out.println("\n");
	        inorder(root.right); 
		}
    }

	//for debugging graph
	static void display()
	{
		System.out.println("USERS GRAPH USING ADJACENY LIST :\n");
		for (AccGNode ii : GHead)
		{
			AccGNode pp = ii;
			while (pp != null)
			{
				System.out.print(pp.A.username + " -> ");
				pp = pp.link;
			}
			System.out.println("\n");
		}
	}

}
