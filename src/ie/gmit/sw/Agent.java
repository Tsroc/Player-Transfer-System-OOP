package ie.gmit.sw;

import java.util.Map;

public class Agent extends User{

	//constructor
	public Agent(int id, String name, String email) {
		super(id, name, email);
	}

	public String listPlayers(Map<Integer, Player> m) {
		String str = "";
		//System.out.println("Listing My Players:");
		
		for(Map.Entry<Integer, Player> p: m.entrySet()) {
			if(p.getValue().getAgentId() == this.getId()) {
				//System.out.println(p.toString());
				str += p.getValue().toString() + "\n";
			}
		}
		//System.out.println("Search: [End]");	
		return str;
	}
	
	//update player value
	public synchronized void updatePlayerValue(Map<Integer, Player> m, int id, float v) {
		System.out.println("Changing Player Status: [" + id + "]");
		for(Map.Entry<Integer, Player> p: m.entrySet()) {
			if(p.getKey() == id && p.getValue().getAgentId() == this.getId()) 
				p.getValue().setValuation(v);
		}
		System.out.println("Search: [End]");	
		
	}
	
	//update player status
	public synchronized void updatePlayerStatus(Map<Integer, Player> m, int id) {
		System.out.println("Changing Player Status: [" + id + "]");
		for(Map.Entry<Integer, Player> p: m.entrySet()) {
			if(p.getValue().getAgentId() == this.getId()) {
				if(p.getKey() == id) { 
					if(p.getValue().getStatus().equalsIgnoreCase("For Sale"))
						p.getValue().setStatus("Sale Suspended");
					else
						p.getValue().setStatus("For Sale");
				}
			}
		}
		System.out.println("Search: [End]");	
		
	}
	
	@Override
	public String toString() {
		return String.format("%d: [%s, %s]",
				getId(), getName(), getEmail());
	}

}
