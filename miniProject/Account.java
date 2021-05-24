package miniProject;

import java.time.*;
import java.util.*;

public class Account
{
	User U;
	String password;
	String createdOn; // UTC datetime that the user account was created on Twitter.

	Account()
	{
		U = new User();
		Instant instant = Instant.now();
		createdOn = instant.toString();
	}

	void accept(Scanner sc)
	{
		System.out.print("Enter name: ");
		U.name = sc.nextLine();

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
}