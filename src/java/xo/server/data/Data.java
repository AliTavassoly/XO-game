package xo.server.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import xo.model.Account;
import xo.server.model.AccountDetails;
import xo.util.AbstractAdapter;
import xo.util.XOException;

import java.util.HashMap;
import java.util.Map;

public class Data {
    private static Map<String, AccountDetails> accounts = new HashMap<>();

    public static void setAccounts(Map<String, AccountDetails> accounts) {
        Data.accounts = accounts;
    }

    public static Map<String, AccountDetails> getAccounts() {
        return accounts;
    }

    public static void checkAccount(String username, String password) throws XOException {
        if (!accounts.containsKey(username)) {
            throw new XOException("This username does not exists!");
        }
        if (!accounts.get(username).getAccount().getPassword().equals(password)) {
            throw new XOException("Password is not correct!");
        }
    }

    public static void addAccount(String username, String password) throws XOException {
        if (accounts.containsKey(username)) {
            throw new XOException("This username is already exists!");
        }
        accounts.put(username, new AccountDetails(new Account(accounts.size(), username, password), null, null));
    }

    public static AccountDetails getAccountDetails(String username) {
        return accounts.get(username);
    }

    public synchronized static Gson getGson(){
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(Object.class, new AbstractAdapter<>());

        gsonBuilder.setPrettyPrinting();

        Gson gson = gsonBuilder.create();

        return gson;
    }

    public synchronized static ObjectMapper getDataMapper(){
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        return mapper;
    }

    public synchronized static ObjectMapper getNetworkMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.EVERYTHING);
        return mapper;
    }
}
