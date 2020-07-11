package server.model;

public class Player {
    private Character shape;
    private Account account;

    public Player(Account account, Character shape) {
        this.account = account;
        this.shape = shape;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Character getShape() {
        return shape;
    }

    public void setShape(Character shape) {
        this.shape = shape;
    }
}
