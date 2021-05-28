package MiniProject;

import java.util.*;

public class Twitter {
	//graph using Adjacency list
	static ArrayList<AccGNode> GHead;
	static AccTNode accRoot;//avl
	static String retweetStr;  //stores retweet
	
	Twitter(){
		GHead = new ArrayList<AccGNode>();
		accRoot = null;
	}
	
	int height(AccTNode root){
        int lh, rh;
        if(root == null)
            return 0;

        if(root.left == null)
            lh = 0;
        else
            lh = 1 + root.left.h;

        if(root.right == null)
            rh = 0;
        else
            rh = 1 + root.right.h;

        if(lh > rh)
            return lh;
        else
            return rh;
    }

    int balanceFactor(AccTNode root){
        int bf, lh, rh;

        if(root == null)
            return 0;
        
        if(root.left == null)
            lh = 0;
        else 
            lh = 1 + height(root.left);

        if(root.right == null)
            rh = 0;
        else 
            rh = 1 + height(root.right);

        bf = lh - rh;
        return bf;
    }


    AccTNode LL(AccTNode root){ 
        //Right Rotation
    	AccTNode tmp = root.left;
        root.left = tmp.right;
        tmp.right = root;
        tmp.h = height(tmp);
        root.h = height(root);
        return tmp;
    }

    AccTNode RR(AccTNode root){ 
        //Left Rotation
    	AccTNode tmp = root.right;
        root.right = tmp.left;
        tmp.left = root;
        tmp.h = height(tmp);
        root.h = height(root);
        return tmp;
    }

    AccTNode LR(AccTNode root){ 
        //1) Left Rotation around child(alpha)
        //2) Right Rotation around alpha
        root.left = RR(root.left);
        root = LL(root);
        return root;
    }

    AccTNode RL(AccTNode root){ 
        //1) Right Rotation around child(alpha)
        //2) Left Rotation around alpha
        root.right = LL(root.right);
        root = RR(root);
        return root;
    }
	
    AccTNode insert(AccTNode root, Account A){ 
        int bf;
        if(root == null){
            root = new AccTNode(A);
            return root;
        }

        if(A.username.compareToIgnoreCase(root.A.username) < 0){
            root.left = insert(root.left, A);
            bf = balanceFactor(root);

            if(bf == 2){
                if(A.username.compareToIgnoreCase(root.left.A.username) < 0)
                    root = LL(root);
                else
                    root = LR(root);
            }
        }
        else{ 
            root.right = insert(root.right, A);
            bf = balanceFactor(root);

            if(bf == -2){
                if(A.username.compareToIgnoreCase(root.right.A.username) > 0)
                    root = RR(root);
                else
                    root = RL(root);
            }
        }
        root.h = height(root);
        return root;
    }
    
    AccTNode minValueNode(AccTNode node) 
    { 
    	AccTNode current = node; 
  
        /* loop down to find the leftmost leaf */
        while (current.left != null) 
        current = current.left; 
  
        return current; 
    } 
  
    int max(int a, int b) 
    { 
        return (a > b) ? a : b; 
    } 
    
   
    static Account searchUsername(String un){ 
        AccTNode ptr = accRoot;
        boolean flag = false;

        while(ptr != null){
            if(un.compareToIgnoreCase(ptr.A.username) == 0){
                flag = true;
                break;
            }
            if(un.compareToIgnoreCase(ptr.A.username) < 0)
                ptr = ptr.left;
            else 
                ptr = ptr.right;
        }

        if(flag)
            return ptr.A;
        else
            return null;
    }
    
    static Account searchUsernameGraph(String un){ 
        Iterator<AccGNode> ptr = GHead.iterator();

        while(ptr.hasNext())
        {
        	Account A1=ptr.next().A;
        	if(un.compareToIgnoreCase(A1.username)==0)
                return A1;
        }
        
       return null;        
    }
    
	void createAccount(Scanner sc) {
		Account A = new Account();
		String un;
		do {
			System.out.print("Enter username: ");
			un = sc.nextLine();
			if(searchUsername(un) != null)
				System.out.println("Entered username already exits.");
		}while(searchUsername(un) != null);
		A.username = un;
		A.accept(sc);
		
		//add user to graph
		AccGNode a = new AccGNode(A);
		GHead.add(a);
		
		//add account to avl tree
		accRoot = insert(accRoot, A);
		
		System.out.println("Your account has been successfully created!");
	}
	
	static AccTNode searchUsername1(String un){ 
        AccTNode ptr = accRoot;
        boolean flag = false;

        while(ptr != null){
            if(un.compareToIgnoreCase(ptr.A.username) == 0){
                flag = true;
                break;
            }
            if(un.compareToIgnoreCase(ptr.A.username) < 0)
                ptr = ptr.left;
            else 
                ptr = ptr.right;
        }

        if(flag)
            return ptr;
        else
            return null;
    }
	
	 AccTNode deleteAccount(AccTNode root, String und) {
	    	Scanner sc1 = new Scanner(System.in);
	    	AccTNode A;
	    	if(root == null) {
				return root;
			}
	    	A = searchUsername1(und);
	    	if(A == null) {
	    		System.out.println("Username not found!");            
	    	}
	    	else {
	    		System.out.println("Enter your password : ");
	    		String pwd = sc1.next();
	    		if(! pwd.equals(A.A.password)) {
	    			System.out.println("Incorrect Password...Try again"); 
	    		}
	    		else {
	    			if(und.compareTo(A.A.username) < 0) {       //left subtree 
	    				root.left = deleteAccount(root.left , und); //recursive call
	    			}
	    			else if(und.compareTo(A.A.username) > 0) {      //right subtree 
	    				root.right = deleteAccount(root.right , und); //recursive call
	    			}
	    			else {      //with only one child or no child 
	    				if ((root.left == null) || (root.right == null)) { 
	    					AccTNode temp = null; 
	    	                if (temp == root.left) //LEFT CHILD IS PRESENT OR NOT
	    	                    temp = root.right; 
	    	                else
	    	                    temp = root.left; 
	    	  
	    	                if (temp == null)  //// No child case 
	    	                { 
	    	                    temp = root; 
	    	                    root = null; 
	    	                } 
	    	                else // One child case 
	    	                    root = temp; // Copy the contents of the non-empty child 
	    	            }
	    				else {     //node with two children
	    					 AccTNode temp = minValueNode(root.right); //Get the inorder successor (smallest in the right subtree)
	    					 root.A.username = temp.A.username;
	    					 root.right = deleteAccount(root.right, temp.A.username); //Delete the inorder successor
	    				}
	    				if(root == null) {
	    					return root;
	    				}
	    				root.h = max(height(root.left), height(root.right)) + 1;
	    				System.out.println(root);
	    				int bl = balanceFactor(root);
	    				
	    				if (bl > 1 && balanceFactor(root.left) >= 0) 
	    		            return RR(root); 
	    		  
	    		        // Left Right Case 
	    		        if (bl > 1 && balanceFactor(root.left) < 0) 
	    		        { 
	    		            root.left = LL(root.left); 
	    		            return RR(root); 
	    		        } 
	    		  
	    		        // Right Right Case 
	    		        if (bl < -1 && balanceFactor(root.right) <= 0) 
	    		            return LL(root); 
	    		  
	    		        // Right Left Case 
	    		        if (bl < -1 && balanceFactor(root.right) > 0) 
	    		        { 
	    		            root.right = RR(root.right); 
	    		            return LL(root); 
	    		        } 
	    			}
	    		}
	    		 
	    	}
	    	return root;
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
	
	static void explore(Scanner sc,Account A,ArrayList<AccGNode> GHead) {
		byte ch;
		do {
			System.out.println("____________________________________");
			System.out.println("********* EXPLORE **********");
			System.out.println("0. Back");
			System.out.println("1. Search Username ");
			System.out.println("2. Follow a user");
			System.out.println("3. Retweet");
			//most popular user
			System.out.print("Enter your choice: ");
			ch = sc.nextByte();
			sc.nextLine();
			System.out.println("____________________________________");
			switch(ch) {
			case 0: break;
			
			case 1: System.out.print("Enter username to search: ");
					Account S = searchUsername(sc.nextLine());
					if(S == null)
						System.out.println("Username not found");
					else {
						System.out.println("Username found");
						S.viewProfile();
						//follow? / unfollow? / following?
					}
					break;
					
			case 2:
				System.out.print("Enter username to follow: ");
				String search = sc.nextLine();
				Account F = searchUsernameGraph(search);
				if(F == null)
					System.out.println("Username not found");
				else
				{
					for(AccGNode gN : GHead)
					{
						if(gN.A == F)
						{
							AccGNode temp = new AccGNode(F);
							if(gN.link == null)
								gN.link = temp;
							else
							{
								temp.link = gN.link;
								gN.link = temp;
							}
							A.friendsCount++;
							F.followersCount++;
							break;
						}
					}
					System.out.println("Username found");
					System.out.println("You now follow @" + search);
				}
				break;
				
//			case 3:char c='n';
//				int n;
//				User u1 = new User();
//				System.out.print("Enter username whose tweet you want to retweet : ");
//				String un = sc.nextLine();
//				Account a = searchUsername(un);
//				
//				if(a != null) {
//					System.out.println("Enter tweet number to retweet : ");
//					n = sc.nextInt();
//					for(int i = 0; i < u1.str1.size(); i++) {
//						if(i == n) {
//							System.out.println(u1.tweetStr);
//						}
//					}
//					System.out.println("You retweeted " + un);		
//				}
//				break;
			}
			
		}while(ch != 0);
	}
}