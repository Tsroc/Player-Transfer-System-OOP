package ie.gmit.sw;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Runner {

	public static void main(String[] args) {
    	
		Set<User> users = new TreeSet<>();
    	Map<Integer, Player> players = new HashMap<>();
    	
		Database db = new Database();
		db.setDB(users, players);
		db.setDatabase();
		//must be linked up again, not sure why
		db.setPlayers(players);
		
		//check if user wishes to register or login.
		Scanner sc = new Scanner(System.in);
		
		/*
		 *	The following deals with user registration and login. 
		 *	***COPIED***
		 *
		 */
		int iUserinput;
		Login userlogin = new Login();
		
		System.out.println("Welcome!");
		do {
			iUserinput = -1;
			System.out.println("\t1) Register \n\t2) Login.");
			iUserinput = Integer.parseInt(sc.nextLine());
		} while(iUserinput < 1  || iUserinput > 2);
		
		switch(iUserinput) {
			case 1:
				userlogin.register(users);
				db.saveUsers();
				break;
			case 2:
				userlogin.login(users);
				break;
		}
		
		
		/*
		 * 	The following deals with logged in Clubs and Agents
		 *		at this point the user is logged in and should be assigned to user variable. 
		 *		This could be done above but it will not be very cleanly implemented.
		 */
		
		//setting user
		User user = null;
		for(User u: users) {
			if(u.getId() == userlogin.getId())
				user = u;
		}

			/*
				 Should loop until 0 is selected.
			*/
		if(user instanceof Club) {
			System.out.println("Club.");
			int intIn; 
			String strIn;
			
			//1) Search position, 2) Search my club, 3) suspend/resume sale, 4) purchase player.
			do {
				iUserinput = -1;
				System.out.println("\t1) Search for Players\n\t2) Search my players\n\t3) Suspend/Resume Sale\n\t4) Purchase Player");
				iUserinput = Integer.parseInt(sc.nextLine());
				
				switch(iUserinput) {
				case 1:	//search for players
					System.out.print("\tEnter position (Goalkeeper, Defender, Midfield, Attacker): ");
					strIn = sc.nextLine();
					
					((Club) user).searchPosition(players, strIn);
					
					break;
				case 2:	//search my players
					((Club) user).searchForSaleMy(players);
					
					break;
				case 3:	//suspend/resume sale
					//list players from club.
					((Club) user).listPlayers(players);
					
					System.out.print("Enter ID of player to change: ");
					intIn = Integer.parseInt(sc.nextLine());
					
					((Club) user).updatePlayerStatus(players, intIn);
					db.savePlayers();
					
					break;
				case 4:	//purchase player
					
					System.out.print("Enter ID of player to purchase: ");
					intIn = Integer.parseInt(sc.nextLine());
					
					((Club) user).purchasePlayer(players, users, intIn);
					db.savePlayers();
					break;
				default:
					System.out.println("Invalid input, please try again.");
				}
				
			} while(iUserinput != 0);
			System.out.println("Exiting Club...");
			
		}
		else if(user instanceof Agent) {
			System.out.println("Agent.");
			int id;
			String name;
			int age, clubId, agentId;
			float valuation;
			String status;
			String position;
			
			//1) Add player, 2) Update Value, 3) Update Status.
			do {
				iUserinput = -1;
				System.out.println("\t1) Add Player\n\t2) Update Player Value\n\t3) Update Player Status");
				iUserinput = Integer.parseInt(sc.nextLine());
				
				switch(iUserinput) {
				case 1:	//add player
					System.out.print("Enter player ID: ");
					id = Integer.parseInt(sc.nextLine());
					System.out.print("Enter Name");
					name = sc.nextLine();
					System.out.print("Enter Age");
					age = Integer.parseInt(sc.nextLine());
					System.out.print("Enter Club ID");
					clubId = Integer.parseInt(sc.nextLine());
					System.out.print("Enter Valuation");
					valuation = Float.parseFloat(sc.nextLine());
					System.out.print("Enter Status");
					status = sc.nextLine();
					System.out.print("Enter Position");
					position = sc.nextLine();
					
					players.put(id, new Player(id, name, age, clubId, user.getId(), valuation, status, position));
					
					
					break;
				case 2:	//update value
					//list players from agent.
					((Agent) user).listPlayers(players);
					
					System.out.println("Enter player ID: ");
					id = sc.nextInt();
					System.out.println("Enter new value");
					valuation = sc.nextFloat();
					
					((Agent) user).updatePlayerValue(players, id, valuation);
					db.savePlayers();
					
					break;
				case 3:	//update status
					//list players from agent.
					((Agent) user).listPlayers(players);
					
					System.out.println("Enter player ID: ");
					id = sc.nextInt();

					((Agent) user).updatePlayerStatus(players, id);
					db.savePlayers();
					
					break;
				default:
					System.out.println("Invalid input, please try again.");
				}
				
			} while(iUserinput != 0);
			System.out.println("Exiting Agent...");
			
		}
		else {
			System.out.println("Error occured.");
		}

	}//main()
	
}
