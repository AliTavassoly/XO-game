package model;

public class Player {
    private String username;
    private Character shape;
    private boolean isMyTurn;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public Character getShape() {
        return shape;
    }
    public void setShape(Character shape) {
        this.shape = shape;
    }

    public boolean isMyTurn() {
        return isMyTurn;
    }
    public void setMyTurn(boolean myTurn) {
        isMyTurn = myTurn;
    }

    public Player(String username, Character shape, boolean isMyTurn){
        this.username = username;
        this.shape = shape;
        this.isMyTurn = isMyTurn;
    }

    public void changeTurn(){
        isMyTurn = !isMyTurn;
    }
}
