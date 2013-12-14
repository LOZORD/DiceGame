import java.util.Random;

//Acts like a die that you roll
public class Die 
{
	//the number of sides of the die
	private int numSides;
	//it gets its very own rng!
	private Random rng;

	//Constructor which takes the number of sides
	public Die(int num) {
		numSides = num;
		rng = new Random();
	}

	//A die can be rolled, just like IRL!
	public int roll ()
	{
		//the secret sauce number
		return (rng.nextInt(53706)) % numSides;
	}

	//Returns this die's number of sides
	public int getSize ()
	{
		return numSides;
	}
}
