package ie.gmit.sw;

import java.util.Map;
import java.util.Set;

/*
    What does an club user do?

*/

public class Club extends User{

    private double funds;
    
    public Club(int id, String name, String email, double funds) {
		super(id, name, email);
		this.setFunds(funds);
	}

    public double getFunds() {
		return funds;
	}

	public void setFunds(double funds) {
		this.funds = funds;
	}


	//database search functions will be here otherwise names will not be good
	//search all players in given position
	public String searchPosition(Map<Integer, Player> m, String pos) {
		String str = "";
		//System.out.println("Searching Position: [" + pos + "]");

		for(Map.Entry<Integer, Player> p: m.entrySet()) {
			if(p.getValue().getPosition().equalsIgnoreCase(pos)) {
				//System.out.println(p.toString());
				str += p.getValue().toString() + "\n";
			}
		}
		//System.out.println("Search: [End]");
		return str;
	}
	
	public String listPlayers(Map<Integer, Player> m) {
		String str = "";
		//System.out.println("Listing My Players:");
		
		for(Map.Entry<Integer, Player> p: m.entrySet()) {
			if(p.getValue().getClubId() == this.getId()) {
				//System.out.println(p.toString());
				str += p.getValue().toString() + "\n";
			}
		}
		//System.out.println("Search: [End]");	
		return str;
	}

	public String listOtherPlayers(Map<Integer, Player> m) {
		String str = "";
		//System.out.println("Listing My Players:");
		
		for(Map.Entry<Integer, Player> p: m.entrySet()) {
			if(p.getValue().getClubId() != this.getId()) {
				//System.out.println(p.toString());
				str += p.getValue().toString() + "\n";
			}
		}
		//System.out.println("Search: [End]");	
		return str;
	}
	
	//search all players for sale in their club
	public String searchForSaleMy(Map<Integer, Player> m) {
		String str = "";
		//System.out.println("Searching My Players For Sale:");
		
		for(Map.Entry<Integer, Player> p: m.entrySet()) {
			if(p.getValue().getClubId() == this.getId()) {
				if(p.getValue().getStatus().equalsIgnoreCase("For Sale")) {
					//System.out.println(p.toString());
					str += p.getValue().toString() + "\n";
				}
				
			}
		}
		//System.out.println("Search: [End]");	
		return str;
	}
	
	//suspend/resume the same of a player in their club
	public synchronized void updatePlayerStatus(Map<Integer, Player> m, int id) {
		System.out.println("Changing Player Status: [" + id + "]");
		for(Map.Entry<Integer, Player> p: m.entrySet()) {
			if(p.getValue().getClubId() == this.getId()) {
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
	
	//purchase a player 
	public synchronized void purchasePlayer(Map<Integer, Player> m, Set<User> u, int id) {
		boolean found = false;
		float value = 0;
		int exClub = 0;
		
		System.out.println("Purchasing Player:");
		for(Map.Entry<Integer, Player> p: m.entrySet()) {
			if(p.getKey() == id) {
				if(getFunds() >= p.getValue().getValuation()) {
					exClub = p.getValue().getClubId();
					p.getValue().setClubId(this.getId());
					found = true;
					value = p.getValue().getValuation();
					p.getValue().setStatus("Sold");
				}				
			}
		}
		//update funds
		if(found) {
			setFunds(getFunds() - value);
			
			for(User user: u) {
				if(user.getId() == exClub)
					((Club) user).setFunds( ((Club) user).getFunds() + value);
			}
		}
		System.out.println("Search: [End]");	
	}
	
	
	@Override
	public String toString() {
		return String.format("%d: [%s, %s, %.2f]",
				getId(), getName(), getEmail(), getFunds());
	}

}