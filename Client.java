/*
public class Client{
    

    
}


*/



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
		portaddress  = console.nextInt();
		
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
			console.nextLine();
			userLogin();
			System.out.println("Logged on.");
			

			/*
				MORE CODE
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

	String recieveMessage()
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

	void userLogin(){
		//welcome message
		boolean validLogin = false;

		do{
			//console.nextLine();
			message = recieveMessage();
			System.out.println(message);

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

			//System.out.println(Boolean.valueOf(recieveMessage()));
			validLogin = Boolean.valueOf(recieveMessage());

		} while (!validLogin);
	}
	
}
