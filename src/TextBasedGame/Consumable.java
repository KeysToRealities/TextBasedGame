package TextBasedGame;

public class Consumable extends Item {
	private int healthRestore;

	public Consumable(int itemId,
					  String itemName,
					  String itemDescription,
					  int healthRestore,
					  java.util.ArrayList<Integer> itemLocations) {
		super(itemId, itemName, itemDescription, healthRestore, itemLocations);
		this.healthRestore = healthRestore;
	}

	public int getHealthRestore() {
		return healthRestore;
	}

}
