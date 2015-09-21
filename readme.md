#Video Poker

##Introduction
This project was a group course assignment for Computer Programming II (Java). This project is based off the Jack's or Better Video Poker game, which is the most common version of video poker. The rules and payouts were based off of this [link](https://en.wikipedia.org/wiki/Video_poker#Jacks_or_Better). 

##Instructions

* To begin the game, click on the 'Draw' button. These drawn cards are now your current hand.
* Select the cards that you would like to keep in your hand. Your selected cards will have a yellow outline.
![alt text](https://github.com/ShannonAllene/Video-Poker/blob/master/Video%20Poker%20Pictures/selected%20cards.png "Selected Cards")
* Change your bet amount at this time. You begin with $1000 credit. *Note that your bet must be greater than or equal to $10 and less than or equal to $500.*
* Select the 'Discard' button to replace your discarded cards. The cards shown are your new hand.
![alt text](https://github.com/ShannonAllene/Video-Poker/blob/master/Video%20Poker%20Pictures/full%20house.png "Winning Hand")
* In the bottom-right corner, it determine if it is a winning hand. The payout amounts are based on the cards showing. *Please check the Payout section for details.*
* To start a new game, click on the 'Draw' button. 
*Note that at any point in your game you can save the hand by selecting the 'Save Game' button and reload your previously saved games by selecting the 'Load Game' button.

###Payouts

| Hand            | Payout|
|:---------------:|:------|
| Royal Flush     |	1:250 |
| Straight Flush  |	1:50  |
| Four of a Kind  |	1:25  |
| Full House      |	1:9   |
| Flush 		  |	1:6   |
| Straight	      |	1:4   |
| Three of a Kind |	1:3   |
| Two Pair		  |	1:2   |
| Jacks or Better |	1:1   |

##Notes
We realized later that using a stack would have been much more efficient in making this project. Instead we used a random generator to produce the card hands. *(Maybe a project for another day.)*

##Acknowledgements
Alix Rosarion is co-creator of this Video Poker project.
