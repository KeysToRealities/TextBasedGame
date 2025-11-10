package TextBasedGame;
import java.util.ArrayList;
import java.util.List;

public class Puzzle {
    private int id;
    private String name;
    private String description;
    private String solution;
    private int   numberOfAttempts;
    private int   roomId;
    private String hint;

/**
 * Represents a puzzle placed in a room. Contains metadata such as solution,
 * number of attempts remaining and a hint.
 */
    public Puzzle(int id, String name, String description, String solution,
              int numberOfAttempts, int roomId,
                  String hint) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.solution = solution;
       
        this.numberOfAttempts= numberOfAttempts;
        this.roomId = roomId;
        this.hint = hint;
    }
    public int getId() {
		return id;
	}
    	public String getName() {
		return name;
	}
    public String getDescription() {
		return description;
	}
   
	public int getNumberOfAttempts() {
			return numberOfAttempts;
		}
		public int getRoomId() {
		return roomId;
	}

		public String getHint() {
		return hint;
	}
		public String getSolution() {
		    return solution;
		}
		
	public void setNumberOfAttempts(int remaining) {
			this.numberOfAttempts = remaining;
			
		}
}