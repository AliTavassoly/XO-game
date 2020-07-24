package xo.server.model;

import xo.client.Mapper;
import xo.server.ClientHandler;
import xo.server.XOServer;

public class AccountInfoUpdater implements UpdateWaiter {
    private ClientHandler clientHandler;
    private UpdateWaiter.UpdaterType updaterName;
    private String username;

    public AccountInfoUpdater(String username, UpdateWaiter.UpdaterType updaterName, ClientHandler clientHandler) {
        this.username = username;
        this.updaterName = updaterName;
        this.clientHandler = clientHandler;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public UpdateWaiter.UpdaterType updaterType() {
        return updaterName;
    }

    @Override
    public void update() {
        XOServer.getInstance().updateAccountInfo(clientHandler);
    }
}
