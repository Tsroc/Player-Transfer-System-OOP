package ie.gmit.sw;
/*
    Runs server side when a client connects
    How to use this class?

    login will be instances, with each having a User, user will be filled with login attempts
*/
import java.util.Scanner;
import java.util.Set;

public class Login{
    //attempt to log in using this User.
	private int id;
	private String name;

	private static final Scanner SC = new Scanner(System.in);
	

    public void displayMenu(){
		
		System.out.println("Login Menu");
		System.out.print("\tEnter ID: ");
		id = Integer.parseInt(SC.nextLine());
		System.out.print("\tEnter Name: ");
		name = SC.nextLine();
		
		System.out.println("id: " + id + " name: " + name);
        //sc.close();
    }

    public int getId() {
		return id;
	}

	public boolean userAuthentication(Set<User> users){
    	for(User u: users) {
			if(u.validateUser(id, name)) {
				return true;
			}
		}
		return false;
    }
    
 	public void register(Set<User> u) {
		int iUserinput;
		String name, email;

		System.out.println("Registration.");
		do {
			iUserinput = -1;
			System.out.println("\t1) Club\n\t2) Agent.");
			iUserinput = SC.nextInt();
		} while(iUserinput < 1  || iUserinput > 2);	

		System.out.println("TEST.");
		
		switch(iUserinput) {
			case 1:	//register a club.
				float funds;
				
				System.out.println("\tEnter... id, name, email, funds.");
				id = SC.nextInt();
				name = SC.next();
				email = SC.next();
				funds = SC.nextFloat();
				
				u.add(new Club(id , name , email, funds));
				
				
				break;
			case 2:	//register an agent.
				System.out.println("\tEnter... id, name, email.");
				
				id = SC.nextInt();
				name = SC.next();
				email = SC.next();
				
				u.add(new Agent(id , name , email));
				
				break;
		}
		
	}
	
	public void login(Set<User> u) {
		
		boolean validUser = false;
		
		do {
			displayMenu();
			validUser = userAuthentication(u);
			
			//login feedback
			if(validUser)
				System.out.println("logged in.");
			else
				System.out.println("Invalid login details. Please try again.");
			
		} while(!validUser);
	}


}