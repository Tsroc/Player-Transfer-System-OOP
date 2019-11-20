/*
    Class used to get user data and store it, should be singleton class
    For now the data will be encoded within the class but it may be read from a file later.

    Note: Is this being a singleton class going to cause problems with threads?
*/
import java.util.LinkedList;

public class UserData{
    private static UserData userData_instance = null;

    //set of users?
    private static LinkedList<User> userList = new LinkedList<>();
    private static void getUserData(){
        userList.add(new Club("user1", "password1"));
        userList.add(new Club("user2", "password2"));
        userList.add(new Agent("user3", "password3"));
        userList.add(new Club("user4", "password4"));
        userList.add(new Agent("user5", "password5"));

    }
    /*
        */
    

    private UserData(){
        //fill userData here
        //How is userData gathered?
        //Nothing needs done here, UserData is a storage structure and will be accessed through methods,
        //Here it will simply be filled if it is taken from external file.
        getUserData();
    }

    public static UserData getInstance(){
        if(userData_instance == null)
            userData_instance = new UserData();
        
        return userData_instance;
    }

    public static User getUser(int i){
        return userList.get(i);
    }

    public static LinkedList<User> getUsers(){
        return userList;
    }

    public static void printUsers(){
        for(int i = 0; i < userList.size(); i++){
            System.out.println(userList.get(i).toString());
        }
    }


    public static int length(){
        return userList.size();
    }

    public static int verifyLogin(User user){
        for(int i = 0; i < UserData.length(); i++){
            if(user.equals(UserData.getUser(i))){
                System.out.println("Valid user login.");
                return i;
            }
        }
        System.out.println("Invalid user login.");
        return -1;
    }

    public static boolean contains(String username){
        for(int i = 0; i < UserData.length(); i++){
            if(username.equals( UserData.getUser(i).getUsername() )){
                return true;
            }
        }
        return false;
    }
    

    public static void add(User user){
        userList.add(user);
    }

}