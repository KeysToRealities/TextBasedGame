package TextBasedGame;
import java.util.ArrayList;
public class Item {
	private int itemId;
	private String itemName;
	private String itemDescription;
	private int ItemDamage;
	private ArrayList<Integer> itemLocations;

	public Item(int itemId,
			String itemName, 
			String itemDescription,
			int ItemDamage,
			ArrayList<Integer> itemLocations) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemDescription = itemDescription;
		this.ItemDamage =ItemDamage;
		this.itemLocations = itemLocations;
	}
	public int getItemId() {
		return this.itemId;
	}
	

	public  String getItemName() {
		return itemName;
	}
	public String getItemDescription() {
		return itemDescription;
	}

	
	public ArrayList<Integer> getItemLocations() {
		return itemLocations;
	}
	public void newItemLocation(int roomID) {
		this.itemLocations.add(roomID);
	}
	public void removeItemLocation(int roomID) {
		this.itemLocations.remove(Integer.valueOf(roomID));
	}
	
	
}