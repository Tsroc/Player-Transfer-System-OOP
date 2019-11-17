/*
    Class used to get user data and store it, should be singleton class
    For now the data will be encoded within the class but it may be read from a file later.


*/

public class UserData{
    private static UserData userData_instance = null;

    //set of users?

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


}