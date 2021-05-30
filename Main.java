package MiniProject;
import java.util.*;
public class Main {

	public static void main(String[] args) {
		byte ch;
		Scanner sc = new Scanner(System.in);
		Twitter T = new Twitter();
		//T.defaultUsers();
		User U = new User();
		
		do {
			System.out.println("____");
			System.out.println("* WELCOME TO TWITTER **");
			System.out.println("0. Exit");
			System.out.println("1. Sign up"); 
			System.out.println("2. Log in");
			System.out.println("3. Most Popular User");
			System.out.println("4. Display All Account");
			System.out.print("Enter your choice: ");
			ch = sc.nextByte();
			sc.nextLine(); //'\n'
			System.out.println("____");
			
			switch(ch) {
				case 0:System.out.println("* PROGRAM END **");
				break;
				
				case 1: T.createAccount(sc);
				break;
				
				case 2:T.login(sc);
				break;
				
				case 3:
					U.sortArraylist(T.GHead);
					U.printMPU(T.GHead);
				break;
				
				case 4:
					System.out.println("-------------------------------------");
					System.out.println("USERNAME" +"\t\t"+ "NAME");
					T.display(T.accRoot);
					System.out.println("-------------------------------------");
					
				break;
				
				case 5:
				break;
				
				default : System.out.println("Invalid Choice");
				break;
			}
		}while(ch != 0);
	}

}
/**
 * 0. Log out
 * 1. View my profile
 * 		1. View followers
 * 		2. View following
 * 		3. View Tweets
 * 2. Search a user
 * 		1. View followers
 * 		2. View following
 * 		3. View Tweets
 * 3. Display username-wise sorted list (avl tree inorder)
 * 4. Follow an user
 * 5. Unfollow an user
 * 6. View users connected at k-levels (bfs)
 * 7. Most popular users (heap-sort)
 * 8. Tweet
 * 9. Delete my account
 */