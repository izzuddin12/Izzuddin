public class Snake {
    private int fromPosition;
    private int toPosition;

    public Snake(int from, int to) {
        this.fromPosition = from;
        this.toPosition = to;
    }
    public void setFromPosition(int fromPosition){
        this.fromPosition = fromPosition;
    }
    public void setToPosition(int toPosition){
        this.toPosition = toPosition;
    }
    public int getFromPosition(){
        return fromPosition;
    }
    public int getToPosition(){
        return toPosition;
    }
}
