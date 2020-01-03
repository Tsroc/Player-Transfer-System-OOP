package ie.gmit.sw;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Database{
	String clubfile = "clubs.json";
	String agentfile = "agents.json";
	String playerfile = "players.json";
	
	Set<User> users = null;
	ConcurrentMap<Integer, Player> players = null;

	Gson gson = new Gson();
	

	
	public void setDB(Set<User> u, Map<Integer, Player> m) {
		this.users = u;
		this.players = new ConcurrentHashMap<>(m);

	}
	
	
	public void getPlayers() {
		for(Map.Entry<Integer, Player> p: players.entrySet())
			System.out.println(p.getValue().toString());
	}
	
	public void setPlayers(Map<Integer, Player> m) {
		players.putAll(m);
	}
	
	public void getUsers() {
		for(User u: users)
			System.out.println(u.toString());
	}
	
	public void setUsers(Set<User> u) {
		this.users = u;
		readUsers();
	}
	
	
	public void setDatabase(Map<Integer, Player> m) {
		readPlayers();
		readUsers();

		for(Map.Entry<Integer, Player> p: players.entrySet())
			m.put(p.getKey(), p.getValue());
	}
	
	public void readPlayers() {
        try (Reader reader = new FileReader("players.json")) {

        	players  = gson.fromJson(reader, new TypeToken<ConcurrentHashMap<Integer, Player>>(){}.getType());

        } catch (IOException e) {
            e.printStackTrace();
        }	
	}
	
	public void savePlayers() {
		try (FileWriter file = new FileWriter(playerfile)) {
			file.write(gson.toJson(players));
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void readUsers() {
		ConcurrentSkipListSet<User> clubs = null, agents = null;

        try (Reader reader = new FileReader(clubfile)) {
            clubs = gson.fromJson(reader, new TypeToken<ConcurrentSkipListSet<Club>>(){}.getType());
            
			for(User u: clubs)
				users.add(u);
			
        } catch (IOException e) {
            e.printStackTrace();
        }	
        try (Reader reader = new FileReader(agentfile)){
            agents = gson.fromJson(reader, new TypeToken<ConcurrentSkipListSet<Agent>>(){}.getType());
            
			for(User u: agents)
				users.add(u);
            
        } catch (IOException e) {
            e.printStackTrace();
        }	
        
	}
	
	public void saveUsers() {
		Set<User> clubs = new TreeSet<>();
		Set<User> agents = new TreeSet<>();
		
		for(User u: users) {
			if(u instanceof Club)
				clubs.add(u);
			else if(u instanceof Agent)
				agents.add(u);
		}
		
		try (FileWriter file = new FileWriter(clubfile)) {
			 
			file.write(gson.toJson(clubs));
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try (FileWriter file = new FileWriter(agentfile)) {
			 
			file.write(gson.toJson(agents));
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}

