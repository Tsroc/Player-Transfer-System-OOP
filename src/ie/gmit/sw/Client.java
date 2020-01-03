package ie.gmit.sw;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Scanner;

public class Client 
{
	
	private Socket connection;
	private  Scanner console;
	private  String ipaddress;
	private  int portaddress;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	public Client()
	{
		console = new Scanner(System.in);
		
		System.out.println("Enter the IP Address of the server");
		ipaddress = console.nextLine();
		
		System.out.println("Enter the TCP Port");
		portaddress  = Integer.parseInt(console.nextLine());
		
	}
	
	public static void main(String[] args) 
	{
			Client temp = new Client();
			temp.clientapp();
	}

	public void clientapp()
	{		
		try 
		{
			connection = new Socket(ipaddress,portaddress);
		
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			System.out.println("Client Side ready to communicate");
		
			//communication
			/*
				LOGIN
			*/
			userLogin();
			
			System.out.println(receiveMessage());
			
			/*
			 	LOGGED IN:
			 		
			*/
			//get login type
			int usertype = Integer.parseInt(receiveMessage());	//receive response: if 1, 2 -> true
			
			switch(usertype) {
				case 1:
					System.out.println("User Type: Club.");
					usertypeClub();
					break;
				case 2:
					System.out.println("User Type: Agent.");
					usertypeAgent();
					break;
				default:
			
			}//switch - end
			

			/*
				MORE CODE:
					close connections.
			*/
			
			out.close();
			in.close();
			connection.close();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	void sendMessage(String msg)
	{
		try{
			out.writeObject(msg);
			out.flush();
			//System.out.println("client>" + msg);
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

	String iGetinput(int max) {
		String strIn;
		int iIn;
		boolean valid = false;
		
		do {
			strIn = console.nextLine();
			
			try {
				iIn = Integer.parseInt(strIn);
				
				if(iIn > 0 && iIn <= max)
					valid = true;
			}
			catch(NumberFormatException e) {
				
			}
			if(!valid)
				System.out.print("\tInvalid input. Please try again: ");
		} while (!valid);
		
		return strIn;
	}

	String iGetinput(int min, int max) {
		String strIn;
		int iIn;
		boolean valid = false;
		
		do {
			strIn = console.nextLine();
			
			try {
				iIn = Integer.parseInt(strIn);
				
				if(iIn >= min && iIn <= max)
					valid = true;
			}
			catch(NumberFormatException e) {
				
			}
			if(!valid)
				System.out.print("\tInvalid input. Please try again: ");
		} while (!valid);
		
		return strIn;
	}
	
	String fGetinput(float max) {
		String strIn;
		float iIn;
		boolean valid = false;
		
		do {
			strIn = console.nextLine();
			
			try {
				iIn = Float.parseFloat(strIn);
				
				if(iIn > 0 && iIn <= max)
					valid = true;
			}
			catch(NumberFormatException e) {
				
			}
			if(!valid)
				System.out.print("\tInvalid input. Please try again: ");
		} while (!valid);
		
		return strIn;
	}
	
	String strGetinputStatus() {
		String strIn = "";
		int iIn = 0;
		boolean valid = false;

		System.out.println("\t1) For Sale\t2) Sale Suspended\t1) Sold");
		
		do {
			strIn = console.nextLine();
			
			try {
				iIn = Integer.parseInt(strIn);
				
				if(iIn > 0 && iIn <= 3) {
					switch(iIn) {
					case 1:
						strIn = "For Sale";
						break;
					case 2:
						strIn = "Sale Suspended";
						break;
					case 3:
						strIn = "Sold";
						break;
					}
					
					valid = true;
				}
			}
			catch(NumberFormatException e) {
				
			}
			if(!valid)
				System.out.print("\tInvalid input. Please try again: ");
		} while (!valid);	
		
		return strIn;
	}

	String strGetinputPosition() {
		String strIn = "";
		int iIn = 0;
		boolean valid = false;
		
		System.out.println("\t1) Goalkeeper\t2) Defender\t1) Midfield\t1) Attacker");
		
		do {
			strIn = console.nextLine();
			
			try {
				iIn = Integer.parseInt(strIn);
				
				if(iIn > 0 && iIn <= 4) {
					switch(iIn) {
					case 1:
						strIn = "Goalkeeper";
						break;
					case 2:
						strIn = "Defender";
						break;
					case 3:
						strIn = "Midfield";
						break;
					case 4:
						strIn = "Attacker";
						break;
					}
					
					valid = true;
				}
			}
			catch(NumberFormatException e) {
				
			}
			if(!valid)
				System.out.print("\tInvalid input. Please try again: ");
		} while (!valid);	
		
		return strIn;
	}
	
	

	void userLogin(){
		//welcome message
		int id;
		float funds;
		boolean valid = false;
		int iUserinput, iUserinput2;
		
		System.out.println(receiveMessage());				//receive "Welcome!"
		
		System.out.print(receiveMessage());			//receive - "1) Register 2) Login."
		iUserinput = Integer.parseInt(iGetinput(2));	//validate console.in - user selection
		sendMessage(Integer.toString(iUserinput));		//send

		switch(iUserinput) {
			case 1:
				System.out.println(receiveMessage());			//receive - Registration menu
				System.out.println(receiveMessage());			//receive - "1) Club 2) agent
				iUserinput2 = Integer.parseInt(iGetinput(2));	//validate console.in - user selection
				sendMessage(Integer.toString(iUserinput2));		//send 
					
				switch(iUserinput2) {
					case 1:
						do {
							System.out.print(receiveMessage());		//receive - "enter id" 
							id = Integer.parseInt(iGetinput(1000, 2000));	//validate console.in - max_id = 2000
							sendMessage(Integer.toString(id));		
							System.out.print(receiveMessage());		//receive - "enter name"
							sendMessage(console.nextLine());

							valid = Boolean.parseBoolean(receiveMessage());						//receive - true -> valid user
							
						} while(!valid);
						
						System.out.print(receiveMessage());		//receive - "enter email"
						sendMessage(console.nextLine());
						System.out.print(receiveMessage());		//receive - "enter funds"
						funds = Float.parseFloat(fGetinput(10000));	//validate console.in - max funds = 10000
						sendMessage(Float.toString(funds));				//send - id
						
						break;
					case 2:
						do {
							System.out.print(receiveMessage());		//receive - "enter id" 
							id = Integer.parseInt(iGetinput(1000, 2000));	//validate console.in - max_id = 2000
							sendMessage(Integer.toString(id));		
							System.out.print(receiveMessage());		//receive - "enter name"
							sendMessage(console.nextLine());

							valid = Boolean.parseBoolean(receiveMessage());						//receive - true -> valid user
							
						} while(!valid);

						System.out.print(receiveMessage());		//receive - "enter email"
						sendMessage(console.nextLine());
						
						break;
				}
				System.out.println("Reg success.");
				
				break;
			case 2:
				do {
					System.out.println(receiveMessage());								//login menu
					System.out.print(receiveMessage());									//receive - "enter ID"
					sendMessage(Integer.toString( Integer.parseInt(iGetinput(2000))));	//send
					
					System.out.print(receiveMessage());									//receive - "enter Name"
					sendMessage(console.nextLine());									//send
					
					System.out.println(receiveMessage());								//receive - str: valid or invalid
					valid = Boolean.parseBoolean(receiveMessage());						//receive - true -> valid user
				} while (!valid);
				
				break;
		}
	}//userLogin()

	void usertypeClub() {
		int id;
		int iUserinput;
		
		do {
			System.out.println(receiveMessage()); 					//club menu
			iUserinput = Integer.parseInt(iGetinput(0, 4));			//validate console.in - user selection
			sendMessage(Integer.toString(iUserinput));				//send
			
			switch(iUserinput) {
				case 1:	//search for players
					System.out.print(receiveMessage());
					sendMessage(console.nextLine());
					
					System.out.println(receiveMessage());
					break;
				case 2:	//search my players
					System.out.println(receiveMessage());
					
					break;
				case 3:	//suspend/resume sale
					System.out.println(receiveMessage());			//list of players
					System.out.print(receiveMessage());				//receive - suspend/resume msg
					id = Integer.parseInt(iGetinput(10000, 20000));	//validate console.in - player id
					sendMessage(Integer.toString(id));				//send 
					
					break;
				case 4:	//purchase player
					System.out.println(receiveMessage());			//list of players
					System.out.print(receiveMessage());				//receive - purchase player msg
					id = Integer.parseInt(iGetinput(10000, 20000));	//validate console.in - player id
					sendMessage(Integer.toString(id));				//send
					
					
					break;
				default:
					System.out.println(receiveMessage());
			
			}//switch - end
			
		}while(iUserinput != 0);
		System.out.println(receiveMessage());
	}
	
	void usertypeAgent() {
		float fUserinput;
		int iUserinput, iUserinput2;

		do {
			System.out.println(receiveMessage()); //agent menu
			iUserinput = Integer.parseInt(iGetinput(0, 3));	//validate console.in - user selection
			sendMessage(Integer.toString(iUserinput));		//send
			
			switch(iUserinput) {
				case 1:	//add player
					System.out.print(receiveMessage());							//receive - "enter name"
					sendMessage(console.nextLine());							//send name
					System.out.print(receiveMessage());							//receive - "enter age"
					iUserinput2 = Integer.parseInt(iGetinput(50));				//validate console.in - player age 
					sendMessage(Integer.toString(iUserinput2));					//send
					
					System.out.print(receiveMessage());							//receive - "enter club id"
					iUserinput2 = Integer.parseInt(iGetinput(1000, 2000));		//validate console.in - club id
					sendMessage(Integer.toString(iUserinput2));					//send

					System.out.print(receiveMessage());							//receive - "enter value"
					fUserinput = Float.parseFloat(fGetinput(10000));			//validate console.in - value 
					sendMessage(Float.toString(fUserinput));					//send
					System.out.print(receiveMessage());							//receive - "enter status"
					sendMessage(strGetinputStatus());							//send 
					System.out.print(receiveMessage());							//receive - "enter position"
					sendMessage(strGetinputPosition());							//send
					
					break;
				case 2:	//search my players
					System.out.println(receiveMessage());						//list ofY players
					System.out.print(receiveMessage());							//receive - "enter id"
					iUserinput2 = Integer.parseInt(iGetinput(10000, 20000));	//validate console.in - player id 
					sendMessage(Integer.toString(iUserinput2));					//send
					System.out.print(receiveMessage());							//receive - "enter value"
					fUserinput = Float.parseFloat(fGetinput(10000));			//validate console.in - value 
					sendMessage(Float.toString(fUserinput));					//send
					
					break;
				case 3:	//suspend/resume sale
					System.out.println(receiveMessage());						//list ofY players
					System.out.print(receiveMessage());							//receive - "enter id"
					iUserinput2 = Integer.parseInt(iGetinput(10000, 20000));	//validate console.in - player id 
					sendMessage(Integer.toString(iUserinput2));					//send
					
					break;
				default:
					System.out.println(receiveMessage());
			
			}//switch - end
			
		}while(iUserinput != 0);
	}
}
