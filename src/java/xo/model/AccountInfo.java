package xo.model;

public class AccountInfo implements Comparable<AccountInfo>{
    private String username;
    private boolean isOnline;
    private int point;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isOnline() {
        return isOnline;
    }
    public void setOnline(boolean online) {
        isOnline = online;
    }

    public int getPoint() {
        return point;
    }
    public void setPoint(int point) {
        this.point = point;
    }

    public AccountInfo(){
    }

    public AccountInfo(String username, int point, boolean isOnline){
        this.username = username;
        this.point = point;
        this.isOnline = isOnline;
    }

    public status getStatus(){
        if(isOnline)
            return status.ONLINE;
        return status.OFFLINE;
    }

    @Override
    public int compareTo(AccountInfo accountInfo) {
        if(this.getPoint() != accountInfo.getPoint()){
            if(this.getPoint() > accountInfo.getPoint())
                return -1;
            else
                return 1;
        } else {
            return 0;
        }
    }

    public enum status{
        ONLINE,
        OFFLINE
    }
}
