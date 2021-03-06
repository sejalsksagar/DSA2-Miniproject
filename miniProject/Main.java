package miniProject;

import java.util.*;

public class Main
{

	public static void main(String[] args)
	{
		char ch;
		Scanner sc = new Scanner(System.in);
		Twitter T = new Twitter();
		T.defaultUsers();

		do
		{
			System.out.println("____________________________________");
			System.out.println("************* TWITTER **************");
			System.out.println("0. Exit");
			System.out.println("1. Sign up");
			System.out.println("2. Log in");
			System.out.print("Enter your choice: ");
			ch = sc.next().charAt(0);
			sc.nextLine(); // '\n'
			System.out.println("____________________________________");

			switch (ch)
			{
				case '0':
					System.out.println("********* PROGRAM END ************");
					break;

				case '1':
					T.createAccount(sc);
					break;

				case '2':
					T.login(sc);
					break;

				default:
					System.out.println("Invalid Choice");
					break;
			}
		} while (ch != '0');
	}
}