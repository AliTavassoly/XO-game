package client;

import client.gui.GameFrame;
import data.Data;
import data.DataBase;
import server.model.Account;
import server.model.Player;

public class Main {
    public static Account currentAccount;
    public static Player currentPlayer;

    public static void login(String username, String password) throws Exception {
        Data.checkAccount(username, password);
        currentAccount = Data.getAccount(username);
    }

    public static void register(String username, String password) throws Exception {
        Data.addAccount(username, password);
        currentAccount = Data.getAccount(username);
    }

    public static void logout(String username){
        currentAccount = null;
        currentPlayer = null;
    }

    public static void main(String[] args) {
        try{
            DataBase.load();
            GameFrame.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
