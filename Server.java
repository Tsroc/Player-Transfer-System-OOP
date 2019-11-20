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
					
					login should work with threads.
					Thread should be locked when user is registering.
			*/

			
			User user;	//empty user
			user = loginMenu();
			//System.out.println(user.toString());
			//System.out.println(user.getClass().getName());

			/*
				MORE CODE
				note: loginMenu should return a user, which can be used for
					further code will depend on .getClass().getName() of user.
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
			}
			catch(IOException ioException){
				ioException.printStackTrace();
			}
	  }


	String recieveMessage()
	{
		try{
			return (String)in.readObject();
		}
		catch(ClassNotFoundException classNot){
			System.err.println("data received in unknown format");
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
		return "";
	}

	User loginMenu(){

		User user = null;
		String message = "Welcome.";
		sendMessage(message);
		message = "\t1)Login\n\t2)New account";
		sendMessage(message);

		switch(	Integer.parseInt( recieveMessage()) ){
			case 1:
				user = userLogin();
				break;
			case 2:
				user = userRegister();
				break;
		}

		return user;
	}//loginMenu() - end

	User userLogin(){
		//list of user
		UserData.getInstance();
		String username, password;
		User user;

		//User user;
		boolean validUser = false;
		int userIndex;
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
			user = new User(username, password);
			//user.setUsername(username);
			//user.setPassword(password);
			userIndex = UserData.verifyLogin(user);
			validUser = userIndex >= 0? true: false;

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


		//creates new object of type club or agent to be returned.
		if(UserData.getUser(userIndex).getClass().getName().equals("Club")){
			user = new Club(user.getUsername(), user.getPassword());
			user.copy(UserData.getUser(userIndex));
		}
		else{
			user = new Agent(user.getUsername(), user.getPassword());
			user.copy(UserData.getUser(userIndex));
		}

		return user;
	}//userLogin() - end
	
	User userRegister(){
		/*
			should accept a username and password and add to UserData's
			list of users.
			Afterwards user should be logged on.
			Note, this must loop to ensure same user not added twice.
		*/

		UserData.getInstance();
		String message, username, password;
		User user;


		//username is userd tp validate user, if matches it should not be accepted.
		message = "\tPlease enter username.";
		boolean validUsername;
		do{
			sendMessage(message);
			username = recieveMessage();
			validUsername = UserData.contains(username);
			System.out.println(validUsername);
			sendMessage(Boolean.toString(validUsername));
		}while (validUsername);

		message = "\tPlease enter password.";
		sendMessage(message);
		password = recieveMessage();
		
		String classType;
		//do{
		message = "\tPlease enter user type, 1) Club, 2) Agent.";
		sendMessage(message);
		classType = recieveMessage();

		System.out.println("CLASSTYPE: " + classType);
		if(classType.equals("1")){
		//if(true){
			user = new Club(username, password);
		}else{
			user = new Agent(username, password);
		}

		UserData.add(user);

		return user;
	}//userRegister() - end

}


