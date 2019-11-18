import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Scanner;

public class Client 
{
	
	private Socket connection;
	private String message;
	private  Scanner console;
	private  Scanner sc;
	private  String ipaddress;
	private  int portaddress;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	public Client()
	{
		/*
			different consoles created for different programs

		*/
		sc = new Scanner(System.in);
		
		System.out.println("Enter the IP Address of the server");
		ipaddress = sc.nextLine();
		
		System.out.println("Enter the TCP Port");
		portaddress  = sc.nextInt();
		
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
					1) Login
					2) Create a new user
				
			*/
			
			loginMenu();
			

			/*
				MORE CODE
				note: loginMenu should return a user, which can be used for
					further code
				
				from user variable, should get class type to determine if
					club or agent.
				This should be done server side, with a variable passed to client,
					to indicate if the user is a club or agent.
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
		console = new Scanner(System.in);
		try{
			out.writeObject(msg);
			out.flush();
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}//sendMessage() - end

	String recieveMessage()
	{
		console = new Scanner(System.in);
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
	}//recieveMessage() - end

	void loginMenu(){
		int loginSelection;
		String strMenu;
		System.out.println(	recieveMessage() );

		strMenu = recieveMessage();
		System.out.println(strMenu);
		//client must select 1 or 2
		loginSelection = console.nextInt();

		while (loginSelection < 1 || loginSelection > 2){
			System.out.println("Invalid input.\n" + strMenu);
			loginSelection = console.nextInt();
		}
		sendMessage( String.valueOf(loginSelection) );

		switch(loginSelection){
			case 1:
				System.out.println("Selected 1.");
				userLogin();
				break;
			case 2:
				System.out.println("Selected 2.");
				userRegister();
				break;
		}

	}//loginMenu() - end

	void userLogin(){
		//welcome message
		boolean validUser = false;

		do{
			//Enter username
			message = recieveMessage();
			System.out.println(message);
			//return username
			message = console.nextLine();
			sendMessage(message);

			//Enter password 
			message = recieveMessage();
			System.out.println(message);
			//return username
			message = console.nextLine();
			sendMessage(message);

			validUser = Boolean.valueOf(recieveMessage());

			//should get msg logged on or not logged on
			if(validUser){
				System.out.println(	recieveMessage() );
			}
			else{
				System.out.println(	recieveMessage() );
			}

		} while (!validUser);
	}//userLogin() - end

	void userRegister(){
		/*
			should accept a username and password and add to UserData's
			list of users.
			Afterwards user should be logged on.
		*/

		//Enter username
		message = recieveMessage();
		System.out.println(message);
		//return username
		message = console.nextLine();
		sendMessage(message);

		//Enter password 
		message = recieveMessage();
		System.out.println(message);
		//return username
		message = console.nextLine();
		sendMessage(message);
	}//userRegister() - end
	
}
