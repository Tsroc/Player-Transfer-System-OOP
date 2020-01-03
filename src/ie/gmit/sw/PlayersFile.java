package ie.gmit.sw;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/*
 	Clubs class must deal with file work, 
 */
public class PlayersFile{
	List<Player> players = null;
	
	public void setPlayers(List<Player> p) {
		for(Player player: this.players)
			p.add(player);
	}
	
	public void fileRead() {
		Gson gson = new Gson();

        try (Reader reader = new FileReader("clubs.json")) {
            this.players = gson.fromJson(reader, new TypeToken<List<Player>>(){}.getType());

        } catch (IOException e) {
            e.printStackTrace();
        }	
	}
	
	public void fileWrite() {
		 try (FileWriter file = new FileWriter("players.json")) {
        	 
			GsonBuilder builder = new GsonBuilder();
			builder.excludeFieldsWithoutExposeAnnotation();
			Gson gson = builder.create();
			file.write(gson.toJson(this.players));
            file.flush();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}