package miniProject;


import java.time.Instant;
import java.time.ZoneId;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Account extends User{
	
	private String password;
	String createdOn; 	//UTC datetime that the user account was created on Twitter.
	
	Account(){
		Instant instant = Instant.now();
		createdOn = instant.toString();
		LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		super.joined = "Joined "+ldt.getMonth()+" "+ldt.getYear();
	}
	
	void accept(Scanner sc) {
		
		System.out.print("Enter name: ");
		name = sc.nextLine();

		System.out.print("Enter password: ");
		setPassword(sc.nextLine());
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	void activity(Scanner sc, Account A) {
		
		byte ch;
		do {
			System.out.println("____________________________________");
			System.out.println("******** Welcome @"+username+" *********");
			System.out.println("0. Log out");
			System.out.println("1. Home");
			System.out.println("2. Explore");
			System.out.println("3. Notifications");
			System.out.println("4. Profile");
			System.out.println("5. Tweet");
			System.out.println("6. More");
			System.out.print("Enter your choice: ");
			ch = sc.nextByte();
			System.out.println("____________________________________");
			
			switch(ch) {
			case 0:
				break;
				
			case 1:
				break;
				
			case 2: Twitter.explore(sc,A);
				break;
				
			case 3:
				break;
				
			case 4: A.profile(sc);
				break;
			}
		}while(ch!=0);
	}
}