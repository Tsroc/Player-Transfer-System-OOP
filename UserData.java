/*
    Class used to get user data and store it, should be singleton class
    For now the data will be encoded within the class but it may be read from a file later.

    Note: Is this being a singleton class going to cause problems with threads?
*/

public class UserData{
    private static UserData userData_instance = null;

    //set of users?
    private static User[] users = {
        new User("user1", "password1"),
        new User("user2", "password2"),
        new User("user3", "password3"),
        new User("user4", "password4"),
        new User("user5", "password5")
    };
    

    private UserData(){
        //fill userData here
        //How is userData gathered?
        //Nothing needs done here, UserData is a storage structure and will be accessed through methods,
        //Here it will simply be filled if it is taken from external file.
    }

    public static UserData getInstance(){
        if(userData_instance == null)
            userData_instance = new UserData();
        
        return userData_instance;
    }

    public static User getUser(int i){
        return users[i];
    }

    public static User[] getUsers(){
        return users;
    }

    public static int length(){
        return users.length;
    }

    public static boolean verifyLogin(User user){
        for(int i = 0; i < UserData.length(); i++){
            if(user.equals(UserData.getUser(i))){
                System.out.println("\nValid user login.\n");
                return true;
            }
        }
        System.out.println("\nInvalid user login.\n");
        return false;
    }

}