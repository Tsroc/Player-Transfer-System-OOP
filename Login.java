/*
    Runs server side when a client connects
    How to use this class?

    login will be instances, with each having a User, user will be filled with login attempts
*/
import java.util.Scanner;
public class Login{
    //attempt to log in using this User.
    public User user;

    public Login(String username, String password){
        displayMenu(this.user);

    }

    private void displayMenu(User user){
        String username;
        String password;

        Scanner sc = new Scanner(System.in);

        System.out.println("Login Menu");
        System.out.println("Enter username.");
        username = sc.nextLine();
        System.out.println("Enter password.");
        password = sc.nextLine();

        user = new User(username, password);
    }

    public boolean userAuthentication(){
        boolean validUser;

        validUser = true;

        //loop over list of users and compareTo()
        //userList from where?

        return validUser;
    }

}