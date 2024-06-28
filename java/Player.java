public class Player {
    //states
    private String userName;
    private int position;

    //methods
    //constructor
    public Player(String userName){
        this.userName = userName;
        this.position = 0;
    }

    //setter
    public void setUserName(String userName){
        this.userName = userName;
    }
    public void setPosition(int position){
        this.position = position;
    }

    //getter
    public String getUserName(){
        return userName;
    }

    public int getPosition(){
        return position;
    }

    //rolldice method
    public int rollDice(){
        return (int) (Math.random()*6)+1;
    }

    //move around method
    public void moveAround(int x, int boardSize){
        if(this.position + x > boardSize)
            this.position = boardSize - ((this.position + x) % boardSize);
        else this.position += x;
    }
}
