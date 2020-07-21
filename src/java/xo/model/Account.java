package xo.model;

public class Account {
    private int id;

    private String username;
    private String password;

    private int totalGames;
    private int winGames;
    private int lostGames;
    private int point;

    public Account(int id, String username, String password) {
        this.id = id;

        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getTotalGames() {
        return totalGames;
    }
    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
    }

    public int getWinGames() {
        return winGames;
    }
    public void setWinGames(int winGames) {
        this.winGames = winGames;
    }

    public int getLostGames() {
        return lostGames;
    }
    public void setLostGames(int lostGames) {
        this.lostGames = lostGames;
    }

    public int getPoint() {
        return point;
    }
    public void setPoint(int point) {
        this.point = point;
    }

    public void lostGame(){
        totalGames++;
        lostGames++;
    }

    public void wonGame(){
        totalGames++;
        winGames++;
    }
}
