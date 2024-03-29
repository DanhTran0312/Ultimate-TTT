/************************************************************************
* Ultimate Tic-Tac-Toe Game
* Author: Danh Tran
* Course: CS 2336.006
************************************************************************/

public class Box{
    private int row; // the row index of the box
    private int col; // the column index of the box
    private final static String DASH = "-";
    private String value = DASH; // the value inside the box

    // Return true if the box is
    public boolean isEmpty(){
        return this.value.equals(Box.DASH) || isDigit(this.value);
    }

    // Check if an input is a digit or not
    private boolean isDigit(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
    return true;
}

    // Constructor
    Box(int row, int col){
        this.row = row;
        this.col = col;
    }

    // Constructor
    Box(int row, int col, String value){
        this.row = row;
        this.col = col;
        this.value = value;
    }

    // return the row index of the box
    int getRow(){
        return this.row;
    }

    // return the column index of the box
    int getCol(){
        return this.col;
    }

    // set a value/placeholder for the box
    boolean setValue(String s){
        if(isEmpty()){
            this.value = s;
            TTTGame.turn = false;
            return true;
        }
        else
            return false;
    }

    // get the value stored in the box
    String getValue(){
        return this.value;
    }

    // print out the value stored in the box
    void printValue(){
        System.out.println(value);
    }
}
