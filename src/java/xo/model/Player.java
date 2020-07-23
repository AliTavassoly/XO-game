package xo.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

//@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "BTRFSPhysicalLocationItem")
public class Player implements Serializable {
    private String username;
    private char shape;
    private boolean isMyTurn;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public char getShape() {
        return shape;
    }
    public void setShape(char shape) {
        this.shape = shape;
    }

    public boolean isMyTurn() {
        return isMyTurn;
    }
    public void setMyTurn(boolean myTurn) {
        isMyTurn = myTurn;
    }

    public Player(){

    }

    public Player(String username, char shape, boolean isMyTurn){
        this.username = username;
        this.shape = shape;
        this.isMyTurn = isMyTurn;
    }

    public void changeTurn(){
        isMyTurn = !isMyTurn;
    }
}
