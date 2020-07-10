package data;

import server.model.Account;
import util.XOException;

import java.util.HashMap;
import java.util.Map;

public class Data {
    private static Map<String, Account> accounts = new HashMap<>();

    public static void setAccounts(Map<String, Account> accounts) {
        Data.accounts = accounts;
    }

    public static Map<String, Account> getAccounts() {
        return accounts;
    }

    public static void checkAccount(String username, String password) throws XOException {
        if (!accounts.containsKey(username)) {
            throw new XOException("This username does not exists!");
        }
        if (!accounts.get(username).getPassword().equals(password)) {
            throw new XOException("Password is not correct!");
        }
    }

    public static void addAccount(String username, String password) throws XOException {
        if (accounts.containsKey(username)) {
            throw new XOException("This username is already exists!");
        }
        accounts.put(username, new Account(accounts.size(), username, password));
    }

    public static Account getAccount(String username) {
        return accounts.get(username);
    }
}
