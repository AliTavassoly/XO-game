package xo.server.data;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import xo.model.Account;
import xo.server.model.AccountDetail;
import xo.util.AbstractAdapter;
import xo.util.XOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Data {
    private static Map<String, AccountDetail> accountsDetails = new HashMap<>();

    public static void setAccountsDetails(Map<String, AccountDetail> accountsDetails) {
        Data.accountsDetails = accountsDetails;
    }

    public static Map<String, AccountDetail> getAccountsDetails() {
        return accountsDetails;
    }

    public static void checkAccount(String username, String password) throws XOException {
        if (!accountsDetails.containsKey(username)) {
            throw new XOException("This username does not exists!");
        }
        if (!accountsDetails.get(username).getAccount().getPassword().equals(password)) {
            throw new XOException("Password is not correct!");
        }
    }

    public static void addAccountDetail(String username, String password) throws XOException {
        if (accountsDetails.containsKey(username)) {
            throw new XOException("This username is already exists!");
        }
        accountsDetails.put(username, new AccountDetail(new Account(accountsDetails.size(), username, password), null, null));
    }

    public static AccountDetail getAccountDetails(String username) {
        return accountsDetails.get(username);
    }

    public static ArrayList<AccountDetail> getAccountDetails() {
        ArrayList<AccountDetail> accounts = new ArrayList<>();
        for(AccountDetail accountDetail: accountsDetails.values()){
            accounts.add(accountDetail);
        }
        return accounts;
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
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper;
    }

    public synchronized static ObjectMapper getNetworkMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.EVERYTHING);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper;
    }
}
