public class Box{
    private int row; // the row index of the box
    private int col; // the column index of the box
    private final static String DASH = "-";
    private String value = DASH; // the value inside the box

    boolean isEmpty(){
        return this.value.equals(Box.DASH);
    }
    Box(int row, int col){
        this.row = row;
        this.col = col;
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
