package xo.server.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import xo.server.model.AccountDetails;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DataBase {
    public static ObjectMapper mapper;
    public static String dataPath = "data";

    private static Map<String, Integer> getConfigs() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(dataPath + "/game_configs.json");
        file.getParentFile().mkdirs();
        file.createNewFile();
        return mapper.readValue(file, new TypeReference<HashMap<String, Integer>>() {});
    }

    private static void loadConfigs() throws Exception {
        var gameConfigs = getConfigs();
        Configs.setConfigs(gameConfigs);
    }

    private static Map<String, AccountDetails> getAccounts() throws Exception {
        File file = new File(dataPath + "/accounts.json");
        file.getParentFile().mkdirs();
        file.createNewFile();
        Map<String, AccountDetails> ans = null;
        try {
            ans = mapper.readValue(file, new TypeReference<HashMap<String, AccountDetails>>() {
            });
        } catch (Exception e){ }
        if (ans == null)
            return Data.getAccounts();
        return ans;
    }

    private static void loadAccounts() throws Exception{
        Data.setAccounts(getAccounts());
    }

    private static void saveAccounts() throws Exception {
        File file = new File(dataPath + "/accounts.json");
        mapper.writeValue(file, Data.getAccounts());
    }

    public static void save(){
        try {
            saveAccounts();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void load() throws Exception{
        mapper = Data.getDataMapper();

        loadConfigs();
        loadAccounts();
    }
}
