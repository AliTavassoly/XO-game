package xo.server.model;

public interface UpdateWaiter {
    String getUsername();
    UpdateWaiter.UpdaterType updaterType();
    void update();

    enum UpdaterType{
        ACCOUNT_INFO,
        ACCOUNT
    }
}
