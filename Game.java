import java.util.Scanner;

public class Game {
	
	/* Last winter, around this time, I was really into probability 
	 * and old games of chance. I came up with this simple dice game and now
	 * that classes are over, I have the time to finish it. I hope you enjoy it
	 * and win! - Lozord
	 */
	
	
	//The game only has two players
	static Player p1;
	static Player p2;
	//The difference in points required to start betting
	static int threshold;
	//The hard coded top boundary of play
	static int topBound;
	//The maximum amount that a player can bet, agreed upon by both players
	static int maxBet;

	public static void main(String[] args) 
	{
		//Gotta have the welcome text
		System.out.println("Welcome to Lozord's Dice Game of Chance!");

		//The directions
		System.out.println("Here are the directions:" +
				"Every round, a player wins if they have the higher dice roll, plus the bet" +
				"There are two ways to win:\n" +
				"1) A player can cross the top bound mark to win;\n " +
				"however, they must do this exactly.\n" +
				"2) If a player goes beyond the top bound,\n they are " +
				"reversed in movement, " +
				"so any subsequent won rounds will subtract from their " +
				"score. \nIf a player gets a non-positive score " +
				"while reversed, they win.\nBetting occurs when the " +
				"agreed-upon threshold has been crossed." +
				"\n\n");

		//Get player info and values
		Scanner mainIn = new Scanner(System.in);

		System.out.print("Player One, enter your name: ");

		String p1Name = mainIn.nextLine();

		System.out.print("Player Two, enter your name: ");

		String p2Name = mainIn.nextLine();

		System.out.println("Welcome to the game, " 
				+ p1Name + " and " + p2Name + "!");
		
		System.out.println("What would you like the top bound to be?");
		
		topBound = getValidNumber(mainIn, 50, 5000);

		System.out.println("What bet threshold would you like to play with? ");

		threshold = getValidNumber(mainIn, 1, topBound/3);
		
		System.out.println("What is the maximum bet?");
		
		maxBet = getValidNumber(mainIn, 1, topBound/2);

		p1 = new Player (p1Name, 6);

		p2 = new Player (p2Name, 6);

		//Let a player get ahead randomly
		System.out.println("Roll to determine who goes first!");

		int a1, a2;

		a1 = a2 = 0;

		while (a1 == a2)
		{
			a1 = p1.pickUp().roll();
			a2 = p2.pickUp().roll();

			if (a1 > a2)
			{
				System.out.println(p1.getName() 
						+ " goes first with a boost of " + a1 + "!");
				p1.updatePoints(a1);
			}

			else if (a1 < a2)
			{
				System.out.println(p2.getName() 
						+ " goes first with a boost of " + a2 + "!");
				p2.updatePoints(a2);
			}

		}

		//count the number of rounds to let people know how 1337 they are
		int roundNum = 1;

		//the main game loop
		while(!(hasWon(p1) || hasWon(p2)))
		{
			System.out.println("\t\t\tROUND " + roundNum++);
			
			//if the betting threshold has not been met
			if (Math.abs(p1.getPoints()-p2.getPoints()) < threshold)
			{
				playRound(0);
			}
			
			//if it has
			else
			{
				int roundBet = 0;

				if (p1.getPoints() != p2.getPoints())
				{

					if (determineP1SetsBet())
					{
						System.out.print(p1.getName() 
								+ ", what shall the bet be? ");
					}
					else
					{
						System.out.print(p2.getName() 
								+ ", what shall the bet be? ");
					}
					
					roundBet = getValidNumber(mainIn, 0, maxBet);

				}

				playRound(roundBet);
			}
			
		}

		//if someone has won the game
		if(hasWon(p1))
			System.out.println("Yay " + p1.getName() + "! You won.");
		else
			System.out.println("Yay " + p2.getName() + "! You won.");

		return;

	}

	//A method that gets a integer within a given range
	//Because I can't use C++'s sweet sweet cin
	//But Java is cool too
	private static int getValidNumber(Scanner mainIn, int lower, int upper) 
	{
		int ret = lower - 1;

		while (ret < lower || ret > upper)
		{
			System.out.println("Please enter a number between " 
		+ lower + " and " + upper);

			while(!mainIn.hasNextInt())
			{
				System.out.println("Please enter in a valid number!");

				mainIn.next();
			}	
			
			ret = mainIn.nextInt();
		}

		return ret;
	}

	//Plays a single round of the game, given a bet
	private static void playRound(int bet) 
	{
		int one, two;

		one = two = 0;

		//the while loop is for when players roll the same number
		while (one == two)
		{
			one = p1.pickUp().roll();
			two = p2.pickUp().roll();

			if (one > two)
			{
				System.out.println(p1.getName() 
						+ " won the round with a roll of " + one + "!");
				//the player gets the roll and bet amount
				p1.updatePoints(one + bet);
			}

			else if (one < two)
			{
				System.out.println(p2.getName() 
						+ " won the round with a roll of " + two + "!");
				p2.updatePoints(two + bet);
			}

		}

		//reverse if they go beyond the bounds
		if (p1.getPoints() > topBound && p1.isGoingUp())
		{
			p1.reverse();
			System.out.println(p1.getName() + " got reversed!");
		}

		if (p2.getPoints() > topBound && p2.isGoingUp())
		{
			p2.reverse();
			System.out.println(p2.getName() + " got reversed!");
		}

		//print the current scores and orientations of the two players
		printGame();

	}

	//Method that prints the score and direction of the two players
	private static void printGame() 
	{

		System.out.println
		(p1.getName() + ": " + p1.getPoints() 
				+ ", Direction up?: " + p1.isGoingUp());
		System.out.println
		(p2.getName() + ": " + p2.getPoints() 
				+ ", Direction up?: " + p2.isGoingUp());
	}

	//Method that determines if player P has won the game
	public static boolean hasWon(Player p)
	{
		if(p.isGoingUp())
		{
			return p.getPoints() == topBound;
		}

		else
		{
			return p.getPoints() <= 0;
		}
		//this could be changed later to make it so that players have 
		//to land directly on a bound to win, but for now it is seemingly
		//not broken
	}

	//Method that determines who should be placing the bet, if any 
	public static boolean determineP1SetsBet()
	{
		//Assumes scores are unequal
		if (p1.isGoingUp() && p2.isGoingUp())
			{return p1.getPoints() < p2.getPoints();}
		else if (!p1.isGoingUp() && !p2.isGoingUp())
			{return p1.getPoints() > p2.getPoints();}
		else if (p1.isGoingUp() && !p2.isGoingUp())
			{return p1.getPoints() < 100 - p2.getPoints();}
		else 
			{return 100 - p1.getPoints() > p2.getPoints();}

	}

}
