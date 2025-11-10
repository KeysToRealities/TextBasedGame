package TextBasedGame;
import java.io.*;
import java.util.*;

public class Game {
public static ArrayList<Room> RoomData;
public static ArrayList<Item> ItemData;
public static ArrayList<Puzzle> PuzzleData;
public static ArrayList<Monster> MonsterData;
public static HashMap<String, Runnable> commandInputs = new HashMap<>();

public static void loadGame() {
    welcomeMessage();
    RoomData = ParseRoomdata();
    ItemData = ParseItemData();
    MonsterData = ParseMonsterData();
    PuzzleData = ParsePuzzleData();
    registerCommands();
     }

public static void welcomeMessage() {
	System.out.println("Welcome to the Room explorer Text-Based Adventure Game!");
	System.out.println("You find yourself in a mysterious world filled with rooms and items.");
	System.out.println("Explore the rooms, pick up items, and embark on an exciting adventure!");
	System.out.println("Type 'help' to see a list of commands you can use.");
	System.out.println("HaveFun!");
}

public static void registerCommands() {
	NavigationCommands();
    ActionCommands();
    gameCommands();
}
public static void NavigationCommands() {
	commandInputs.put("north", () -> Player.MoveDirection("north"));
    commandInputs.put("south", () -> Player.MoveDirection("south"));
    commandInputs.put("east", () -> Player.MoveDirection("east"));
    commandInputs.put("west", () -> Player.MoveDirection("west"));
    commandInputs.put("move north", () -> Player.MoveDirection("north"));
    commandInputs.put("move south", () -> Player.MoveDirection("south"));
    commandInputs.put("move east", () -> Player.MoveDirection("east"));
    commandInputs.put("move west", () -> Player.MoveDirection("west"));
}
public static void ActionCommands() {
	commandInputs.put("explore", () -> Player.exploreRoom());
	commandInputs.put("solve", () -> Player.solvePuzzle());
    commandInputs.put("inventory", () -> Player.ViewInventory());
    
   
}
public static void gameCommands() {
	commandInputs.put("help", () -> Game.help());
	commandInputs.put("quit", () -> Game.quit());
	commandInputs.put("lost", () -> Game.lost());
}

public static void quit() {
	System.out.println("Thank you for playing! Goodbye.");
	System.exit(0);
}
public static void help() {
	System.out.println("Available commands:");
	System.out.println(" <direction> - Move in the specified direction (north, south, east, west)");
	System.out.println("pickup <item> - Pick up an item");
	System.out.println("drop <item> - Drop an item from your inventory");
	System.out.println("inspect <item> - Inspect an item in your inventory");
	System.out.println("inventory - View your current inventory");
	System.out.println("help - Show this help message");
	System.out.println("lost - Show your current location and possible directions");
	System.out.println("quit - Exit the game");

}
public static void lost() {
  System.out.println("You are currently in the room " + Game.RoomData.get(Player.getCurrentRoomID()-1).getRoomName());
  System.out.println("You can go in these following directions");
  Room.findPossibleDirections();
}

public static ArrayList<Room> ParseRoomdata() {
    ArrayList<Room> rooms = new ArrayList<>();
    String delimiter = "/";

    InputStream inputStream = Game.class.getResourceAsStream("/TextBasedGame/RoomData2.txt");

    if (inputStream == null) {
        System.out.println("Could not find RoomData2.txt");
        return rooms;
    }

    try (Scanner scanner = new Scanner(inputStream)) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            // Skip completely blank lines
            if (line.isEmpty()) continue;

            String[] parts = line.split(delimiter);

            if (parts.length < 5) {
                System.out.println("⚠️ Skipping malformed line: " + line);
                continue;
            }

            int roomNumber = Integer.parseInt(parts[0].trim());
            String roomName = parts[1].trim();
            String description = parts[2].trim();
            boolean visited = Boolean.parseBoolean(parts[3].trim());

            // Parse exits (always required)
            String[] exitTokens = parts[4].split(",");
            ArrayList<Integer> roomExits = new ArrayList<>();
            for (String token : exitTokens) {
                roomExits.add(Integer.parseInt(token.trim()));
            }

            // Parse items if present (optional part[5])
            ArrayList<Integer> itemIds = new ArrayList<>();
            if (parts.length >= 6 && !parts[5].trim().isEmpty()) {
                String[] itemTokens = parts[5].split(",");
                for (String token : itemTokens) {
                    token = token.trim();
                    if (!token.isEmpty()) {
                        try {
                            itemIds.add(Integer.parseInt(token));
                        } catch (NumberFormatException e) {
                            System.out.println("⚠️ Invalid item ID in room " + roomNumber + ": " + token);
                        }
                    }
                }
            }

            // Create the Room
            Room room = new Room(roomNumber, roomName, description, roomExits, visited, itemIds);
            rooms.add(room);
        }

    } catch (Exception e) {
        System.out.println("❌ Error reading RoomData2.txt: " + e.getMessage());
        e.printStackTrace();
    }

    return rooms;
}
public static ArrayList<Item> ParseItemData() {
    ArrayList<Item> items = new ArrayList<>();
    String delimiter = "/";
    InputStream inputStream = Game.class.getResourceAsStream("/TextBasedGame/ItemData.txt");
    if (inputStream == null) {
        System.out.println("Could not find ItemData File");
        return items;
    }

    try (Scanner scanner = new Scanner(inputStream)) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split(delimiter);
            if (parts.length < 6) {
                System.out.println("Skipping malformed line: " + line);
                continue;
            }

            try {
                int itemID = Integer.parseInt(parts[0].trim());
                String itemName = parts[1].trim();
                String itemDescription = parts[2].trim().replaceAll("^\"|\"$", "");
                int itemNumber = Integer.parseInt(parts[3].trim());

                ArrayList<Integer> itemLocations = new ArrayList<>();
                String locPart = parts[4].trim();
                if (!locPart.isEmpty()) {
                    for (String token : locPart.split(",")) {
                        token = token.trim();
                        if (!token.isEmpty()) {
                            itemLocations.add(Integer.parseInt(token));
                        }
                    }
                }

                String typeOfItem = parts[5].trim();
                if (typeOfItem.equalsIgnoreCase("Weapon")) {
                    items.add(new Weapon(itemID, itemName, itemDescription, itemNumber, itemLocations));
                } else if (typeOfItem.equalsIgnoreCase("Healing") || typeOfItem.equalsIgnoreCase("Consumable")) {
                    items.add(new Consumable(itemID, itemName, itemDescription, itemNumber, itemLocations));
                } else {
                 
                    items.add(new Item(itemID, itemName, itemDescription, itemNumber, itemLocations));
                }

            } catch (Exception e) {
                System.out.println("Error parsing item line: " + line + " : " + e.getMessage());
            }
        }
    } catch (Exception e) {
        System.out.println("Error reading ItemData file: " + e.getMessage());
        e.printStackTrace();
    }

    return items;
}
public static ArrayList<Puzzle> ParsePuzzleData() {
    ArrayList<Puzzle> puzzles = new ArrayList<>();
    String delimiter = "/";

    InputStream inputStream = Game.class.getResourceAsStream("/TextBasedGame/PuzzleData.txt");

    if (inputStream == null) {
        System.out.println("Could not find PuzzleData.txt");
        return puzzles;
    }

    try (Scanner scanner = new Scanner(inputStream)) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split(delimiter);
            if (parts.length < 7) {
                System.out.println("Skipping malformed line: " + line);
                continue;
            }

            int id = Integer.parseInt(parts[0].trim());
            String name = parts[1].trim();
            String description = parts[2].trim();
            String solution = parts[3].trim();
            int numberOfAttempts = Integer.parseInt(parts[4].trim());
            int roomId = Integer.parseInt(parts[5].trim());
            String hint = parts[6].trim();

            Puzzle puzzle = new Puzzle(id, name, description, solution, numberOfAttempts, roomId, hint);
            puzzles.add(puzzle);
        }
    } catch (Exception e) {
        System.out.println("❌ Error reading PuzzleData.txt: " + e.getMessage());
        e.printStackTrace();
    }

    return puzzles;
}
public static ArrayList<Monster> ParseMonsterData() {
	ArrayList<Monster> monsters = new ArrayList<>();
		String delimiter = "/";

	InputStream inputStream = Game.class.getResourceAsStream("/TextBasedGame/MonsterData.txt");
	if (inputStream == null) {
	    System.out.println("Could not find MonsterData.txt");
	    return monsters;
	}
	try (Scanner scanner = new Scanner(inputStream)) {
	    while (scanner.hasNextLine()) {
	        String line = scanner.nextLine().trim();
	        if (line.isEmpty()) continue;

	        String[] parts = line.split(delimiter);
	        if (parts.length < 7) {
	            System.out.println("Skipping malformed line: " + line);
	            continue;
	        }

	        int id = Integer.parseInt(parts[0].trim());
	        String name = parts[1].trim();
	        String description = parts[2].trim();
	        int health = Integer.parseInt(parts[3].trim());
	        String attackName = parts[4].trim();
	        int attackDamage = Integer.parseInt(parts[5].trim());
	        int locationID = Integer.parseInt(parts[6].trim());

	        Monster monster = new Monster(id, name, description, health, attackName, attackDamage, locationID);
	        monsters.add(monster);
	    }
	} catch (Exception e) {
	    System.out.println("❌ Error reading MonsterData.txt: " + e.getMessage());
	    e.printStackTrace();
	}
	return monsters;
		
}
}
