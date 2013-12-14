//This class implements its a player who plays my dice game!
public class Player {

	//Their name
	private String name;
	//How many points they have scored
	private int points;
	//Whether they are trying to go up or down in score
	private boolean goingUp;
	//They get their very own die
	private Die die;
	
	//Player constructor
	//They start out going up in the game and without any points
	public Player (String n, int dice_side_num)
	{
		name = n;
		points = 0;
		goingUp = true;
		die = new Die (dice_side_num);
	}


	//Getters and setters
	
	public String getName()
	{
		return name;
	}

	public int getPoints ()
	{
		return points;
	}

	public boolean isGoingUp ()
	{
		return goingUp;
	}

	//Reverses a players movement if they cross the 100 inexactly
	public void reverse ()
	{
		if (goingUp)
			goingUp = false;
	}
	
	//Trying to make it lifelike, so players can interact with their die
	//It get the jokes that might come from "players" and "picking up"
	public Die pickUp ()
	{
		return die;
	}
	
	//Updates a player's points based on their direction
	public void updatePoints (int n)
	{	
		if (this.isGoingUp())	
			points += n;
		else
			points -= n;
	}


	
}
