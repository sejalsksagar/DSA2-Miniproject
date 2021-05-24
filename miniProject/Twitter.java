package miniProject;

import java.util.*;


public class Twitter {
	//graph using Adjacency list
	ArrayList<UserGNode> GHeads;
	AccTNode accRoot;//avl
	
	Twitter(){
		GHeads = new ArrayList<UserGNode>();
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
        System.out.println("LL");
        return tmp;
    }

    AccTNode RR(AccTNode root){ 
        //Left Rotation
    	AccTNode tmp = root.right;
        root.right = tmp.left;
        tmp.left = root;
        tmp.h = height(tmp);
        root.h = height(root);
        System.out.println("RR");
        return tmp;
    }

    AccTNode LR(AccTNode root){ 
        //1) Left Rotation around child(alpha)
        //2) Right Rotation around alpha
        root.left = RR(root.left);
        root = LL(root);
        System.out.println("LR");
        return root;
    }

    AccTNode RL(AccTNode root){ 
        //1) Right Rotation around child(alpha)
        //2) Left Rotation around alpha
        root.right = LL(root.right);
        root = RR(root);
        System.out.println("RL");
        return root;
    }
	
    AccTNode insert(AccTNode root, Account A){ 
        int bf;

        if(root == null){
            root = new AccTNode(A);
            return root;
        }

        if(A.U.username.compareToIgnoreCase(root.A.U.username) < 0){
            root.left = insert(root.left, A);
            bf = balanceFactor(root);

            if(bf == 2){
                if(A.U.username.compareToIgnoreCase(root.left.A.U.username) < 0)
                    root = LL(root);
                else
                    root = LR(root);
            }
        }
        else{ 
            root.right = insert(root.right, A);
            bf = balanceFactor(root);

            if(bf == -2){
                if(A.U.username.compareToIgnoreCase(root.right.A.U.username) > 0)
                    root = RR(root);
                else
                    root = RL(root);
            }
        }

        root.h = height(root);
        return root;
    }
    
    Account searchUsername(String un){ 
        AccTNode ptr = accRoot;
        boolean flag = false;

        while(ptr != null){
            if(un.compareToIgnoreCase(ptr.A.U.username) == 0){
                flag = true;
                break;
            }
            if(un.compareToIgnoreCase(ptr.A.U.username) < 0)
                ptr = ptr.left;
            else 
                ptr = ptr.right;
        }

        if(flag)
            return ptr.A;
        else
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
		A.U.username = un;
		A.accept(sc);
		
		//add user to graph
		UserGNode u = new UserGNode(A.U);
		GHeads.add(u);
		
		//add account to avl tree
		accRoot = insert(accRoot, A);
		
		System.out.println("Your account has been successfully created!");
	}
	
	void login() {
		
	}
}
