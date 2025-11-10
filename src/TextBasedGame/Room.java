package TextBasedGame;
import java.util.ArrayList;
/**
 * Represents a world location (room). Stores id, name, description,
 * adjacent room ids and item ids present in the room.
 */
public class Room {
 private int roomID;
 private String Roomdescription;
 private String roomName;
 private ArrayList<Integer> adjacentRooms;
 private boolean visited;
 private ArrayList<Integer> ItemsInRoom;
 
 public Room(int roomID,String RoomName,
	 String roomDescription, 
	 ArrayList<Integer> adjacentRooms, 
	 boolean visited,
	 ArrayList<Integer> ItemsInRoom) {
	 
	 this.roomID = roomID;
	 this.roomName = RoomName;
	 this.Roomdescription = roomDescription;
	 this.adjacentRooms = adjacentRooms;
	 this.visited = visited;
	 this.ItemsInRoom = ItemsInRoom;
	 
  }
 
 public int getRoomID() {
	  return roomID;
 }
 
 public String getRoomDescription() {
	  return Roomdescription;
	  
  }
 public  String getRoomName() {
	  return roomName;
  }
  
 public  boolean getVisited() {
	  return visited;
  }
 public void setVisited(boolean visited) {
	  this.visited = visited;
  }


 public boolean getHasItem() {
	  return !ItemsInRoom.isEmpty();
  }
 public ArrayList<Integer> getItemsInRoom() {
	  return ItemsInRoom;
  }

 public  ArrayList<Integer> getAdjacentRooms() {
	  return adjacentRooms;
  }
 /**
  * Print the possible directions the player can move from the current room.
  */
 public static void findPossibleDirections() {
ArrayList<Integer> adjacentRooms = Game.RoomData.get(Player.getCurrentRoomID()-1).getAdjacentRooms();  
	System.out.println("Possible directions to move:");
    if (adjacentRooms.get(0) != 0) {
    System.out.println("north");
    }
	if (adjacentRooms.get(1) != 0) {
		System.out.println("east");
	}
	if (adjacentRooms.get(2) != 0) {
		System.out.println("south");
	}
	if (adjacentRooms.get(3) != 0) {
		System.out.println("west");
	}
	
}
	
}

