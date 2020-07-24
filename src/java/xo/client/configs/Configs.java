package xo.client.configs;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Configs {
    public static String dataPath = "data";

    public static int boardRows;
    public static int boardCols;

    public static int gameFrameWidth;
    public static int gameFrameHeight;

    public static int cellWidth;
    public static int cellHeight;

    public static int shapeInGameWidth;
    public static int shapeInGameHeight;

    public static int scoreBoardListPanelWidth;
    public static int scoreBoardListPanelHeight;

    public static int winCondition;

    public static void setConfigs(Map<String, Integer> configs) {
        boardRows = configs.get("boardRows");
        boardCols = configs.get("boardCols");

        cellWidth = configs.get("cellWidth");
        cellHeight = configs.get("cellHeight");

        gameFrameWidth = configs.get("gameFrameWidth");
        gameFrameHeight = configs.get("gameFrameHeight");

        shapeInGameWidth = configs.get("shapeInGameWidth");
        shapeInGameHeight = configs.get("shapeInGameHeight");

        scoreBoardListPanelWidth = configs.get("scoreBoardListPanelWidth");
        scoreBoardListPanelHeight = configs.get("scoreBoardListPanelHeight");

        winCondition = configs.get("winCondition");
    }

    private static Map<String, Integer> getConfigs() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(dataPath + "/game_configs.json");
        file.getParentFile().mkdirs();
        file.createNewFile();
        return mapper.readValue(file, new TypeReference<HashMap<String, Integer>>() {});
    }

    public static void loadConfigs() throws Exception{
        var gameConfigs = getConfigs();
        Configs.setConfigs(gameConfigs);
    }
}
