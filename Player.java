public class Player{
    private String name; // name of the Player
    private String mark; // the unique mark of each player

    // Player contructor which takes in the player's name and mark.
    Player(String name, String mark){
        this.setName(name); // setting the player's name
        this.setMark(mark); // setting the player's mark
    }

	/**
	* Returns value of name
	* @return
	*/
	public String getName() {
		return this.name;
	}

	/**
	* Returns value of mark
	* @return
	*/
	public String getMark() {
		return this.mark;
	}

	/**
	* Sets new value of name
	* @param
	*/
	public void setName(String name) {
		this.name = name;
	}

	/**
	* Sets new value of mark
	* @param
	*/
	public void setMark(String mark) {
		this.mark = mark;
	}

    // pick a random number within that goes from 0 to a given range
    public int randomNumber(int range){
        return (int)(Math.random()*range);
    }
}
