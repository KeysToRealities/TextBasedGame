package TextBasedGame;
import java.util.ArrayList;
import java.util.Scanner;

public class Player {
	private static int currentRoomID;
	private static ArrayList<Item> inventory = new ArrayList<>();
	private static Weapon equippedItem;
	private static int DefaultAttackDamage = 5;
	private static int Health = 100;
	 /**
     * Move the player in a cardinal direction.
     * direction must be "north", "east", "south" or "west".
     */
	public static void MoveDirection(String direction) {
		
	 int currentRoomId = Player.getCurrentRoomID();
	 Room currentRoom = Game.RoomData.get(currentRoomId-1);
	 
	 int index = switch (direction){
	        case"north"->0;
	        case"east"->1;
	        case"south"->2;
	        case"west"->3;
	        default->-1;
};
		ArrayList<Integer> adjacentRooms = currentRoom.getAdjacentRooms();
		
			int nextRoomID = adjacentRooms.get(index);
			
			if (nextRoomID != 0) {
				
				Player.setCurrentRoomID(nextRoomID);
				
				currentRoom = Game.RoomData.get(nextRoomID-1);
				
				System.out.println("You have moved to: " + currentRoom.getRoomName());
				System.out.println(currentRoom.getRoomDescription());
				Player.monsterChecker();
				Player.solvePuzzleIfExists();
				
				if (!currentRoom.getVisited()) {
					
					currentRoom.setVisited(true);
					
					
				} else {
					System.out.println("You have been here before.");
					
				}
			} else {
				System.out.println("You can't go that way.");
			}
		}

    /** Get the player's current room ID. */
		 
public static int getCurrentRoomID() {
		return currentRoomID;
    }
/** Set the player's current room ID. */
public static void setCurrentRoomID(int roomID) {
		currentRoomID = roomID;
	}
public static int getAttackDamage() {
	if (equippedItem != null) {
		return equippedItem.getWeaponDamage();
	} else {
		return DefaultAttackDamage;
	} 

}
public static int getHealth() {
	return Health;
}
public static void setHealth(int health) {
	Health = health;
}

public static void equipItem(String itemName) {
	for (Item item : inventory) {
		if (item.getItemName().equalsIgnoreCase(itemName) && item instanceof Weapon) {
			equippedItem =  (Weapon) item;
			System.out.println("You have equipped: " + equippedItem.getItemName());
			return;
		}
	}
	System.out.println("You don't have a weapon called `" + itemName + "` in your inventory.");
}
public static void useItem(String itemName) {
	for (Item item : inventory) {
		if (item.getItemName().equalsIgnoreCase(itemName) && item instanceof Consumable) {
			Consumable consumable = (Consumable) item;
			Health += consumable.getHealthRestore();
			inventory.remove(item);
			if (Health > 100) {
				Health = 100; 
			}
			System.out.println("You used: " + consumable.getItemName());
		    System.out.println("Your health is now: " + Health);
		    return;
		}
		
	}
	System.out.println("You don't have a consumable called `" + itemName + "` in your inventory.");
}
public static void unequipItem() {
	if (equippedItem != null) {
		System.out.println("You have unequipped: " + equippedItem.getItemName());
		equippedItem = null;
	} else {
		System.out.println("No item is currently equipped.");
	}
}
/**
 * Explore the current room and list any items that are present.
 * Uses Game.ItemData to find items referencing the current room.
 */
public static void exploreRoom() {
		Room currentRoom = Game.RoomData.get(currentRoomID-1);
		
		if(currentRoom.getHasItem()) {
			
			System.out.println("You see an item in the room.");
			
			for (Item item : Game.ItemData) {
				
				if (item.getItemLocations().contains(currentRoomID)) {
					
					System.out.println("Item found: " + item.getItemName());
					
					System.out.println("Description: " + item.getItemDescription());
				}
			}
			
		}else {
			System.out.println("There are no items in this room.");
		}
	}
/**
 * Attempt to pick up an item by name from the current room.
 * Item matching is case-insensitive. Updates inventory, room item list and item locations.
 */
	
public static void PickUpItem(String itemName) {
	    Room currentRoom = Game.RoomData.get(currentRoomID - 1);
	    ArrayList<Integer> roomItemIDs = currentRoom.getItemsInRoom();
	    if (roomItemIDs == null || roomItemIDs.isEmpty()) {
	        System.out.println("There are no items in this room.");
	        return;
	    }

	    // Search Game.ItemData for an item with matching name that is located in this room
	    for (int i = 0; i < roomItemIDs.size(); i++) {
	     
	        for (Item item : Game.ItemData) {
	            if (item.getItemName().equalsIgnoreCase(itemName) && item.getItemLocations().contains(currentRoomID)) {
	                // add to inventory
	                inventory.add(item);
	                // remove from room's item list 
	                roomItemIDs.remove(i);
	                // remove the room location from the item and mark collected
	                item.removeItemLocation(currentRoomID);
	                
	                System.out.println("You picked up: " + item.getItemName());
	                return;
	            }
	        }
	    }

	    System.out.println("There is no item called `" + itemName + "` in this room.");
	}
/**
 * Drop an item from inventory into the current room.
 * Adds current room id to item's locations and updates room item list.
 */
public static void DropItem(String itemName) {
		// Search inventory for the item
	    for (int i = 0; i < inventory.size(); i++) {
	       for (Item item : Game.ItemData) {
	        if (item.getItemName().equalsIgnoreCase(itemName)) {
	            // Remove from inventory
	            inventory.remove(i);
	            // Add current room to item's locations and mark not collected
	            item.newItemLocation(currentRoomID);
	            // Add item ID to current room's item list
	            Room currentRoom = Game.RoomData.get(currentRoomID - 1);
	            currentRoom.getItemsInRoom().add(item.getItemId()); 
	            System.out.println("You dropped: " + item.getItemName());
	            return;
	        }
	    }
	    System.out.println(" You don't have an item called `" + itemName + "` in your inventory.");	
		
	}
}
/**
 * Inspect an item in the player's inventory by name.
 */
public static void InspectItem(String itemName) {
		if (inventory == null || inventory.isEmpty()) {
			System.out.println("Your inventory is empty.");
			return;
		}
		for (Item it : inventory) {
			if (it.getItemName().equalsIgnoreCase(itemName)) {
				System.out.println(it.getItemName() + " - " + it.getItemDescription());
				return;
			}
		}
		System.out.println("You don't have an item called `" + itemName + "` in your inventory.");
	
	}
/**
 * Display the inventory contents to the player.
 */


public static void ViewInventory() {
			 if (inventory.isEmpty()) {
	        System.out.println("Your inventory is empty.");
	        return;
	    }

	    System.out.println("Your inventory contains:");
	    for (Item item : inventory) {
	        System.out.println("- " + item.getItemName() + ": " + item.getItemDescription());
	    }
}  

/**
 * Attempt to solve the puzzle in the current room (if present).
 * Prompts the user for an answer and consumes attempts.
 */
public static void solvePuzzleIfExists() {
    int currentRoom = Player.getCurrentRoomID();

    for (Puzzle p : Game.PuzzleData) {
        if (p.getRoomId() == currentRoom) {
            System.out.println("You encounter a puzzle as you enter...");
            System.out.println("Do you wish to solve this puzzle now? (yes/no)");
            Scanner scanner = new Scanner(System.in);
            String response = scanner.nextLine().trim().toLowerCase();
            if (response.equals("yes")) {
            solvePuzzle(); 
            break;
            } else {
				System.out.println("You can come back to attempt to solve the puzzle later");
			}
        }
    }
}
public static void solvePuzzle() {
    int currentRoom = Player.getCurrentRoomID();

    // Find puzzle
    Puzzle puzzle = null;
    for (Puzzle p : Game.PuzzleData) {
        if (p.getRoomId() == currentRoom) {
            puzzle = p;
            break;
        }
    }

    if (puzzle == null) {
        System.out.println("There's no puzzle in this room.");
        return;
    }
 // Puzzle prompt
    System.out.println("Puzzle: " + puzzle.getName());
    System.out.println(puzzle.getDescription());
    System.out.print("Enter your answer: ");

    Scanner scanner = new Scanner(System.in);
    String answer = scanner.nextLine().trim();

    if (answer.equalsIgnoreCase(puzzle.getSolution())) {
        System.out.println("Correct! You've solved the puzzle.");
        Game.PuzzleData.remove(puzzle);
    } else {
        int remaining = puzzle.getNumberOfAttempts() - 1;
        puzzle.setNumberOfAttempts(remaining); // 

        System.out.println("Incorrect. Attempts left: " + remaining);
        if (remaining > 0) {
            System.out.println("Hint: " + puzzle.getHint());
            solvePuzzle();
        }

        if (remaining == 0) {
            System.out.println("You've used all your attempts.");
            Game.PuzzleData.remove(puzzle);
        }
    }
}
public static void monsterChecker() {
	int currentRoom = Player.getCurrentRoomID();

	for (Monster m : Game.MonsterData) {
		if (m.getLocationID() == currentRoom) {
			System.out.println("A wild Monster appears!");
			System.out.println("Type examine to learn more about the monster.");
			while(true) {
				Scanner scanner1 = new Scanner(System.in);
				String examineInput = scanner1.nextLine().trim();
				if (examineInput.equalsIgnoreCase("examine")) {
					System.out.println("Monster Name: " + m.getName());
					System.out.println("Description: " + m.getDescription());
					System.out.println("Attack Damage: " + m.getAttackDamage());
					break;
				} else {
					System.out.println("Invalid command. Please type 'examine' to learn more about the monster.");
				}
			}
			
		    System.out.println("Would you like to Attack or ignore?");
		    Scanner scanner = new Scanner(System.in);
		    String choice = scanner.nextLine().trim();
		    while(true) {
		    if (choice.equalsIgnoreCase("Attack")) {
		       Player.InitiateCombat(m);
		       break;
		    } else if (choice.equalsIgnoreCase("ignore")) {
		        System.out.println("You chose to ignore the " + m.getName() + ".");
		        Game.MonsterData.remove(m);
		        break;
		    } else {
		        System.out.println("Invalid choice. Please enter 'Attack' or 'ignore'.");
		        choice = scanner.nextLine().trim();
		      
		    }
		
		}
	}
	}
	
}
	
public static void InitiateCombat(Monster monster) {
			Scanner scanner = new Scanner(System.in);
	System.out.println("Combat initiated with " + monster.getName() + "!");
	
	while (monster.getHealth() > 0 && Player.getHealth() > 0) {
		// Player's turn
		System.out.println("Your Health: " + Player.getHealth());
		System.out.println(monster.getName() + " Health: " + monster.getHealth());
		System.out.println("Choose your action: Attack or Run");
		String action = scanner.nextLine().trim();
		
		if (action.equalsIgnoreCase("Attack")) {
			int damageDealt = Player.getAttackDamage();
			monster.setHealth(monster.getHealth() - damageDealt);
			if(equippedItem != null) {
				System.out.println("You used " + equippedItem.getItemName() + " to attack!");
				System.out.println("You dealt " + damageDealt + " damage to " + monster.getName() + "." + "");
			}
			else {
				System.out.println("You dealt " + damageDealt + " damage to " + monster.getName() +  " using your fists! ");
			}
		} else if (action.equalsIgnoreCase("Run")) {
			System.out.println("You fled from the combat.");
			
			return;
		} else {
			System.out.println("Invalid action. Try again.");
			continue;
		}
		
		// Monster's turn
		if (monster.getHealth() > 0) {
			int randomNumber = (int)(Math.random() *10 ) + 1;
			if (randomNumber <= 3) {
				System.out.println(monster.getName() + " Critically Striked you!");
				int damageTaken = monster.getAttackDamage() * 2;
				Player.setHealth(Player.getHealth() - damageTaken);
				System.out.println(monster.getName() + " used " + monster.getAttackName() + " And did "+ damageTaken + " damage to you.");
			}else {
			int damageTaken = monster.getAttackDamage();
			Player.setHealth(Player.getHealth() - damageTaken);
			System.out.println(monster.getName() + " used " + monster.getAttackName() + " And did "+ damageTaken + " damage to you.");
		}
	}
	
	if (Player.getHealth() <= 0) {
		System.out.println("You have been defeated by " + monster.getName() + ".");
		System.out.println("Game Over.");
		System.out.println("Would you like to restart the game? (yes/no)");
		String restartChoice = scanner.nextLine().trim();
		while (true) {
		if (restartChoice.equalsIgnoreCase("yes")) {
			Main.startGame();
			break;
		} else if (restartChoice.equalsIgnoreCase("no")) {
			
			System.out.println("Thank you for playing!");
			System.exit(0);
			break;
		}
		else {
			System.out.println("Invalid choice. Please enter 'yes' or 'no'.");
			restartChoice = scanner.nextLine().trim();
		}
		}
	} else {
		System.out.println("You have defeated " + monster.getName() + "!");
		Game.MonsterData.remove(monster);
	}
}


}
	
}
