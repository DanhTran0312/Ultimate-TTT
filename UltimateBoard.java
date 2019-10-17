public class UltimateBoard implements IBoard{
    Board[][] boardBox;
    private int col; // constant colunm number of the board
    private int  row; // constant row number of the board
    private String name; // name of the board
    private boolean hasWinner = false;

    // Constructor that takes 3 parameters
    UltimateBoard(){
        this.setSize(3, 3);
        name = "Ultimate TicTacToe Board";
        boardBox = new Board[this.row][this.col];
        initBoard(); // initialize boxes in the board
    }

    public boolean hasWinner(){
        return this.hasWinner;
    }

    private void initBoard(){
        int counter = 0;
        for(int i = 0;i<boardBox.length;i++){
            for(int j = 0;j<boardBox[i].length;j++){
                boardBox[i][j] = new Board(row,col,"Board " +counter++); // create a new Box object for each box in the array.
            }
        }
    }

    public int getColSize(){
        return this.col;
    }


    public int getRowSize(){
        return this.row;
    }


    public String getName(){
        return this.name;
    }


    public void setSize(int row, int col){
        this.row = row;
        this.col = col;
    }


    public void print(){
        String output = "";
        for(int row = 0; row<this.row*this.col; row++){// fix this
            for(int bCol = 0;bCol<boardBox[row%3].length;bCol++){
                for(int col = 0;col< 3;col++){ // fix this
                    output += col==0?"| ":"";
                    output += (boardBox[row/3][bCol].getBox(row%3, col).getValue() + " ");
                }
                if(bCol+1 == this.col)
                    output += "|\n"; // return when reach the end of the row
                else
                    output += "|"; // adding a space after a value
            }
            if((row+1)%3 == 0 && row != (this.row*this.col)-1)
                output+= "|-------------------------|\n";
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
