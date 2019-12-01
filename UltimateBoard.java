public class UltimateBoard implements IBoard{
    private Board[][] boardBox; // array of boards within the Ultimate Board
    private int col; // constant colunm number of the board
    private int  row; // constant row number of the board
    private String name; // name of the board
    private boolean hasWinner = false;

    // Default constructor
    UltimateBoard(){
        this.setSize(3, 3);
        name = "Ultimate TicTacToe Board";
        boardBox = new Board[this.row][this.col];
        initBoard(); // initialize boxes in the board
    }

    public boolean hasWinner(){
        return this.hasWinner;
    }

    // Initialize the boards within the Ultimate Board
    private void initBoard(){
        int counter = 0;
        for(int i = 0;i<boardBox.length;i++){
            for(int j = 0;j<boardBox[i].length;j++){
                boardBox[i][j] = new Board(row,col,"Board #" +counter++); // create a new Box object for each box in the array.
            }
        }
    }

    // get the size of the column of the board
    public int getColSize(){
        return this.col;
    }

    // get the size of the row of the board
    public int getRowSize(){
        return this.row;
    }

    // get the name of the board
    public String getName(){
        return this.name;
    }

    // set the size of the board
    public void setSize(int row, int col){
        this.row = row;
        this.col = col;
    }

    // Print the layout of the Ultimate Board row by row
    public void print(){
        String output = "";
        // loop thtough each row in the Ultimate Board
        for(int row = 0; row<this.row*this.col; row++){
            // loop through each column in the sub-board
            for(int bCol = 0;bCol<boardBox[row%3].length;bCol++){
                // loop through each column in the Ultimate Board
                for(int col = 0;col< 3;col++){
                    output += col==0?"| ":"";
                    output += (boardBox[row/3][bCol].getBox(row%3, col).getValue() + " ");
                }

                // print the simplified board
                if((bCol == boardBox[row%3].length-1) && (row >=3 && row <=5)){
                    if(row == (this.row*this.col-1)/2)
                        output += "|\tSimplified Board-->\t";
                    else
                        output+= "|\t\t\t\t";
                    for(int sCol = 0;sCol<boardBox[row%3].length;sCol++){
                        output += sCol==0?"| ":"";
                        output += (boardBox[row%3][sCol].getBoardMark()+ " ");
                    }
                }

                
                if(bCol+1 == this.col)
                    output += "|\n"; // return when reach the end of the row
            }
            if((row+1)%3 == 0 && row != (this.row*this.col)-1)
                output+= "|-----------------------|\n";
        }
        System.out.println(output);
    }

    public String getMark(int row, int col){
        return boardBox[row][col].getBoardMark();
    }

    public boolean setMark(String player, int row, int col){
        return boardBox[row][col].setBoardMark(player);
    }

    public boolean makeMove(String player, int boardNum, int boxNum){
        return boardBox[boardNum/this.col][boardNum%this.col].makeMove(player, boxNum/3, boxNum%3);
    }

    public Board getBoard(int row, int col){
        return boardBox[row][col];
    }

    public boolean isFull(){
        for(int i = 0;i<boardBox.length;i++){
            for(int j = 0;j<boardBox[i].length;j++){
                if(!boardBox[i][j].isFull())
                    return false;
            }
        }
        return true;
    }
}
