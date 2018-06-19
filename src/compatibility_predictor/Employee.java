package compatibility_predictor;

public class Employee {
	
	private String name;
	private long strength, endurance, intelligence;
	private float score;
	
	//default constructor
	public Employee() {
		this.name = "";
		this.strength = 0;
		this.endurance = 0;
		this.intelligence = 0;
		this.score = 0;
	}
	
	//parameterized contructor
	public Employee(String inName, long inStength, long inEndurance, long  inIntelligence) {
		this.name = inName;
		this.strength = inStength;
		this.endurance = inEndurance;
		this.intelligence = inIntelligence;
		this.score = 0;
	}
	
	public String getName() {
		return this.name;
	}
	
	public long getStrength() {
		return this.strength;
	}
	
	public long getEndruance() {
		return this.endurance;
	}
	
	public long getIntelligence() {
		return this.intelligence;
	}
	
	public float getScore() {
		return this.score;
	}
	
	public void setScore(float inNumber) {
		this.score = inNumber;
	}
}

