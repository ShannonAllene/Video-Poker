import java.util.Arrays;

public class WinningHands
{
	private String outcome;
	
	public WinningHands(int [] arr, String[] array)
	{
		setWinner(arr,array);
	}
	// Convert the card numbers to the associated rank
	public static void convertToRank( int [] arr)
	{
		for (int i=0; i<5;i++)
		{
			arr[i] = rank(arr[i]);
		}
	}
	//Five cards in the same suit, but not consecutive in value
	public static boolean flush(int [] arr, String [] array)
	{
		int [] temp = new int[5];;
		for (int i=0;i<5;i++)
		{
			temp[i] = arr[i];
		}
	//Check to see whether the cards are all of the same suit
		boolean isFlush = false;	
		for (int i = 0; i<4;i++)
		{
			if (array[i+1] == array[i])
				isFlush = true;
			else
				return false;
		}
	//If the cards are consecutive, return false because then we have straight flush
		if(straight(temp)==true)
		{
			isFlush = false;
		}
		return isFlush;
	}
	//Determine the rank of the current card. Runs from 2-14
	public static int rank (int x)
	{
		int rank =x;
		if(rank >12)
		
		{
			rank = ( x % 13 ) +1;
		}
		else
		{
			rank = rank +1;
		}
		if(rank ==1)
			rank = 14;
		return rank;
	}
	public static boolean fullHouse(int [] arr)// This is the sorted array
	{
		int [] temp = new int[5];;
		for (int i=0;i<5;i++)
		{
			temp[i] = arr[i];
		}
		if (twoPair(temp) && threeOfKind(temp))
			return true;
		else
			return false;
	}
	public static boolean fourOfKind(int [] arr)//This is the sorted array
	{
		int [] temp = new int[5];;
		for (int i=0;i<5;i++)
		{
			temp[i] = arr[i];
		}
		convertToRank(temp);
		sort(temp, 0, 4);
		int countA = rankCount(temp[0],temp);
		int countB = rankCount(temp[1],temp);
		if(countA == 4)
			return true;
		else if (countB == 4)
			return true;
		else
			return false;
	}
	public static boolean straightFlush( int [] arr, String [] array )// arr is the sorted array
	{
		int [] temp = new int[5];;
		for (int i=0;i<5;i++)
		{
			temp[i] = arr[i];
		}
	//If it is a straight, and the cards are of the same suit, return true.
		if (straight (temp) && sameSuits(array))
			return true;
		else
			return false;
	}
	public static boolean royalFlush(int [] arr, String [] array)
	{
		int [] temp = new int[5];;
		for (int i=0;i<5;i++)
		{
			temp[i] = arr[i];
		}
		//Check whether the 5 cards are straight.
		if(straightFlush(temp, array))
		{
		//Since it is a straight, and the cards were just sorted, if the first card is a 10, it is a royal flush
			if(temp[0] == 10)
				return true;
			else
				return false;
		}
		else
			return false;
	}

	public static boolean threeOfKind(int [] arr) // this is the sorted rank array
	{
		int [] temp = new int[5];;
		for (int i=0;i<5;i++)
		{
			temp[i] = arr[i];
		}
		convertToRank(temp); // Convert the integers to the appropriate rank
		sort(temp,0,4);		// Sort the ranked cards
		int countA = rankCount(temp[0], temp); // Evaluate how many instances of this rank there are
		int countB;
		int countC;
		
		if(countA >2 ) // if the first one is present more than twice, we have a three of a kind
			return true;
		//Next 2 else if seem redundant. I should have documented when I was trying to figure it out
		else if(countA == 2)  
		{
		
			countB = rankCount(temp[countA], temp);	//Start at the index of the number of the previous card to avoid counting it twice
			if(countB>2) 
				return true;
			else if(countB ==1)
			{
				countC = rankCount(temp[countA+countB],temp);
				if (countC >2)
					return true;
			}
			
		}
		else if(countA == 1)
		{
			countB = rankCount(temp[countA], temp);
			if(countB>2)
				return true;
			else if(countB ==1)
			{
				countC = rankCount(temp[countA+countB],temp);
				if(countC >2)
					return true;
			}
		}
		else {}
			return false;
	}
	
	public static boolean straight(int [] arr)
	{
		int [] temp = new int[5];;
		for (int i=0;i<5;i++)
		{
			temp[i] = arr[i];
		}
		convertToRank(temp);
		sort(temp,0,4);
		boolean isStraight = false;
	//If every card in the sorted array is equal to the previous one +1, it is a straight.
		for (int i = 0; i<4;i++)
		{
			if (temp[i+1] == temp[i]+1)
				isStraight = true;
			else
				return false;
		}
		return isStraight;
	}
	//Identify the suit of a given card
	public static String suit(int x)
	{
		String str = "";
		if( x>= 1 && x<=13)
		{
			str = "Clubs";
		}
		else if(x>= 14 && x<=26)
		{
			str = "Diamonds";
		}
		else if(x>= 27 && x<=39)
		{
			str = "Hearts";
		}
		else
		{
			str = "Spades";
		}
		return str;
	}
	
	public static boolean twoPair(int [] arr) // this array it is getting will be the one holding the ranks
	{
		int [] temp = new int[5];;
		for (int i=0;i<5;i++)
		{
			temp[i] = arr[i];
		}
		convertToRank(temp);
		sort(temp,0,4);
		boolean twopair = false;
		//We only need to consider the first three because if none of them is repeated
		//we cannot have a two pair
		int countA = rankCount(temp[0], temp);
		int countB = rankCount(temp[1], temp);
		int countC;
		// if the first one is repeated, check to see if either the second or the third one is also
		if(countA >1 && countA <4 ) //If countA is more than 3 we cannot have a two pair
		{
			countB = rankCount(temp[countA], temp);
			if(countB>1) //if the second one is repeated, it is a two pair
				twopair = true;
			else if(rankCount(temp[countA+countB],temp)>1) // if third is repeated, it is a two pair
				twopair = true;
			else
			twopair = false;
		}
	//If the first is not a pair, start again with the second this time
		else if (countB >1 && countB <3)
		{
			countC = rankCount(temp[countB+1], temp); // account for the first value that was skipped (the first was not part of a pair) by adding 1 to the index
			if(countC > 1)
				twopair = true; //if the third is also a pair, we got a winning hand
			else
				twopair = false;
		}
		else{}
		return twopair;	
	}
	
	public static boolean jacksOrBetter(int [] arr)
	{
		int [] temp = new int[5];;
		for (int i=0;i<5;i++)
		{
			temp[i] = arr[i];
		}
		convertToRank(temp);
		int countJ =0; //keep up with how many jacks 
		int countQ=0; //keeps up with how many queens
		int countK = 0; //number of kings
		int countA=0;  //number of aces
		for (int i=0; i<5; i++) //check each index value
		{
			//increment counts accordingly
			if (temp[i]==11) 
				countJ++;
			else if (temp[i]==12)
				countQ++;
			else if (temp[i]==13)
				countK++;
			else if(temp[i]==14)
				countA++;
			else{}
		}
		if(countJ >1 || countQ>1 || countK>1 || countA>1) //if any have a pair, then true
			return true;
		else
			return false;
	}
	
	//Sort the array to reduce the complexity of checking for hands
	public static void sort(int [] arr, int start, int end)
	{
		int n = end -start;
		if(start == end)
			arr[start] = arr[start];
		else if (start <end)
		{
			for (int i=0; i<n; i++)
			{
				int first = arr[end-1];
				int last = arr[end];
				if (first > last)
				{
				arr[end-1] = last;
				arr[end] = first;
				}
				sort(arr, start, end-1);
			}
		}
	}
	
	//Returns the number of integers in the array that  are identical to the one provided
	public static int rankCount( int x, int [] arr)
	{
		int count =0;
		for (int i=0; i<5;i++)
		{
			if(arr[i] == x)
				count++;
		}
		return count;	
	}
	
	public static boolean sameSuits (String [] array)
	{
		boolean sameSuit = false;
		for (int i=0; i<4;i++)
		{
			if(array[i]== array[i+1])
				sameSuit = true;
			else
				return false; // If one suit differs, they are not all of the same suit.
		}
		return sameSuit;
	}
	//Determine the outcome of the provided hand and record it.
	public void setWinner(int [] arr, String [] array)
	{
		if(royalFlush(arr, array))
			outcome = "Royal Flush";
		else if(straightFlush(arr,array))
			outcome = "Straight Flush";
		else if(fourOfKind(arr))
			outcome = "Four of a Kind";
		else if(fullHouse(arr))
			outcome = "Full House";
		else if(flush(arr, array))
			outcome = "Flush";
		else if(straight(arr))
			outcome = "Straight";
		else if(threeOfKind(arr))
			outcome = "Three of a Kind";
		else if(twoPair(arr))
			outcome = "Two Pair";
		else if(jacksOrBetter(arr))
			outcome = "Jacks or Better";
		else
			outcome = "Not a Winning Hand";
	}
	//Returns the current wining hand
	public String getWinner()
	{
		return outcome;
	}
}