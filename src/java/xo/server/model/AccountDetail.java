package xo.server.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import xo.model.Account;
import xo.server.ClientHandler;
import xo.server.logic.Game;

@JsonIgnoreProperties(value = { "currentGame", "clientHandler"})

public class AccountDetail {
    private Account account;
    private Game currentGame;
    private ClientHandler clientHandler;

    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }

    public Game getCurrentGame() {
        return currentGame;
    }
    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }
    public void setClientHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public AccountDetail(){ }

    public AccountDetail(Account account, Game currentGame, ClientHandler clientHandler){
        this.account = account;
        this.currentGame = currentGame;
        this.clientHandler = clientHandler;
    }
}
