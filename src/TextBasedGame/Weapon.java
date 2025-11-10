package TextBasedGame;

public class Weapon extends Item {
	private int weaponDamage;

	public Weapon(int itemId,
				  String itemName,
				  String itemDescription,
				  int weaponDamage,
				  java.util.ArrayList<Integer> itemLocations) {
		super(itemId, itemName, itemDescription, weaponDamage, itemLocations);
		this.weaponDamage = weaponDamage;
	}

	public int getWeaponDamage() {
		return weaponDamage;
	}
 
}
