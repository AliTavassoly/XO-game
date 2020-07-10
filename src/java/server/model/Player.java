package server.model;

public class Player {
    private String myShape;
    private Account account;

    public Player(Account account, String myShape) {
        this.account = account;
        this.myShape = myShape;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getMyShape() {
        return myShape;
    }

    public void setMyShape(String myShape) {
        this.myShape = myShape;
    }
}
