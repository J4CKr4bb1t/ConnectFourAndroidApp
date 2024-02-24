package com.hfad.connectfourapp

import FourInARow
import GameConstants
import android.os.Bundle
import android.graphics.Color
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast


class BoardFragment : Fragment() {

    //game board used for back end
    val FIR_board = FourInARow()

    //current game state. playing or an end game result
    var currentState = GameConstants.PLAYING

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        val view = inflater.inflate(R.layout.fragment_board, container, false)

        //getting player name
        val playerNametxt = BoardFragmentArgs.fromBundle(requireArguments()).playerNameText

        //displaying info
        val playerNameView = view.findViewById<TextView>(R.id.playerNameView)
        val displayText = playerNametxt + "'s game- make your move!"
        playerNameView.text = displayText
        //end name display

        //listen for reset button
        val resetButton = view.findViewById<Button>(R.id.action_button)

        //define all the board space buttons in a list
        val buttonList = listOf(R.id.space0,R.id.space1,R.id.space2,R.id.space3,R.id.space4,R.id.space5,
            R.id.space6,R.id.space7,R.id.space8,R.id.space9,R.id.space10,R.id.space11,
            R.id.space12,R.id.space13,R.id.space14,R.id.space15,R.id.space16,R.id.space17,
            R.id.space18,R.id.space19,R.id.space20,R.id.space21,R.id.space22,R.id.space23,
            R.id.space24,R.id.space25,R.id.space26,R.id.space27,R.id.space28,R.id.space29,
            R.id.space30,R.id.space31,R.id.space32,R.id.space33,R.id.space34,R.id.space35,)


        resetButton.setOnClickListener {
            //code when reset button is pressed
            val duration = Toast.LENGTH_SHORT
            if(currentState != 0) //valid restart
            {
                //toast to say we're reseting
                Toast.makeText(requireContext(), "NEW GAME!", duration).show()

                //switch back to default display text
                playerNameView.text = displayText

                //for every button...
                for (buttonId in buttonList)
                {
                    val button = view.findViewById<Button>(buttonId)

                    //set each tag to blank, color to LTGRAY, and tell currentState to return to playing!
                    button.tag = "BLANK"
                    button.setBackgroundColor(Color.LTGRAY)
                    currentState = GameConstants.PLAYING
                }
                FIR_board.clearBoard() //reset board on back end
            }
            else
            {
                Toast.makeText(requireContext(), "Finish your game first!", duration).show()
            }

        } //end reset button listener

        //set logic for all of the board buttons
        for (buttonId in buttonList)
        { //check all buttons
            //current button in check loop
            val button = view.findViewById<Button>(buttonId)

            //initialize buttons as ltgray
            button.setBackgroundColor(Color.LTGRAY)


            if (currentState == GameConstants.PLAYING )
            {
                //listens for player to hit the buttons
                button.setOnClickListener{

                    //make sure to update button's state when pressed
                    val buttonSpace = resources.getResourceEntryName(buttonId)
                    val buttonSpaceNum = buttonSpace.subSequence(5, buttonSpace.length).toString()
                    val state = FIR_board.getSpace(buttonSpaceNum.toInt())

                    //if player selects blank button, we want to run through the game logic
                    //we ALSO need to check that we're still playing!
                    Log.d("ButtonState",state.toString())
                    if (state == 0 && currentState == GameConstants.PLAYING)
                    {
                        //set the button's color and tag it as belonging to the player
                        button.setBackgroundColor(Color.BLUE)
                        button.tag = "PLAYER"

                        //turn selected button into an int spaceNumber for back-end
                        val playerSpace = resources.getResourceEntryName(buttonId)
                        val playerSpaceNum = playerSpace.subSequence(5, playerSpace.length).toString()

                        //set player move on back-end
                        FIR_board.setMove(GameConstants.PLAYER, playerSpaceNum.toInt())

                        //check for winner aka update game's currentState
                        currentState = FIR_board.checkForWinner()

                        //do computer's move IF GAME STILL ON
                        if (currentState == GameConstants.PLAYING)
                        {
                            //get int of computer move (uses back end to avoid collisions
                            val compMove = FIR_board.computerMove

                            //find view associated with that board location
                            val compButtonId = resources.getIdentifier(
                                    "space$compMove",
                                    "id",
                                    requireContext().packageName
                            )
                            val compButton = view.findViewById<Button>(compButtonId)

                            //display comp move on front end (update the view)
                            compButton.tag = "COMP"
                            compButton.setBackgroundColor(Color.RED)

                            //set comp move on back end
                            FIR_board.setMove(GameConstants.COMP, compMove)

                        }//end comp move

                        //check for winner aka update game state again
                        currentState = FIR_board.checkForWinner()

                        //log currentState for debugging
                        Log.d("WinStateEndComp", currentState.toString())

                        //if current state is anything BUT playing, we want to update that now
                        if(currentState != 0)
                        {
                            Toast.makeText(requireContext(), "GAME OVER", Toast.LENGTH_SHORT).show()

                            Log.d("WinStateEndButtonCheck", currentState.toString())
                            var endString = ""

                            when(currentState)
                            {
                                    0 -> endString = playerNameView.text.toString()

                                    1 -> endString = "Ended in a TIE!!!"

                                    2 -> endString = "BLUE 4! "+playerNametxt+" WON!!!"

                                    3 -> endString = "RED 4! "+playerNametxt+" LOST!!!"
                            }

                            playerNameView.text = endString


                        }//end win display
                    }//end player's move
                    else
                    {
                       if (currentState == GameConstants.PLAYING)Toast.makeText(requireContext(), "INVALID MOVE", Toast.LENGTH_SHORT).show()
                    }
                }//end listener
            } //end if playing
        }//end check buttons

        return view
    }// end on create view
}