/*
    User expected to be inherited.
    Has username and password.
    ...
    Should be abstract as a user may not be simply a User
*/
public class User{
    private String username;
    private String password;

    public User(){
        setUsername("");
        setPassword("");
    }

    public User(String username, String password){
        setUsername(username);
        setPassword(password);
    }
    //setters and getters
    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return this.username;
    }

    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return this.password;
    }

    public boolean equals(User user2){
        if(this.username.equals(user2.username)){
            if(this.password.equals(user2.password)){
                return true;
            }
        }
        return false;
        
    }
    public String toString(){
        return "Username: " + username + " Password: " + password;
    }

    public void copy(User user2){
        this.username = user2.getUsername();
        this.password = user2.getPassword();
    }

}