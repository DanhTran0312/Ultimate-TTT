/************************************************************************
* Ultimate Tic-Tac-Toe Game
* Author: Danh Tran
* Course: CS 2336.006
************************************************************************/

interface IBoard{
    int col = 3;
    int row = 3;
    void print();
    String getMark(int row, int col);
    boolean makeMove(String player,int row, int col);
    void setSize(int row, int col);
    int getColSize();
    int getRowSize();
    String getName();
    boolean isFull();
    Board getBoard(int row, int col);
    boolean hasWinner();
}
