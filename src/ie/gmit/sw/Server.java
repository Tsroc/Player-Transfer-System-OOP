package ie.gmit.sw;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Server {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ServerSocket listener;
		int clientid=0;
		try 
		{
			 listener = new ServerSocket(10000,10);
			 
			 while(true)
			 {
				System.out.println("Main thread listening for incoming new connections");
				Socket newconnection = listener.accept();
				
				System.out.println("New connection received and spanning a thread");
				Connecthandler t = new Connecthandler(newconnection, clientid);
				clientid++;
				t.start();
			 }
			
		} 
		catch (IOException e) 
		{
			System.out.println("Socket not opened");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}


class Connecthandler extends Thread
{

	Socket individualconnection;
	int socketid;
	ObjectOutputStream out;
	ObjectInputStream in;
	String message;

	Set<User> users = new TreeSet<>();
	Map<Integer, Player> players = new HashMap<>();
	Database db = new Database();
	User user = null;
	
	Scanner sc = new Scanner(System.in);
	
	public Connecthandler(Socket s, int i)
	{
		individualconnection = s;
		socketid = i;
	}
	
	public void run()
	{
		
		try 
		{
			out = new ObjectOutputStream(individualconnection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(individualconnection.getInputStream());
			System.out.println("Connection"+ socketid+" from IP address "+individualconnection.getInetAddress());
		
			//Commence the conversation with the client......
			
			db.setDB(users, players);
			db.setDatabase(players);
			//db.setPlayers(players);
			
			/*
				LOGIN:
			*/
			
			int userId = userLogin();	
			
			//set user.
			for(User u: users) {
				if(u.getId() == userId) 
					user = u;
			}
			
			sendMessage(user.toString());
			
			/*
			 	LOGGED IN:
			 		
			*/
			if(user instanceof Club) {
				sendMessage("1");
				//club code
				usertypeClub();
			}
			else if(user instanceof Agent) {
				sendMessage("2");
				//agent code
				usertypeAgent();
			}
			
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				out.close();
				in.close();
				individualconnection.close();
			}
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	  void sendMessage(String msg)
	  {
			try{
				out.writeObject(msg);
				out.flush();
				//System.out.println("client> " + msg);
			}
			catch(IOException ioException){
				ioException.printStackTrace();
			}
	  }


	String receiveMessage()
	{
		try{
			return (String)in.readObject();
			//System.out.println("client>" + msg);
		}
		catch(ClassNotFoundException classNot){
			System.err.println("data received in unknown format");
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
		return "";
	}

	/*
	 * Currently no input is verified.
	 * Can add verification later, but is a low prio.
	 * 
	 */
	int userLogin(){
		int id = 0;
		String name;
		int iUserinput;
		
		sendMessage("Welcome!");
		db.getUsers();
		
		iUserinput = -1;
		sendMessage("\t1) Register \n\t2) Login");
		iUserinput = Integer.parseInt(receiveMessage());		//receive user input, validated client side. 
		
		switch(iUserinput) {
			case 1:
				int iUserinput2;
				String email;
				boolean valid = false;

				sendMessage("Registration.");
				sendMessage("\t1) Club\n\t2) Agent");
				iUserinput2 = Integer.parseInt(receiveMessage());
				System.out.println(iUserinput2);
				
				switch(iUserinput2) {
					case 1:	//register a club.
						float funds;
						
						do {
							sendMessage("\tEnter id: ");
							id = Integer.parseInt(receiveMessage());
							sendMessage("\tEnter name: ");
							name = receiveMessage();
							
							//search users, ensure id/name do not match
							for(User u: users) {
								if(!(u.getName().equalsIgnoreCase(name))&&(!(u.getId() == id))) {
									valid = true;
								}
								else {
									valid = false;
									break;
								}
							}
							
							if (valid) {
								sendMessage("true");
							}
							else
								sendMessage("false");
							
						} while(!valid);

						sendMessage("\tEnter email: ");
						email = receiveMessage();
						sendMessage("\tEnter funds: ");
						funds = Float.parseFloat(receiveMessage());

						users.add(new Club(id , name , email, funds));
						
						break;
					case 2:	//register an agent.
						do {
							sendMessage("\tEnter id: ");
							id = Integer.parseInt(receiveMessage());
							sendMessage("\tEnter name: ");
							name = receiveMessage();
							
							//search users, ensure id/name do not match
							for(User u: users) {
								if(!(u.getName().equalsIgnoreCase(name))&&(!(u.getId() == id))) {
									valid = true;
								}
								else {
									valid = false;
									break;
								}
							}
							
							if (valid) {
								sendMessage("true");
							}
							else
								sendMessage("false");
							
						} while(!valid);

						sendMessage("\tEnter email: ");
						email = receiveMessage();
						users.add(new Agent(id , name , email));
						
						break;
				}
				
				db.saveUsers();
				break;
			case 2:
				boolean validUser = false;
		
				do {
					//menu
					sendMessage("Login Menu");
					sendMessage("\tEnter ID: ");
					id = Integer.parseInt(receiveMessage());
					sendMessage("\tEnter Name: ");
					name = receiveMessage();
					
					System.out.println("Attempted login: id: " + id + " name: " + name);
					
					//userAuthentication
					for(User u: users) {
						if(u.validateUser(id, name)) {
						//if ((u.getId() == id)&&(u.getName().equals(name))) {
							validUser = true;
						}
					}
					
					//login feedback
					if(validUser) {
						System.out.println("Valid User.");
						sendMessage("Valid login.");
						sendMessage("true");
					}
					else {
						System.out.println("Invalid User.");
						sendMessage("Invalid login. Please try again.");
						sendMessage("false");
					}
				} while(!validUser);
				
				break;
		}//switch - end
		
		return id;
	}
	

	void usertypeClub() {
		
		System.out.println("Club.");
		int intIn; 
		String strIn;
		int iUserinput;
		
		//1) Search position, 2) Search my club, 3) suspend/resume sale, 4) purchase player.
		do {
			//printing club details to console for debug
			System.out.println("Printing users.");
			for(User u: users)
				System.out.println(u.toString());
			
			sendMessage("\t1) Search for Players.\n\t2) Search my players.\n\t3) Suspend/Resume Sale.\n\t4) Purchase Player.\n\t0) Exit.");
			iUserinput = Integer.parseInt(receiveMessage());
			
			switch(iUserinput) {
			case 1:	//search for players
				sendMessage("\tEnter position (Goalkeeper, Defender, Midfield, Attacker): ");
				strIn = receiveMessage();
				
				db.setPlayers(players);
				sendMessage(((Club) user).searchPosition(players, strIn));
				
				break;
			case 2:	//search my players
				db.setPlayers(players);
				sendMessage(((Club) user).searchForSaleMy(players));
				
				break;
			case 3:	//suspend/resume sale
				//list players from club.
				db.setPlayers(players);
				sendMessage(((Club) user).listPlayers(players));
				
				sendMessage("Enter ID of player to change: ");
				intIn = Integer.parseInt(receiveMessage());
				
				((Club) user).updatePlayerStatus(players, intIn);
				db.savePlayers();
				
				break;
			case 4:	//purchase player
				db.setPlayers(players);
				sendMessage(((Club) user).listOtherPlayers(players));
				sendMessage("Enter ID of player to purchase: ");
				intIn = Integer.parseInt(receiveMessage());
				
				((Club) user).purchasePlayer(players, users, intIn);
				db.savePlayers();
				db.saveUsers();
				break;
			case 0:
				sendMessage("Exiting Club...");
				break;
			default:
				sendMessage("Invalid input, please try again.");
			}
			
		} while(iUserinput != 0);
	}
	
	void usertypeAgent() {
		System.out.println("Agent.");
		int id, newId = 10000;
		String name;
		int age, clubId;
		float valuation;
		String status;
		String position;
		int iUserinput;
		Set<Integer> idSet;
		
		//1) Add player, 2) Update Value, 3) Update Status.
		do {

			//printing club details to console for debug
			System.out.println("Printing users.");
			for(User u: users)
				System.out.println(u.toString());
			
			sendMessage("\t1) Add Player\n\t2) Update Player Value\n\t3) Update Player Status\n\t0) Exit.");
			iUserinput = Integer.parseInt(receiveMessage());
			
			switch(iUserinput) {
			case 1:	//add player
				sendMessage("Enter Name");
				name = receiveMessage();
				sendMessage("Enter Age");
				age = Integer.parseInt(receiveMessage());
				sendMessage("Enter Club ID");
				clubId = Integer.parseInt(receiveMessage());
				sendMessage("Enter Valuation");
				valuation = Float.parseFloat(receiveMessage());
				sendMessage("Enter Status");
				status = receiveMessage();
				sendMessage("Enter Position");
				position = receiveMessage();
				
				db.setPlayers(players);
				idSet = new TreeSet<>();
				
				for(Map.Entry<Integer, Player> p: players.entrySet()) {
					idSet.add(p.getValue().getId());
				}
				
				for(int i = 10001; i < 20001; i++) {
					if (!(idSet.contains(i))){
						newId = i;
						break;
					}
					
				}
				
				players.put(newId, new Player(newId, name, age, clubId, user.getId(), valuation, status, position));
				db.savePlayers();
				
				break;
			case 2:	//update value
				//list players from agent.
				db.setPlayers(players);
				sendMessage(((Agent) user).listPlayers(players));
				
				sendMessage("Enter player ID: ");
				id = Integer.parseInt(receiveMessage());
				sendMessage("Enter new value");
				valuation = Float.parseFloat(receiveMessage());
				
				((Agent) user).updatePlayerValue(players, id, valuation);
				db.savePlayers();
				
				break;
			case 3:	//update status
				//list players from agent.
				db.setPlayers(players);
				sendMessage(((Agent) user).listPlayers(players));
				
				sendMessage("Enter player ID: ");
				id = Integer.parseInt(receiveMessage());

				((Agent) user).updatePlayerStatus(players, id);
				db.savePlayers();
				
				break;
			case 0:
				sendMessage("Exiting Agent...");
				break;
			default:
				sendMessage("Invalid input, please try again.");
			}
			
		} while(iUserinput != 0);
	}
	
}


