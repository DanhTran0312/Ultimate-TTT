/************************************************************************
* Ultimate Tic-Tac-Toe Game
* Author: Danh Tran
* Course: CS 2336.006
************************************************************************/

/*
Analysis:
Design an ultimate Tic-Tac-Toe game
A Player can only place a mark in a board that is determined by the opponent's last placed mark
If the board is full, the player get to choose the board

Design:
Create a TTTGame class that contain the Player objects and the IBoard object that the players will be played on
When the game start, randomly select a player to go first then alternate afterward
When the selected board is full, let the player/AI choose the board to be played on otherwise the board depends on the player's last move
After choosing the smaller the board to play on, choose a box within that board to place a mark
After each turn, check if there is a winner either in the smaller board then check if there is a winner in the Ultimate Board.
If there is no winner in the Ultimate Board then continue looping until the Ultimate Board is full or there is a winner


Test:
To prevent the program from crashing, check wheter or not the user is entered what he/she is supposed to
Take the input a string to prevent crashes
An input is valid if:
    -It is an integer
    -The number is within the range of the number of boards/boxes in the ultimate board
    -The number that the user chose for the board/box is still empty or still valid to be played on

Input:
"asd" --> Invalid input
"\" --> Invalid input
"10" --> Invalid input
"-1" --> Invalid input
"0" --> Valid Input
*/



import java.lang.Math; // import Java Math library
import java.util.Scanner;

public class TTTGame{
    private Player[] players = new Player[2]; // create an array of Player object
    private IBoard gameBoard;
    private int row = 3; // Default row size
    private int col = 3; // Default column size
    private static int scoreToWin = 3; //score required to win the game {Default is 3}
    private String[] marks = {"X", "O"}; // unique mark for each player
    private String name = "TicTacToe"; // name of the game
    private int currentPlayerIndex = (int) (Math.random()*2); // randomly assign the index of the person who is going first
    private int currentBoardIndex; // keep track of the board index the player will play on
    public static boolean turn = true; // determine if it's a player turn to choose a board
    private int boxIndex = 0; // previous player's box index

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
        start(); // start the game
    }

    public String getName(){
        return this.name;
    }

    private void start(){
        Scanner sc = new Scanner(System.in);
        // Starting message
        System.out.println("===== WELCOME TO THE ULTIMATE TIC-TAC-TOE GAME!! =====");
        //Print out starting player and his/her mark
        System.out.println("Starting Players: "+players[currentPlayerIndex].getName()+", Mark: "+players[currentPlayerIndex].getMark());
        while(true){ // loop until the game is over
            Player cPlayer = players[this.currentPlayerIndex]; // current player object
            // Loop until the player have found an empty box to mark
            gameBoard.print();
            // AI
            if(players[currentPlayerIndex] instanceof AI){
                System.out.println("Current Player: " + players[currentPlayerIndex].getName()+ ", Mark: "+players[currentPlayerIndex].getMark());
                System.out.print("Please select a valid board\nSelected Board: ");
                // if it's the AI turn to choose the board or if the board that will be played on is full then the AI get to choose the board
                if(turn)
                    currentBoardIndex = chooseBoard();
                // the board that the AI will play on is determined by the opponent last placed box
                else
                    currentBoardIndex = boxIndex;
                System.out.println(currentBoardIndex);
                System.out.print("Please select a valid box in " +gameBoard.getBoard(currentBoardIndex/3,currentBoardIndex%3).getName()+"\nSelected Box: ");
                // randomly select a box within the selected board until found an empty box
                do{
                    boxIndex = cPlayer.randomNumber(this.col*this.row);
                } while(!gameBoard.getBoard(currentBoardIndex/3, currentBoardIndex%3).getBox(boxIndex/3,boxIndex%3).isEmpty());
                System.out.println(boxIndex);
                gameBoard.makeMove(cPlayer.getMark(), currentBoardIndex, boxIndex); // the AI place the mark in the selected boardIndex and boxIndex
            }
            // Player


            else{
                // if it's the PLayer turn to choose the board or if the board that will be played on is full then the Player get to choose the board
                System.out.println("Current Player: " + players[currentPlayerIndex].getName()+ ", Mark: "+players[currentPlayerIndex].getMark());
                String input;
                // if is is the player turn to pick a board
                if(turn){
                    do{
                        printAvailableBoard();
                        System.out.print("Please select a valid board\nSelected Board: ");
                        input = sc.nextLine();
                    }while(!isValidBoard(input));
                    currentBoardIndex = Integer.parseInt(input);
                }
                else{
                    System.out.print("Selected Board: ");
                    currentBoardIndex = boxIndex;
                    System.out.println(currentBoardIndex);
                }
                do{
                    printAvailableBox(gameBoard.getBoard(currentBoardIndex/3, currentBoardIndex%3));
                    System.out.print("Please select a valid box in " +gameBoard.getBoard(currentBoardIndex/3,currentBoardIndex%3).getName()+"\nSelected Box: ");
                    input = sc.nextLine();
                } while(!isValidBox(input));
                boxIndex = Integer.parseInt(input);
                gameBoard.makeMove(cPlayer.getMark(), currentBoardIndex, boxIndex); // the Player place the mark in the selected boardIndex
            }


            // AI vs AI
            /*
            else{
                System.out.println("Current Player: " + players[currentPlayerIndex].getName()+ ", Mark: "+players[currentPlayerIndex].getMark());
                System.out.print("Please select a valid board\nSelected Board: ");
                // if it's the AI turn to choose the board or if the board that will be played on is full then the AI get to choose the board
                if(turn)
                    currentBoardIndex = chooseBoard();
                // the board that the AI will play on is determined by the opponent last placed box
                else
                    currentBoardIndex = boxIndex;
                System.out.println(currentBoardIndex);
                System.out.print("Please select a valid box in " +gameBoard.getBoard(currentBoardIndex/3,currentBoardIndex%3).getName()+"\nSelected Box: ");
                // randomly select a box within the selected board until found an empty box
                do{
                    boxIndex = cPlayer.randomNumber(this.col*this.row);
                } while(!gameBoard.getBoard(currentBoardIndex/3, currentBoardIndex%3).getBox(boxIndex/3,boxIndex%3).isEmpty());
                System.out.println(boxIndex);
                gameBoard.makeMove(cPlayer.getMark(), currentBoardIndex, boxIndex); // the AI place the mark in the selected boardIndex and boxIndex
            }
            */

            // check is there is a winner in the small board
            if(isWinner(gameBoard.getBoard(currentBoardIndex/3, currentBoardIndex%3)) && !gameBoard.getBoard(currentBoardIndex/3, currentBoardIndex%3).hasWinner())
                gameBoard.getBoard(currentBoardIndex/3, currentBoardIndex%3).setBoardMark(cPlayer.getMark());

            // Check if any player has won the game
            if(gameOver(gameBoard))
                break; // break the loop if a player win or result in a tie

            // Determine if the next player get to pick the board or not
            if(gameBoard.getBoard(boxIndex/3,boxIndex%3).isFull())
                turn = true;
            switchPlayer(); // switch player after the other play has played the turn.
        }

    }

    // Check if the box entered is valid
    private boolean isValidBox(String s){
        if(isDigit(s)){
            int n = Integer.parseInt(s);
            if((n <= (row*col-1) && n>=0) && gameBoard.getBoard(currentBoardIndex/3, currentBoardIndex%3).getBox(n/3,n%3).isEmpty())
                return true;
            else{
                System.out.println(s+" is not a valid box");
                return false;
            }
        }
        System.out.println(s+" is not a valid box");
        return false;
    }

    // Check if the board entered is valid
    private boolean isValidBoard(String s){
        if(isDigit(s)){
            int n = Integer.parseInt(s);
            if((n <= (row*col-1) && n>=0) && !gameBoard.getBoard(n/3,n%3).isFull())
                return true;
            else{
                System.out.println(s+" is not a valid board");
                return false;
            }
        }
        System.out.println(s+" is not a valid board");
        return false;
    }

    // Print out available boards that the players can play on
    private void printAvailableBoard(){
        System.out.println("Available Board: ");
        for(int i=0;i<row; i++){
            for(int j=0;j<col; j++){
                if(!gameBoard.getBoard(i, j).isFull())
                    System.out.print(gameBoard.getBoard(i, j).getName() +" ");
                if(i == row-1 && j == col-1)
                    System.out.println("");
            }
        }
    }

    // Print out available boxes within the selected board that the players can play on
    private void printAvailableBox(Board board){
        int n = 0;
        System.out.println("Available Box in "+board.getName()+": ");
        for(int i=0;i<board.getRowSize(); i++){
            for(int j=0;j<board.getColSize(); j++){
                if(board.getBox(i,j).isEmpty())
                    System.out.print(n+" ");
                if(i == row-1 && j == col-1)
                    System.out.println("");
                n++;
            }
        }
    }

    // check if the input is a digit
    private boolean isDigit(String s){
        try{
            Integer.parseInt(s);
            return true;
        }
        catch(NumberFormatException | NullPointerException nfe){
            return false;
        }
    }

    // choose a random board from the Ultimate Board that is not full
    private int chooseBoard(){
        int n;
        do{
            n = players[this.currentPlayerIndex].randomNumber(this.row*this.col);
        }while(gameBoard.getBoard(n/3,n%3).isFull());
        return n;
    }


    // Check if the game is over or not
    private boolean gameOver(IBoard board){
        // The game is over if there is a winner or if all the board and boxes are full
        if(isWinner(board)){
            gameBoard.print();
            System.out.println("Player: "+players[currentPlayerIndex].getMark()+" Win The Ultimate Board!!");
            return true;
        }
        else if(gameBoard.isFull()){
            gameBoard.print();
            System.out.println("Tie Game");
            return true;
        }
        return false;
    }

    // Check if there is a winner and return true if there is one
    private boolean isWinner(IBoard board){
        // Check each row for winner and return true if there is one
        if(checkRow(board))
            return true;
        // Check each column for winner and return true if there is one
        else if(checkCol(board))
            return true;
        // Check the right to left diagonal for winner and return true if there is one
        else if(checkDiagRL(board))
            return true;
        // Check the left to right diagonal for winner and return true if there is one
        else if(checkDiagLR(board))
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
        while(row<IBoard.row && col<IBoard.col){
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
        while(row<IBoard.row && col<IBoard.col){
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
        for(int row = 0;row<IBoard.col;row++){
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
        for(int row = 0;row<IBoard.col;row++){
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
        this.currentPlayerIndex = this.currentPlayerIndex == 1?0:1;
    }

    private void setBoard(){
        this.gameBoard = new Board(this.row,this.col,"TicTacToe Board"); // creating new Board object and set it as the game board
    }

    private void setBoard(IBoard board){
        this.gameBoard = board; // setting the game board to the board of the customer
    }

    // method that set the player name and unique mark
    private void setPlayers(){
        for(int i =0;i<players.length;i++){
            if(i == players.length-1)
                this.players[i]= new AI(marks[i]);
            else
                this.players[i]= new Player("Player"+(i+1), marks[i]);
        }
    }
}
