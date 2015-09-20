import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.Random;
import java.util.Arrays;
import java.util.Scanner;
import java.io.*;
import javax.imageio.*;

public class Board extends JPanel implements MouseListener, ActionListener
{
	private Deck currentDeck;
	private int [] cardNumbers;
	private JPanel pokerTable, playingAreaPanel, drawPanel, cardPanel, buttonPanel, slPanel, betPanel, statsPanel, leftPanel, topPanel;
	private int counter;
	private JPanel[] underneathDisplay;
	private JLabel [] display; //Store labels as an array
	private ImageIcon icon;
	private JPanel [] selected;
	private JButton drawButton, saveButton, loadButton;
	private JLabel statsLabel, titleLabel, creditLabel, playedLabel, wonLabel, lostLabel, winningsLabel,lossesLabel, betLabel, ourWinLabel;
	private JLabel creditValue, playedValue, wonValue, lostValue, winningsValue, lossesValue;
	private int credit, gamesPlayed, gamesWon, gamesLost, winnings, losses, bet;
	private JTextField betField;
	private WinningHands win; // To determine the outcome of a game
	private String ourWin; // Outcome of the current hand
	private String lastState;// Know the state of the draw/discard button when a game is being saved
	private int currentWin; // To hold the amount to be paid if there is a winning hand
	private Color infoGreen, tableGreen, winningHandWhite;
	
	public Board() 
	{
		credit = 1000; // Starts the player off with $1000
		counter = 0; //Keeps track of how many JPanels were clicked
	// Initialize cardNumbers in the constructor to avoid NullPointerException if the user tries to save a game that has not started yet.
		cardNumbers = new int[5];  
		ourWin = "---"; //starts ourWin with an empty value because no games have been played
		infoGreen = new Color(0,167,34); //lighter green 
		tableGreen = new Color(0,135,0); //darker green for playing area
		winningHandWhite = new Color(255,255, 255); //white letters to show winning hand
		
		//whole GUI
		pokerTable = this;
		setLayout(new BorderLayout());
		
		//cardPanel
		cardPanel = new JPanel();
		cardPanel.setLayout(new GridLayout(1,5)); //one row, five columns
		cardPanel.setBackground(tableGreen);
		underneathDisplay = new JPanel[5]; //JPanel that holds each JLabel
		display = new JLabel[5];
		displayStarters(); //show the back of the cards to begin the game
		
		//betPanel
		betPanel = new JPanel();
		betPanel.setLayout(new FlowLayout());
		betLabel = new JLabel("Bet: $");
		betLabel.setFont(new Font("Ariel", Font.BOLD, 18));
		betField = new JTextField(5);
		betField.setText("10"); // Starts the game with the minimum bet amount permitted
		betField.setFont(new Font("Ariel", Font.BOLD, 18));
		betPanel.add(betLabel);
		betPanel.add(betField);
		betPanel.setBackground(infoGreen);
		
		//draw button area
		drawPanel = new JPanel();
		drawPanel.setLayout(new GridLayout(1,5)); //one row, five columns
		drawButton = new JButton("Draw"); //draw button to draw new cards
		drawButton.setFont(new Font("Ariel", Font.BOLD, 18));
		ourWinLabel = new JLabel(); //winning hand label
		ourWinLabel.setForeground(winningHandWhite);
		ourWinLabel.setFont(new Font("Ariel", Font.BOLD, 18));
		drawButton.addActionListener(this);
		JPanel blankPanel = new JPanel();
		blankPanel.setBackground(infoGreen);
		drawPanel.add(blankPanel); //blank panel
		drawPanel.add(betPanel);//bet Panel
		drawPanel.add(drawButton); //add button in the middle of the panel
		JPanel blankPanel2 = new JPanel();
		blankPanel2.setBackground(infoGreen);
		drawPanel.add(blankPanel2); //blank panel
		drawPanel.add(ourWinLabel); //winning hand
		drawPanel.setBackground(infoGreen);
		
		//playing area panel
		playingAreaPanel = new JPanel();
		playingAreaPanel.setLayout(new BorderLayout());
		playingAreaPanel.add(cardPanel, BorderLayout.CENTER);
		playingAreaPanel.add(drawPanel, BorderLayout.SOUTH);
		playingAreaPanel.setBackground(infoGreen);
		
		//Save and Load button area 
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		saveButton = new JButton("Save Game");
		saveButton.setFont(new Font("Ariel", Font.BOLD, 18));
		saveButton.addActionListener(this);
		loadButton = new JButton("Load Game");
		loadButton.setFont(new Font("Ariel", Font.BOLD, 18));
		loadButton.addActionListener(this);
		buttonPanel.add(saveButton);
		buttonPanel.add(loadButton);
		buttonPanel.setBackground(infoGreen);
		
		//statsPanel
		statsPanel = new JPanel();
		statsPanel.setLayout(new FlowLayout()); 
		creditLabel = new JLabel("Credit: $");
		creditLabel.setFont(new Font("Ariel", Font.BOLD, 16));
		creditValue = new JLabel("1000");
		creditValue.setFont(new Font("Ariel", Font.BOLD, 16));
		playedLabel = new JLabel("Games Played:");
		playedLabel.setFont(new Font("Ariel", Font.BOLD, 16));
		playedValue = new JLabel("0");
		playedValue.setFont(new Font("Ariel", Font.BOLD, 16));
		wonLabel = new JLabel("Games Won:");
		wonLabel.setFont(new Font("Ariel", Font.BOLD, 16));
		wonValue = new JLabel("0");
		wonValue.setFont(new Font("Ariel", Font.BOLD, 16));
		lostLabel = new JLabel("Games Lost:");
		lostLabel.setFont(new Font("Ariel", Font.BOLD, 16));
		lostValue = new JLabel("0");
		lostValue.setFont(new Font("Ariel", Font.BOLD, 16));
		winningsLabel = new JLabel("Winnings: $");
		winningsLabel.setFont(new Font("Ariel", Font.BOLD, 16));
		winningsValue = new JLabel("0");
		winningsValue.setFont(new Font("Ariel", Font.BOLD, 16));
		lossesLabel = new JLabel("Losses : $");
		lossesLabel.setFont(new Font("Ariel", Font.BOLD, 16));
		lossesValue = new JLabel("0");
		lossesValue.setFont(new Font("Ariel", Font.BOLD, 16));
		statsPanel.add(creditLabel);
		statsPanel.add(creditValue);
		statsPanel.add(new JLabel("        "));//blank area for seperation
		statsPanel.add(playedLabel);
		statsPanel.add(playedValue);
		statsPanel.add(new JLabel("        "));//blank area for seperation
		statsPanel.add(wonLabel);
		statsPanel.add(wonValue);
		statsPanel.add(new JLabel("        "));//blank area for seperation
		statsPanel.add(lostLabel);
		statsPanel.add(lostValue);
		statsPanel.add(new JLabel("        "));//blank area for seperation
		statsPanel.add(winningsLabel);
		statsPanel.add(winningsValue);
		statsPanel.add(new JLabel("        "));//blank area for seperation
		statsPanel.add(lossesLabel);
		statsPanel.add(lossesValue);
		statsPanel.setBackground(infoGreen);
		
		//save and load panel with stats
		slPanel = new JPanel();
		slPanel.setLayout(new BorderLayout());
		slPanel.add(statsPanel, BorderLayout.CENTER);
		slPanel.add(buttonPanel, BorderLayout.EAST);
		
		//payout chart Panel
		JPanel paychartPanel = new JPanel();
		paychartPanel.setLayout(new FlowLayout());
		JLabel chartLabel = new JLabel();
		String paychart = "<html><p>Royal Flush..........250<br>Straight Flush.......50<br>Four of a Kind.......25<br>Full House...............9<br>Flush........................6<br>Straight...................4<br>Three of a Kind......3<br>Two Pair.................2<br>Jacks or Better.....1<html>";
		paychartPanel.add(new JLabel(paychart));
		paychartPanel.setBackground(infoGreen);
		
		//payout Panel
		JPanel payoutPanel = new JPanel();
		payoutPanel.setLayout(new FlowLayout());
		JLabel payoutLabel = new JLabel("PAYOUTS :     ");
		payoutPanel.add(payoutLabel);
		payoutPanel.add(paychartPanel);
		payoutPanel.setBackground(infoGreen);
		
		//instructions Panel
		JPanel instructionsPanel = new JPanel();
		instructionsPanel.setLayout(new FlowLayout());
		instructionsPanel.add(new JLabel("INSTRUCTIONS:   "));
		String instructions = "<html><p>To begin the game, click on the 'Draw' button.<br>These drawn cards are now your hand.<br>This would be the time to change or keep the bet amount. <br>Note: Bets must be greater than or equal to $10 and less than or equal to $500.<br>If credit is less than $10, the game is over.<br>After deciding the bet amount, select the cards you would like to KEEP.<br> The cards not selected will be exchanged for new cards when you select the 'Discard' button.<br>The cards shown are your new hand and payout amounts are based on those cards.<br>To start a new game, click the 'Draw' button.<html>";
		instructionsPanel.add(new JLabel(instructions));
		instructionsPanel.setBackground(infoGreen);
		
		//top Panel
		topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1,2)); //one row, two columns
		topPanel.add(instructionsPanel);
		topPanel.add(payoutPanel);
		
		//add all the panels to the main panel
		add(topPanel, BorderLayout.NORTH);
		add(playingAreaPanel, BorderLayout.CENTER);
		add(slPanel, BorderLayout.SOUTH);
	}
	//Displays the front of the card
	public void displayCards(int [] ourHand)
	{
		for (int i =0; i<5;i++)
		{
			try
			{
				Image myImage = ImageIO.read(getClass().getResourceAsStream(ourHand[i]+".jpg"));
				icon = new ImageIcon(myImage); //determines the correct icon
			}
			catch (IOException e){}
			underneathDisplay[i] = new JPanel();
			underneathDisplay[i].setBackground(tableGreen);
			underneathDisplay[i].addMouseListener(this);
			display[i] = new JLabel(); // use array instead to know which one was selected
			display[i].setIcon(icon); //sets the correct icon to display
			underneathDisplay[i].add(display[i]); //panel for the JLabels
			cardPanel.add(underneathDisplay[i]); //panel that holds all the cards
		}
	}
	//Display the back of the cards when a game starts
	public void displayStarters()
	{
		for (int i=0;i<5;i++)
		{
		// Allow the images the .jar file to always access the image files
			try
			{
				Image myImage = ImageIO.read(getClass().getResourceAsStream("0.jpg"));
				icon = new ImageIcon(myImage); //display back of card
			} 
			catch (IOException e1){}
			underneathDisplay[i] = new JPanel();
			underneathDisplay[i].setBackground(tableGreen);
			display[i] = new JLabel(); //use array instead to know which one was selected
			display[i].setIcon(icon); //sets the correct icon to display
			underneathDisplay[i].add(display[i]); //panel for the JLabels
			cardPanel.add(underneathDisplay[i]); //panel that holds all of the cards
		}
	}
	public void mouseClicked(MouseEvent e){}
	public void mousePressed(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseReleased(MouseEvent e)
	{
		
		if (e.getClickCount()==1) //if the card was selected/deselected
		{
			JPanel selected = (JPanel)e.getSource(); //know which card was selected
			if(selected.getBorder() ==null) //no border on selected card
			{
			//Let the user know the panel is selected by colouring the border
				selected.setBorder(BorderFactory.createLineBorder(Color.yellow,3));
				counter++;
			}
			else
			{
			//If the panel is unselected, reset the border to null
				selected.setBorder(null);
				counter--;
			}
		}
	}
	public void actionPerformed(ActionEvent event)
	{
		if (event.getSource() == drawButton && drawButton.getText()=="Draw") //draw button is clicked
		{
			currentDeck = new Deck(); //make a new deck
			cardNumbers = currentDeck.getCardNumber(); //get the card number(1-52)
			cardPanel.removeAll(); //remove previous game's cards
			displayCards(cardNumbers); //display new cards
			cardPanel.validate();
			ourWin = "---"; //reset ourWin to empty
			ourWinLabel.setText(ourWin);
			drawButton.setText("Discard"); //now showing draw button
			counter = 0; // Reset the counter to 0 to allow cards to be selected in the next game.			
		}
		else if(event.getSource() == saveButton)
		{
			saveGame(); //save the current game
		}
		else if(event.getSource() == loadButton)
		{
			loadGame(); //load the previously saved game
		}
		else // If the discard button button is selected, evaluate the current hand and produce the outcome
		{
			try
			{
				bet = Integer.parseInt(betField.getText()); // Evaluate the bet first.
				if ( bet <= 500 && bet <= credit && bet>=10)
				{
					if (counter >0 && counter <5)
					{
						Random rand1 = new Random();
						for (int i=0; i<5;i++)
						{
							if(underneathDisplay[i].getBorder()==null)
							{
								int newCard; // Integer that will hold the new card drawn.
								do
								{
									newCard = rand1.nextInt(52)+1;
								}while(isInHand(newCard)); //check to see if the card is already in the current hand 
							
								try
								{
									Image newImage = ImageIO.read(getClass().getResourceAsStream(newCard + ".jpg"));
									ImageIcon icon1 = new ImageIcon(newImage); // Hold the icon associated with the new card.
									display[i].setIcon(icon1); //display correct icon for the new card
								}
								catch (IOException e2){}
								cardNumbers[i] = newCard;
								
							}
						}
					}
					else if(counter ==0)
					{
					//If the user has not selected any cards, create a new deck and replace the old one.
						currentDeck = new Deck();
						cardNumbers = currentDeck.getCardNumber();
						cardPanel.removeAll();
						displayCards(cardNumbers);
					}
					else{}
					for (int j=0;j<5;j++) //removes the border on the previously selected cards
					{
						underneathDisplay[j].setBorder(null); 
						underneathDisplay[j].removeMouseListener(this); 
					}
					String [] suits = currentSuit(cardNumbers); //suits of the current hand
					win = new WinningHands(cardNumbers, suits); // Determine the winning hand
					ourWin = win.getWinner(); // The outcome of the game
					updateGame(); //update stats of game
					drawButton.setText("Draw"); // draw button to start new game
					cardPanel.validate();
				}
				else
				{
					JOptionPane.showMessageDialog(this, "Invalid bet."); //if the number entered is <10 or >500 or greater than the credit they currently have
				}
			} catch (NumberFormatException e ){JOptionPane.showMessageDialog (this, "Please enter a valid number.");} //user didn't enter an valid number
			if(credit<10) //loser, they can't bet with the minimum bet limit
			{
				JOptionPane.showMessageDialog(this, "Your current credit balance is lower than the minimum bet amount.\nThe game is over.");
				System.exit(0); //exit the game
				
			}
		}
	}
	//Check whether the new card generated is present in the current hand
	public boolean isInHand( int x)
	{
		for(int i=0; i<5;i++)
		{
			if(x == cardNumbers[i]) // verify that you are not adding the same replacement card twice
				return true;
		}
		return false;	
	}
	// To determine the suits associated with the cards in the current hand
	public static String [] currentSuit(int [] arr)
	{
		String [] s = new String[5];
		for (int i =0; i<5;i++)
		{
			if( arr[i]>= 1 && arr[i]<=13)
			{
				s[i] = "Clubs";
			}
			else if(arr[i]>= 14 && arr[i]<=26)
			{
				s[i] = "Diamonds";
			}
			else if(arr[i]>= 27 && arr[i]<=39)
			{
				s[i] = "Hearts";
			}
			else
			{
				s[i] = "Spades";
			}
		}
		return s;
	}
	// Update all the statistics in accordance with the outcome of a game
	public void updateGame() 
	{
		if (bet <= credit && bet <=500) // Verify that the bet is valid. 
		{
			credit = credit - bet; // Decrement the credit by the amount bet
			gamesPlayed++;
			if (ourWin == "Royal Flush")
			{
				gamesWon++;				//Increment the amount of games won
				currentWin = 250 *bet; // Determine the current win
				credit+= currentWin;	// Add the winnings to the player's credit
				winnings+= currentWin;	// Keep a tally of all the winnings to this point
				creditValue.setText(String.valueOf(credit)); // Update the credit value.
				ourWinLabel.setText(ourWin);
			}
			else if (ourWin == "Straight Flush")
			{
				gamesWon++;
				currentWin+= 50 * bet;
				credit+= currentWin;
				winnings+= currentWin;
				creditValue.setText(String.valueOf(credit));
				ourWinLabel.setText(ourWin);
			}
			else if (ourWin == "Four of a Kind")
			{
				gamesWon++;
				currentWin+= 25 *bet;
				credit+= currentWin;
				winnings+= currentWin;
				creditValue.setText(String.valueOf(credit));
				ourWinLabel.setText(ourWin);
			}
			else if (ourWin == "Full House")
			{
				gamesWon++;
				currentWin+= 9* bet;
				credit+= currentWin;
				winnings+= currentWin;
				creditValue.setText(String.valueOf(credit));
				ourWinLabel.setText(ourWin);
			}
			else if (ourWin == "Flush")
			{
				gamesWon++;
				currentWin= 6 * bet;
				credit+= currentWin;
				winnings+= currentWin;
				creditValue.setText(String.valueOf(credit));
				ourWinLabel.setText(ourWin);
			}
			else if (ourWin == "Straight")
			{
				gamesWon++;
				currentWin= 4* bet;
				credit+= currentWin;
				winnings+= currentWin;
				creditValue.setText(String.valueOf(credit));
				ourWinLabel.setText(ourWin);
			}
			else if (ourWin == "Three of a Kind")
			{
				gamesWon++;
				currentWin= 3* bet;
				credit+= currentWin;
				winnings+= currentWin;
				creditValue.setText(String.valueOf(credit));
				ourWinLabel.setText(ourWin);
			}
			else if (ourWin == "Two Pair")
			{
				gamesWon++;
				currentWin= 2* bet;
				credit+= currentWin;
				winnings+= currentWin;
				creditValue.setText(String.valueOf(credit));
				ourWinLabel.setText(ourWin);
			}
			else if (ourWin == "Jacks or Better")
			{
				gamesWon++;
				currentWin= bet;
				credit+= currentWin;
				winnings+= currentWin;
				creditValue.setText(String.valueOf(credit));
				ourWinLabel.setText(ourWin);
			}
			else
			{
				creditValue.setText(String.valueOf(credit));
				gamesLost++;
				losses+= bet;
				ourWinLabel.setText(ourWin);
			}
			//Update the remaining statistics
			playedValue.setText(String.valueOf(gamesPlayed));
			wonValue.setText(String.valueOf(gamesWon));
			lostValue.setText(String.valueOf(gamesLost));
			winningsValue.setText(String.valueOf(winnings));
			lossesValue.setText(String.valueOf(losses));
		}	
	}
	public void saveGame()
	{
		try //write the info to a file
		{
			PrintWriter outputFile = new PrintWriter("savedGame.txt");
			for (int i=0; i<5;i++)
			{
				outputFile.println(cardNumbers[i]);
			}
			outputFile.println(credit);
			outputFile.println(gamesPlayed);
			outputFile.println(gamesWon);
			outputFile.println(gamesLost);
			outputFile.println(winnings);
			outputFile.println(losses);
			outputFile.println(drawButton.getText());
			outputFile.println(ourWin);
			outputFile.close();	
		}
		catch (FileNotFoundException e1){}
	}
	public void loadGame()
	{
		try //load game from a text file
		{
			Scanner scan = new Scanner (new File("savedGame.txt"));
			for (int i=0;i<5;i++)
			{
				cardNumbers[i] = scan.nextInt();
			}
			credit = scan.nextInt();
			gamesPlayed = scan.nextInt();
			gamesWon	= scan.nextInt();
			gamesLost   = scan.nextInt();
			winnings = scan.nextInt();
			losses = scan.nextInt();
			lastState = scan.next();
			ourWin = "";
			while(scan.hasNext())
			{
				ourWin += scan.next() + " ";
			}
			//update the values to match the saved file
			creditValue.setText(String.valueOf(credit));
			playedValue.setText(String.valueOf(gamesPlayed));
			wonValue.setText(String.valueOf(gamesWon));
			lostValue.setText(String.valueOf(gamesLost));
			winningsValue.setText(String.valueOf(winnings));
			lossesValue.setText(String.valueOf(losses));
			ourWinLabel.setText(ourWin);
			cardPanel.removeAll();
			displayCards(cardNumbers);
			drawButton.setText(lastState);
			cardPanel.validate();
			statsPanel.validate();
			drawPanel.validate();
		}
		catch(FileNotFoundException e2) {}
	}
}