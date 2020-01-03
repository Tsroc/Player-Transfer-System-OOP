package ie.gmit.sw;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

class CreatePlayers{
    public static void main(String[] args){

    	//Should be taken from a file.
    	Set<User> users = new TreeSet<>();
    	Map<Integer, Player> players = new HashMap<>();
    	
    	Database db = new Database();
    	db.setDB(users, players);
    	
    	users.add(new Club(1001, "Club_1", "Club1@gmit.ie", 1000.00));
    	users.add(new Club(1002, "Club_2", "Club2@gmit.ie", 1000.00));
        users.add(new Club(1003, "Club_3", "Club3@gmit.ie", 1000.00));
        users.add(new Agent(1004, "Agent_1", "Agent1@gmit.ie"));
        users.add(new Agent(1005, "Agent_2", "Agent2@gmit.ie"));
        users.add(new Agent(1006, "Agent_3", "Agent3@gmit.ie"));
        
        for(User u: users)
        	System.out.println(u.toString());
        
        int id = 10001;
		String name;
		int age = 20;
		int clubId = 1000;
		int agentId = 1003;
		float valuation = 10.0f;
		String status = "For sale";
		String position = "Goalkeeper";
        
        for(int i = 0; i < 15; i++) {
        	name = "player_" + id;
        	
        	if(i % 5 == 0) {
        		clubId++;
        		agentId++;
        	}
        	
        	switch (i) {
        	case 2: position = "Defender";
				break;
        	case 7: position = "Midfield";
        		break;
        	case 11: position = "Attacker";
        		break;
        		
        	}
        	
        	players.put(id, new Player(id, name, age, clubId, agentId, valuation, status, position));
        	System.out.println(id + " added.");
        	id++;
        }
        
        db.saveUsers();
        db.savePlayers();
    }
}