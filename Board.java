/************************************************************************
* Ultimate Tic-Tac-Toe Game
* Author: Danh Tran
* Course: CS 2336.006
************************************************************************/

public class Board implements IBoard{
    private Box boxes[][]; // boxes within the board
    private int col; // constant colunm number of the board
    private int  row; // constant row number of the board
    private String name; // name of the board
    private final static String DASH = "-";
    private String boardMark = DASH;

    // Get the current mark of the board
    public String getBoardMark(){
        return boardMark;
    }

    // Return true if there is already winner and false otherwise
    public boolean hasWinner(){
        return boardMark.equals("X") || boardMark.equals("O");
    }

    // Set the mark of the board equal to the input mark
    public boolean setBoardMark(String mark){
        if(!hasWinner()){
            boardMark = mark;
            System.out.printf("Player: %s win %s\n\n", mark, name);
            return true;
        }
        else
            return false;
    }

    public Board getBoard(int row, int col){
        return this;
    }

    // default constructor
    Board(){
        this(3,3,"2D TicTacToe Board"); // Default Setting for Board
    }

    // Constructor that takes 3 parameters
    Board(int rowNum,int colNum,String boardName){
        this.setSize(rowNum, colNum);
        name = boardName;
        boxes = new Box[this.row][this.col];
        initBoard(); // initialize boxes in the board
    }

    // Get the box at the input row and column
    public Box getBox(int row, int col){
        return boxes[row][col];
    }

    // get the column size of the board
    public int getColSize(){
        return this.col;
    }

    // get the row size of the board
    public int getRowSize(){
        return this.row;
    }

    // Get the name of the board
    public String getName(){
        return this.name;
    }

    // Set the size of the board
    public void setSize(int row, int col){
        this.row = row;
        this.col = col;
    }

    // Check if the board is full or not
    public boolean isFull(){
        if(boardMark != "F"){
            for(int i = 0;i<boxes.length;i++){
                for(int j = 0;j<boxes[i].length;j++){
                    if(boxes[i][j].isEmpty())
                        return false;
                }
            }
            if(!hasWinner())
                boardMark = "F";
        }
        return true;
    }

    // Make a move/Place a mark in a selected box
    public boolean makeMove(String player, int row, int col){
        return boxes[row][col].setValue(player);
    }

    // Get the mark of a selected box
    public String getMark(int row, int col){
        return boxes[row][col].getValue();
    }

    // Initialize the 2d array of boxes
    private void initBoard(){
        for(int i = 0;i<boxes.length;i++){
            for(int j = 0;j<boxes[i].length;j++){
                boxes[i][j] = new Box(i, j, DASH); // create a new Box object for each box in the array.
            }
        }
    }

    // print board layout
    public void print(){
        String output = ""; // storing the output
        for(int i = 0;i<boxes.length;i++){
            for(int j = 0;j<boxes[i].length;j++){
                if(j+1 == this.col)
                    output += (boxes[i][j].getValue() + "\n"); // return when reach the end of the row
                else
                    output += (boxes[i][j].getValue() + " "); // adding a space after a value
            }

        }
        System.out.print(output);
    }

}
