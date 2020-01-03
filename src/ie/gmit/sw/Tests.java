package ie.gmit.sw;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Tests {
	public static void main(String[] args) {
		Set<User> users = new TreeSet<>();
    	Map<Integer, Player> players = new HashMap<>();
    	
		Database db = new Database();
		db.setDB(users, players);
		db.setDatabase();
		//must be linked up again, not sure why
		db.setPlayers(players);
	
	
		User user = null;
		//club
		for(User u: users) {
			if(u.getId() == 1003) {
				if(u instanceof Club)
					user = (Club) u;
				else if(u instanceof Agent)
					user = (Agent) u;
			}
		}
		//agent
		User user2 = null;
		for(User u: users) {
			if(u.getId() == 1004) {
				if(u instanceof Club)
					user2 = (Club) u;
				else if(u instanceof Agent)
					user2 = (Agent) u;
			}
		}

		System.out.println(users.size());
		System.out.println(players.size());
		((Club) user).searchPosition(players, "Attacker");
		((Club) user).searchForSaleMy(players);
		//((Club) user).changePlayerStatus(players, 10002, "Sold");
		((Club) user).purchasePlayer(players, users, 10015);
		
		
		
		//((Agent) user2).updateStatus(players, 10002, "For Sale");
		//((Agent) user2).updateValue(players, 10002, 40);
		
		
		
		db.getPlayers();
		db.getUsers();
		
		//db.savePlayers();	
	}
}
