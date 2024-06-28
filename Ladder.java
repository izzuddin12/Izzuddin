public class Ladder {
    private int fromPosition;
    private int toPosition;

    public Ladder(int x, int y){
        this.fromPosition = x;
        this.toPosition = y;
    }

    public void setFromPosition(int x){
        this.fromPosition = x;
    }

    public void setToPosition(int y){
        this.toPosition = y;
    }

    public int getFromPosition(){
        return fromPosition;
    }

    public int getToPosition(){
        return toPosition;
    }
}
