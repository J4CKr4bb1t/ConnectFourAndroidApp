/*
 * Completed by SeSe Allerheiligen
 * Due Feb 1st
 * Assignment 1
* */

import java.util.Scanner;
val FIR_board = FourInARow()

/** The entry main method (the program starts here)  */
fun main() {

 var currentState: Int = GameConstants.PLAYING
 var userInput = ""
 val HUMAN = 1;
 val COMP = 2;

 //game loop
 do {
  /**
   * 1- accept user move
   * 2- call getComputerMove
   * 3- Check for winner
   * 4- Print game end messages in case of Win , Lose or Tie !
   */

  FIR_board.printBoard()

 //take player move
  var needMove:Boolean = true;
  while(needMove)
  {
   println("playerMove")
   val scanner = Scanner(System.`in`)
   println("Please enter your move: [0,35]");
   userInput = scanner.nextLine()
   userInput = userInput.toString();

   if (userInput == "q")
   {
    break;
   }

   if (userInput.toInt() in 0..35)
   {
    needMove = false;
    FIR_board.setMove(HUMAN,userInput.toInt())
   }

  }//player move has been taken

  currentState = FIR_board.checkForWinner()
  if (currentState != 0) break;


  //computer takes its turn
  FIR_board.setMove(COMP, FIR_board.computerMove);


  //check for winner
  currentState = FIR_board.checkForWinner()


 } while (currentState == GameConstants.PLAYING && userInput != "q")
// repeat if not game-over
 var endString = ""


 FIR_board.printBoard()

 when(currentState)
 {
  0 -> endString = "Player quit..."

  1 -> endString = "Ended in a TIE!!!"

  2 -> endString = "(B) PLAYER WON!!!"

  3 -> endString = "(R) COMPUTER WON!!!"
 }

 println("GAME OVER! \n $endString")

}
 