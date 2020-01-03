package ie.gmit.sw;

public class Player{
	private int id;
	private String name;
	private int age;
	private int clubId;
	private int agentId;
	private float valuation;
	private String status;
	private String position;
	
	//Server should assign ID
	public Player(int id, String name, int age, int clubId, int agentId, float valuation, String status,
			String position) {
		this.setId(id);
		this.setName(name);
		this.setAge(age);
		this.setClubId(clubId);
		this.setAgentId(agentId);
		this.setValuation(valuation);
		this.setStatus(status);
		this.setPosition(position);
		
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getClubId() {
		return clubId;
	}
	public void setClubId(int clubId) {
		this.clubId = clubId;
	}
	public int getAgentId() {
		return agentId;
	}
	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}
	public float getValuation() {
		return valuation;
	}
	public void setValuation(float valuation) {
		this.valuation = valuation;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return String.format("%d: [%s, Age: %d, Club ID: %d, Agent ID: %d, Valuation: %.2f, Status: %s, Position: %s]",
				id, name, age, clubId, agentId, valuation, status, position);
	}
	


	
}