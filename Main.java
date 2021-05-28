package MiniProject;
import java.util.*;
public class Main {

	public static void main(String[] args) {
		byte ch;
		Scanner sc = new Scanner(System.in);
		Twitter T = new Twitter();
		
		
		do {
			System.out.println("____________________________________");
			System.out.println("****** WELCOME TO TWITTER *******");
			System.out.println("0. Exit");
			System.out.println("1. Sign up"); 
			System.out.println("2. Log in");
			System.out.println("3. Delete Account");
			System.out.print("Enter your choice: ");
			ch = sc.nextByte();
			sc.nextLine(); //'\n'
			System.out.println("____________________________________");
			
			switch(ch) {
				case 0:System.out.println("********* PROGRAM END ************");
				break;
				
				case 1: T.createAccount(sc);
				break;
				
				case 2:T.login(sc);
				break;
				
				case 3:
					System.out.println("Enter your username to delete account : ");
			    	String und = sc.next();   //und-- user name delete
			    	T.accRoot = T.deleteAccount(T.accRoot, und);
			    	
				break;
				
				default : System.out.println("Invalid Choice");
				break;
			}
		}while(ch != 0);
	}

}
