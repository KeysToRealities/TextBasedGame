package TextBasedGame; 
import java.util.*;

public class Main {

	public static void main(String[] args) {
		startGame();
      }
	public static void startGame() {
		Game.loadGame();
		Player.setCurrentRoomID(1);
	    Game.RoomData.get(Player.getCurrentRoomID()-1).setVisited(true);
	    System.out.println(Game.RoomData.get(Player.getCurrentRoomID()-1).getRoomDescription());
	   	
	    Scanner scanner = new Scanner(System.in);
	    while(true) {
        
			System.out.println("Enter a command: ");
			
        	String userInput = scanner.nextLine();
			
        	ProcessUserInput(userInput.toLowerCase().trim());
			} 
	}

	public static void ProcessUserInput(String input) {
        // Handle item commands
		if (Game.commandInputs.containsKey(input)) {
			Game.commandInputs.get(input).run();
			
			
		}
		else if (input.startsWith("pickup")) {
            String itemName = input.substring(7).trim();
            if (!itemName.isEmpty()) {
                Player.PickUpItem(itemName);
                
            }
        }
		else if (input.startsWith("pick up")) {
            String itemName = input.substring(8).trim();
            if (!itemName.isEmpty()) {
                Player.PickUpItem(itemName);
                
            }
        }
     // drop item
		else if (input.startsWith("drop")) {
     			String itemName = input.substring(5).trim();
     			if (!itemName.isEmpty()) {
     				Player.DropItem(itemName);
     				
     			}
     		}

     		// inspect item
		else if (input.startsWith("inspect")) {
     			String itemName = input.substring(8).trim();
     			if (!itemName.isEmpty()) {
     				Player.InspectItem(itemName);
     				
     			}
		}
     
	 		
		else if (input.startsWith("equip")) {
	 			String itemName1 = input.substring(6).trim();
	 			if (!itemName1.isEmpty()) {
	 				Player.equipItem(itemName1);
	 				
	 			}
	 		}
		else if (input.startsWith("use")) {
			 	String itemName2 = input.substring(4).trim();
 			if (!itemName2.isEmpty()) {
 				Player.useItem(itemName2);
 				
 			}
		}
     			
     	
     else if (input.startsWith("unequip")) {
    	   Player.unequipItem();
		    				
		    	}
        

        
      else {
		System.out.println("Invalid command. Type 'help' for a list of commands.");
    }

	
}
	
	}


