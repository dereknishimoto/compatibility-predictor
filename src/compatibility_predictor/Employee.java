package compatibility_predictor;

public class Employee {
	
	private String name;
	private long strength, endurance, intelligence, bravery;
	private float score;
	
	//default constructor
	public Employee() {
		this.name = "";
		this.strength = 0;
		this.endurance = 0;
		this.intelligence = 0;
		this.bravery = 0;
		this.score = 0;
	}
	
	//parameterized constructor
	public Employee(String inName, long inStength, long inEndurance, long  inIntelligence, long inBravery) {
		this.name = inName;
		this.strength = inStength;
		this.endurance = inEndurance;
		this.intelligence = inIntelligence;
		this.bravery = inBravery;
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
	
	public long getBravery() {
		return this.bravery;
	}
	
	public float getScore() {
		return this.score;
	}
	
	public void setScore(float inNumber) {
		this.score = inNumber;
	}
}

