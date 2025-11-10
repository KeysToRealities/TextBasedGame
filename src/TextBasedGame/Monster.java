package TextBasedGame;

public class Monster {
	private int id;
	private String name;
	private String description;
	private int health;
	private String attackName;
	private int attackDamage;
	private int LocationID;
	Monster(int id, String name, String description, int health, String attackName, int attackDamage, int LocationID) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.health = health;
		this.attackName = attackName;
		this.attackDamage = attackDamage;
		this.LocationID = LocationID;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public String getAttackName() {
		return attackName;
	}
	public int getAttackDamage() {
		return attackDamage;
	}
	public int getLocationID() {
		return LocationID;
	}
	
}