import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public class Server {

	public static void main(String[] args) {
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
			/*
				LOGIN:
					1) Login
					2) Create a new user
			*/

			
			User user = new User();	//empty user

			loginMenu(user);
			System.out.println(user.toString());
			//System.out.println(user.toString()); //null pointer
			//must get user variable here in order to read the userType.


			/*
				MORE CODE
				note: loginMenu should return a user, which can be used for
					further code
			*/

			
		} 
		catch (IOException e) 
		{
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

	void loginMenu(User user){

		String message = "Welcome.";
		sendMessage(message);
		message = "\t1)Login\n\t2)New account";
		sendMessage(message);

		switch(	Integer.parseInt( recieveMessage()) ){
			case 1:
				userLogin(user);
				break;
			case 2:
				userRegister(user);
				break;
		}
	}//loginMenu() - end

	void userLogin(User user){
		//list of user
		UserData.getInstance();
		String username, password;

		//User user;
		boolean validUser = false;
		//int errorMsg = 0;

		do{
			//user = null;

			message = "\tPlease enter username.";
			sendMessage(message);
			username = recieveMessage();

			message = "\tPlease enter password.";
			sendMessage(message);
			password = recieveMessage();

			//Instantiate a User object with username and password
			//user = new User(username, password);
			user.setUsername(username);
			user.setPassword(password);
			validUser = UserData.verifyLogin(user);

			//need to return to client
			message = Boolean.toString(validUser);
			sendMessage(message);

			//should send msg logged in or not logged in
			if(validUser){
				sendMessage("Login successful.");
			}
			else{
				sendMessage("Login unsuccessful.");
			}
			

		} while(!validUser);
	}//userLogin() - end
	
	void userRegister(User user){
		/*
			should accept a username and password and add to UserData's
			list of users.
			Afterwards user should be logged on.
		*/

		UserData.getInstance();
		String message, username, password;
		//User user;

		message = "\tPlease enter username.";
		sendMessage(message);
		username = recieveMessage();

		message = "\tPlease enter password.";
		sendMessage(message);
		password = recieveMessage();
		
		//user = new User(username, password);
		user.setUsername(username);
		user.setPassword(password);
		//System.out.println(	user.toString() );

		UserData.add(user);
		UserData.printUsers();



	}//userRegister() - end

}


