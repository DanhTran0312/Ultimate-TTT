import java.lang.Math; // import Java Math library

public class TTTGame{
    private Player[] players = new Player[2]; // create an array of Player object
    private IBoard gameBoard;
    private int row = 3; // Default row size
    private int col = 3; // Default column size
    private static int scoreToWin = 3; //score required to win the game {Default is 3}
    private String[] marks = {"X", "O"}; // unique mark for each player
    private String name = "TicTacToe"; // name of the game
    private int currentPlayerIndex = (int) (Math.random()*2); // randomly assign the index of the person who is going first
    private int currentBoardIndex;
    public static int turn = 0;

    // Game constructor
    public TTTGame(){
        setBoard(); // create a Board object based on the rows and columns with the name "1D TicTacToe Board"
        setPlayers(); // initialize each player in the game
        start(); // start the game
    }

    // Game constructor
    public TTTGame(IBoard board){
        this.row = board.getRowSize();
        this.col = board.getColSize();
        scoreToWin = row;
        setBoard(board); // create a Board object based on the rows and columns with the name "TTT Board"
        setPlayers(); // initialize each player in the game
        currentBoardIndex = chooseBoard();
        start(); // start the game
    }

    public String getName(){
        return this.name;
    }

    private void start(){
        // Starting message
        System.out.println("Game started ... ");
        // Print Board Info
        System.out.println("Board: " + this.gameBoard.getName());
        //Print out starting player and his/her mark
        System.out.println("Starting Players: "+players[currentPlayerIndex].getName()+", Mark: "+players[currentPlayerIndex].getMark());
        while(true){ // loop until the game is over
            Player cPlayer = players[this.currentPlayerIndex]; // current player object
            // Loop until the player have found an empty box to mark
            // TODO: change the while logic

            while(!gameBoard.makeMove(cPlayer.getMark(), currentBoardIndex,cPlayer.randomNumber(this.col*this.row)));

            // display the game board after a player had marked
            gameBoard.print();
            System.out.println("");

            UltimateBoard tmpBoard = (UltimateBoard) gameBoard;
            gameBoard = tmpBoard.getBoard(currentBoardIndex/3, currentBoardIndex%3);

            if(isWinner() && !gameBoard.hasWinner())
                tmpBoard.setMark(players[currentPlayerIndex].getMark(), currentBoardIndex/3, currentBoardIndex%3);

            gameBoard = tmpBoard;
            // Check if any player has won the game
            if(gameOver())
                break; // break the loop if a player win or resulted in a tie
            if(turn%2==0 || gameBoard.getBoard(currentBoardIndex/3,currentBoardIndex%3).isFull()){
                turn = 0;
                currentBoardIndex = chooseBoard();
            }
            switchPlayer(); // switch player after the other play has played the turn.
        }

    }


    public int chooseBoard(){
        int n;
        do{
            n = players[this.currentPlayerIndex].randomNumber(this.row*this.col);
        }while(gameBoard.getBoard(n/3,n%3).isFull());

        return n;
    }


    // Check if the game is over or not
    private boolean gameOver(){
        if(isWinner()){
            System.out.println(players[currentPlayerIndex].getMark()+" Win!!!");
            return true;
        }
        else if(gameBoard.isFull()){
            System.out.println("Tie Game");
            return true;
        }
        return false;
    }

    // Check if there is a winner and return true if there is one
    private boolean isWinner(){
        // Check each row for winner and return true if there is one
        if(checkRow(gameBoard))
            return true;
        // Check each column for winner and return true if there is one
        else if(checkCol(gameBoard))
            return true;
        // Check the right to left diagonal for winner and return true if there is one
        else if(checkDiagRL(gameBoard))
            return true;
        // Check the left to right diagonal for winner and return true if there is one
        else if(checkDiagLR(gameBoard))
            return true;
        // If there is no winner return false
        else
            return false;
    }

    // Check each row for winner
    private boolean checkRow(IBoard board){
        int row = 0; // row index
        int col = 0; // column index
        int count = 0; // mark counter
        // Keep looping while the row index and the column index is less than the size of the board
        while(row<board.row && col<board.col){
            // check if the current box mark is the same as the player's mark
            if(board.getMark(row, col).equals(players[currentPlayerIndex].getMark())){
                count++; // increase mark counter by one
                col++; // increase the column index by one
                if (count == scoreToWin) return true; // if the mark counter is equal to the minimum score to win then return true --> there is a winner
            }
            // if the box current mark is not the same as the player's mark then move on to the next row
            else{
                row++; // increase row index by one
                col = 0; // reset column index
                count = 0; // reset mark counter
            }
        }
        return false; // return false if no player have win the game
    }

    // check each column for winner
    private boolean checkCol(IBoard board){
        int row = 0; // row index
        int col = 0; // column index
        int count = 0; // mark counter
        // Keep looping while the row index and the column index is less than the size of the board
        while(row<board.row && col<board.col){
            // check if the current box mark is the same as the player's mark
            if(board.getMark(row, col).equals(players[currentPlayerIndex].getMark())){
                count++; // increase mark counter by one
                row++; // increase the column index by one
                if (count == scoreToWin) return true; // if the mark counter is equal to the minimum score to win then return true --> there is a winner
            }
            // if the box current mark is not the same as the player's mark then move on to the next row
            else{
                col++; // increase row index by one
                row = 0; // reset column index
                count = 0; // reset mark counter
            }
        }
        return false; // return false if no player have win the game
    }

    // Check the diagonal line going from the right to the left
    private boolean checkDiagRL(IBoard board){
        int col = this.col-1; // set the culumn index to be at the top right corner of the board
        int count = 0; // mark counter
        // loop size times
        for(int row = 0;row<board.col;row++){
            // check if the current box mark is the same as the player's mark
            if(board.getMark(row, col).equals(players[currentPlayerIndex].getMark())){
                count++; // increase mark counter by one
                col--; // // decrease column index by one
                if (count == scoreToWin) return true; // if the mark counter is equal to the minimum score to win then return true --> there is a winner
            }
            else
                break; // if the current box mark is not the same as the player's mark then break the loop and return false
        }
        return false;
    }

    // Check the diagonal line going from the left to the right
    private boolean checkDiagLR(IBoard board){
        int col = 0; // set the culumn index to be at the top left corner of the board
        int count = 0; // mark counter
        for(int row = 0;row<board.col;row++){
            // check if the current box mark is the same as the player's mark
            if(board.getMark(row, col).equals(players[currentPlayerIndex].getMark())){
                count++; // increase mark counter by one
                col++; // // increase column index by one
                if (count == scoreToWin) return true; // if the mark counter is equal to the minimum score to win then return true --> there is a winner
            }
            else
                break; // if the current box mark is not the same as the player's mark then break the loop and return false
        }
        return false;
    }

    // Alternate the index of players 1 --> 0 or 0 --> 1
    private void switchPlayer(){
        if(this.currentPlayerIndex == 1)
            this.currentPlayerIndex = 0;
        else
            this.currentPlayerIndex = 1;
    }

    private void setBoard(){
        this.gameBoard = new Board(this.row,this.col,"1D TicTacToe Board"); // creating new Board object and set it as the game board
    }

    private void setBoard(IBoard board){
        this.gameBoard = board; // setting the game board to the board of the customer
    }

    // method that set the player name and unique mark
    private void setPlayers(){
        for(int i =0;i<players.length;i++){
            this.players[i]= new Player("Player"+(i+1), marks[i]);
        }
    }
}
