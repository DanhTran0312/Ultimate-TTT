public class UltimateBoard implements IBoard{
    private Board[][] boardBox;
    private int col; // constant colunm number of the board
    private int  row; // constant row number of the board
    private String name; // name of the board

    // Constructor that takes 3 parameters
    UltimateBoard(){
        this.setSize(3, 3);
        name = "Ultimate TicTacToe Board";
        boardBox = new Board[this.row][this.col];
        initBoard(); // initialize boxes in the board
    }

    private void initBoard(){
        for(int i = 0;i<boardBox.length;i++){
            for(int j = 0;j<boardBox[i].length;j++){
                boardBox[i][j] = new Board(row,col,"Board " +(i+j)); // create a new Box object for each box in the array.
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
        for(int row = 0; row<3; row++){// fix this
            for(int bRow = 0;bRow<boardBox.length;bRow++){
                for(int bCol = 0;bCol<boardBox[bRow].length;bCol++){
                    for(int col = 0;col< 3;col++){ // fix this
                        output += col==0?"| ":"";
                        output += (boardBox[bRow][bCol].getBox(row, col).getValue() + " ");
                    }
                    if(bCol+1 == this.col)
                        output += "|\n"; // return when reach the end of the row
                    else
                        output += "|"; // adding a space after a value
                }

            }
            if(row !=2)
                output+= "|―――――――――――――――――――――――――|\n";
        }
        System.out.println(output);
    }

    public String getMark(int row, int col){
        return boardBox[row][col].getBoardMark();
    }

    public boolean makeMove(String player, int row, int col){
        return boardBox[row][col].setBoardMark(player);
    }

    public boolean isFull(){
        for(int i = 0;i<boardBox.length;i++){
            for(int j = 0;j<boardBox[i].length;j++){
                if(!boardBox[i][j].isFull())
                    return false;
            }
        }
        System.out.println("Tie Game");
        return true;
    }
}
