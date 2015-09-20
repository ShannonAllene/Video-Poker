import java.util.Random;
import java.util.Arrays;
public class Deck
{
	private int [] cardRanks; //Holds the ranks associated with the cards
	private String [] suit;	//Holds the suits associated with the cards
	private int counter;	//Helps us loop through our arrays
	private int[] cardNumber; //Holds the 5 card numbers generated
	private int y;			  // Holds the generated numbers	
	
	public Deck()
	{
		Random random = new Random();
		counter =0; //Initiate counter to 0
		cardNumber = new int[5]; //holds five cards
		suit = new String[5]; // holds the five cards' suits
		cardRanks = new int[5]; //holds five cards' ranks
		//Random rand = new Random(); //DO WE NEED THIS??? NO - Way to go Alix....
		for (int i=0; i<5;i++) // Loop through to fill in the arrays
		{
			do
			{
				y = random.nextInt(52)+1;
			}while(isInHand(y) == true && counter <5);
			//If the generated number is different from the rest, assign the attributes 
			cardNumber[counter] = y;
			setSuit(cardNumber[counter]);
			setRank(cardNumber[counter]);
			counter ++;
		}
	}
	public boolean isInHand( int x) //Check to see if card is already in player's hand
	{
		for(int i=0; i<5;i++)
		{
			if(x == cardNumber[i])
				return true;
		}
		return false;
	}
	public void setSuit(int x) //set the suit of the card according to its card number
	{
		if( x>= 1 && x<=13) //Clubs are from 1 - 13
		{
			suit[counter] = "Clubs";
		}
		else if(x>= 14 && x<=26) //Diamonds are from 14 - 26 
		{
			suit[counter] = "Diamonds";
		}
		else if(x>= 27 && x<=39) //Hearts are from 27 - 39
		{
			suit[counter] = "Hearts";
		}
		else //Spades are from 40 - 52
		{
			suit[counter] = "Spades";
		}
	}
	public String[] getSuit()
	{
		return suit;
	}
	public void setRank(int x) //set the rank according to card number 
	{
		int rank = x;
		if(rank > 12)
		{
			rank = ( x % 13 ) +1; //what ever the remainder is after dividing my 13, you have to add one so it will show up the corresponding card
		}
		else
		{
			rank = rank +1; //have to add one so it will have the correct corresponding card
		}
		if(rank ==1)
		{
			rank = 14; //after dividing by 13, if the remainder is 1, then it is an Ace, so it needs to have a rank of 14
		}
		cardRanks[counter] = rank; //place the rank into the corresponding place in the array
	}
	public int [] getRank()
	{
		return cardRanks;
	}
	public int [] getCardNumber()
	{
		return cardNumber;
	}
}