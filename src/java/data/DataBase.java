package data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import server.model.Account;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Map;

public class DataBase {
    public static Gson gson;
    public static String dataPath = "./data";

    private static Map<String, Object> getConfigs() throws Exception {
        File json = new File(dataPath + "/game_configs.json");
        json.getParentFile().mkdirs();
        json.createNewFile();
        FileReader fileReader = new FileReader(dataPath + "/game_configs.json");
        return gson.fromJson(fileReader, new TypeToken<Map<String, Object>>() {
        }.getType());
    }

    private static void loadConfigs() throws Exception {
        var gameConfigs = getConfigs();
        Configs.setConfigs(gameConfigs);
    }

    private static Map<String, Account> getAccounts() throws Exception {
        File json = new File(dataPath + "/accounts.json");
        json.getParentFile().mkdirs();
        json.createNewFile();
        FileReader fileReader = new FileReader(dataPath + "/accounts.json");
        Map<String, Account> ans = gson.fromJson(fileReader, new TypeToken<Map<String, Account>>() {
        }.getType());
        if (ans == null)
            return Data.getAccounts();
        return ans;
    }


    private static void loadAccounts() throws Exception{
        Data.setAccounts(getAccounts());
    }

    private static void saveAccounts() throws Exception {
        FileWriter fileWriter = new FileWriter(dataPath + "/accounts.json");
        gson.toJson(Data.getAccounts(),
                new TypeToken<Map<Integer, Account>>() {}.getType(), fileWriter);
        fileWriter.flush();
        fileWriter.close();
    }

    public static void save() throws Exception{
        saveAccounts();
    }

    public static void load() throws Exception {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        gson = gsonBuilder.create();

        loadConfigs();
        loadAccounts();
    }
}
