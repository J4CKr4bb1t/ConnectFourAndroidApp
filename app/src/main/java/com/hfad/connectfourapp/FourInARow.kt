/**
 * TicTacToe class implements the interface
 * @author relkharboutly
 * @date 2/12/2022
 *
 *
 * Completed by SeSe Allerheiligen
 * Due Feb 1st
 * Assignment 1
 */
import kotlin.random.Random;

class FourInARow
/**
 * clear board and set current player
 */
    : IGame {
    // game board in 2D array initialized to zeros
    private val board = Array(GameConstants.ROWS) { IntArray(GameConstants.COLS){0} }

    override fun clearBoard() {
        //visit each value and set to 0

        for (j in 0..5){//y loop code
            for (i in 0..5){//x loop code

                //set all values to 0, which is EMPTY
                board[j][i] = 0
            }
        }


    }

    override fun setMove(player: Int, location: Int) {
        //const val EMPTY = 0
        //    const val BLUE = 1
        //    const val RED = 2

        //if player Int = 1, place blue,
        //if player Int = 2, place red

        //Places the tile at the given location
        //breaks down int into x and y position
        val x = location % 6;
        var y = location / 6;

        //with gravity, comment out to remove gravity
        //Classic connect 4 has gravity,
//        for (n in 0 .. 5)
//        {
//            if (board[n][x] == 0)//move disc to lowest open spot in that column
//            {
//                y = n;
//            }
//        }

        //set tile to value equivalent to player who made the action
        board[y][x] = player;

    }

    //Decides what move the computer makes
    private fun compMove(): Int {

        //makes choice weighted towards middle
        val rando =  Random.nextInt(0, 36)

        if(board[rando/6][rando%6] == 0) return rando
        else return compMove()

    }

    //set computerMove to a value based on our Algorithm
    override val computerMove: Int
        get() =compMove()


    override fun checkForWinner(): Int {

        //progress if 0, no more moves, game over. If 1, moves exist, can still play
        //can only pick playing or tie AFTER checking all other win conditions
        var progress = 0;


        //check board for horizontal wins AND if any open tiles exist
        for (j in 0 .. 5)
        {
            var prev = 0;
            var count = 0;
            for (i in 0..5)
            {
                val curr = board[j][i]

                //checks if any open tiles exist
                if(curr == 0)
                {
                    progress = 1;
                }

                //if last space = this space, then it might be a connect-4
                if (prev == curr && curr != 0)
                {
                    count++;
                }
                else //if they're different, re-start connect streak
                {
                    count = 0;
                }

                if (count == 3)//CONNECT 4! Win (0-1-2-3 counter)
                {
                    //could be return 1 + curr, but below is easier to read
                    if(curr == 1) //red player
                    {
                        return 2; //red won
                    }
                    if(curr == 2) //blue player
                    {
                        return 3//blue won
                    }
                }
                prev = curr //set up for next loop
            }
        }

        //check for vertical wins
        for (i in 0..5)
        {
            var prev = 0;
            var count = 0;
            for (j in 0..5)
            {
                val curr = board[j][i]

                if (prev == curr && curr != 0)
                {
                    count++; //updates count if there's a streak of player discs
                }
                else
                {
                    count = 0; //sets to 0 if empty or switches val.
                }

                if (count == 3)//CONNECT 4! Win
                {
                    if(curr == 1) //red player
                    {
                        return 2; //red won
                    }
                    if(curr == 2) //blue player
                    {
                        return 3//blue won
                    }
                }
                prev = curr //sets up for next loop
            }
        }

        //check for diagonal wins

        //left to right

        var count  = 0;
        var prev = 0;
        for (n in 0..5) //checks main diagonal
        {
            val curr = board[n][n];

            if (prev == curr) count++ else count = 0;

            if (count == 3)//CONNECT 4! Win
            {
                if(curr == 1) //red player
                {
                    return 2; //red won
                }
                if(curr == 2) //blue player
                {
                    return 3//blue won
                }
            }
            prev = curr
        }

        //There's definitely a way to do this in a clever loop with a lil' toggle for direction
        // and specific column, but I couldn't figure it out.
        //Brute forcing the diagonals, since we're restricted to a 6x6 grid

        //lower wiggle 4
        if (board[2][1] == board[3][2] &&
            board[3][2]  == board[4][3] &&
            (board[3][2]  == board[5][4] || board[3][2]  == board[1][0] ))
        {
            val curr = board[3][2]

            if(curr == 1) //red player
            {
                return 2; //red won
            }
            if(curr == 2) //blue player
            {
                return 3//blue won
            }
        }

        //upper wiggle 4
        if (board[1][2] == board[2][3] &&
            board[2][3]  == board[3][4] &&
            (board[2][3]  == board[4][5] || board[2][3]  == board[0][1] ))
        {
            val curr = board[2][3]

            if(curr == 1) //red player
            {
                return 2; //red won
            }
            if(curr == 2) //blue player
            {
                return 3//blue won
            }
        }

        //check solid diag (diag on board of length 4, all must be equal to have a win)
        if (board[2][0] == board[3][1] &&
            board[3][1] == board[4][2] &&
            board[4][2] == board [5][3])
        {
            val curr = board[2][0]

            if(curr == 1) //red player
            {
                return 2; //red won
            }
            if(curr == 2) //blue player
            {
                return 3//blue won
            }
        }

        if (board[0][2] == board[1][3] &&
            board[1][3] == board[2][4] &&
            board[2][4] == board [3][5])
        {
            val curr = board[0][2]

            if(curr == 1) //red player
            {
                return 2; //red won
            }
            if(curr == 2) //blue player
            {
                return 3//blue won
            }
        }


        //right to left Diagonals

        //main diag
        count  = 0;
        prev = 0;
        for (n in 0..5)
        {
            val curr = board[n][5-n];

            if (prev == curr) count++ else count = 0;

            if (count == 3)//CONNECT 4! Win
            {
                if(curr == 1) //red player
                {
                    return 2; //red won
                }
                if(curr == 2) //blue player
                {
                    return 3//blue won
                }
            }
            prev = curr
        }

        //lower wiggle 4
        if (board[2][5-1] == board[3][5-2] &&
            board[3][5-2]  == board[4][5-3] &&
            (board[3][5-2]  == board[5][5-4] || board[3][5-2]  == board[1][5-0] ))
        {
            val curr = board[3][5-2]

            if(curr == 1) //red player
            {
                return 2; //red won
            }
            if(curr == 2) //blue player
            {
                return 3//blue won
            }
        }

        //upper wiggle 4
        if (board[1][5-2] == board[2][5-3] &&
            board[2][5-3]  == board[3][5-4] &&
            (board[2][5-3]  == board[4][5-5] || board[2][5-3]  == board[0][5-1] ))
        {
            val curr = board[2][5-3]

            if(curr == 1) //red player
            {
                return 2; //red won
            }
            if(curr == 2) //blue player
            {
                return 3//blue won
            }
        }

        //check solid diag
        if (board[2][5-0] == board[3][5-1] &&
            board[3][5-1] == board[4][5-2] &&
            board[4][5-2] == board [5][5-3])
        {
            val curr = board[2][5-0]

            if(curr == 1) //red player
            {
                return 2; //red won
            }
            if(curr == 2) //blue player
            {
                return 3//blue won
            }
        }

        if (board[0][5-2] == board[1][5-3] &&
            board[1][5-3] == board[2][5-4] &&
            board[2][5-4] == board [3][5-5])
        {
            val curr = board[0][5-2]

            if(curr == 1) //red player
            {
                return 2; //red won
            }
            if(curr == 2) //blue player
            {
                return 3//blue won
            }
        }


        //check progress
        if (progress == 0)
        {
            return 1 // TIE, no more possible moves
        }

        //default state, playing
        return 0
    }

    /**
     * Print the game board
     */
    fun printBoard() {
        println(" 0  1  2  3  4  5 ")
        for (row in 0 until GameConstants.ROWS) {
            for (col in 0 until GameConstants.COLS) {
                printCell(board[row][col]) // print each of the cells
                if (col != GameConstants.COLS - 1) {
                    //print("|") // print vertical partition
                }
            }
            println()
            if (row != GameConstants.ROWS - 1) {
                //println("----------------------") // print horizontal partition
            }
        }
        println("----------------------")
    }//end of printBoard()

    /**
     * Print a cell with the specified "content"
     * @param content either BLUE, RED or EMPTY
     */
    fun printCell(content: Int) {
        when (content) {
            GameConstants.EMPTY -> print("[ ]")
            GameConstants.BLUE ->  print(" B ")
            GameConstants.RED ->   print(" R ")
        }
    }

    fun getSpace(location: Int): Int {
        return board[location/6][location%6]
    }
}

