package xo.server.data;

import java.util.Map;

public class Configs {
    public static int boardRows;
    public static int boardCols;

    public static int gameFrameWidth;
    public static int gameFrameHeight;

    public static int cellWidth;
    public static int cellHeight;

    public static int shapeInGameWidth;
    public static int shapeInGameHeight;

    static public void setConfigs(Map<String, Integer> configs) {
        /*boardRows = configs.get("boardRows");
        boardCols = ((Double) configs.get("boardCols")).intValue();

        cellWidth = ((Double) configs.get("cellWidth")).intValue();
        cellHeight = ((Double) configs.get("cellHeight")).intValue();

        gameFrameWidth = ((Double) configs.get("gameFrameWidth")).intValue();
        gameFrameHeight = ((Double) configs.get("gameFrameHeight")).intValue();

        shapeInGameWidth = ((Double) configs.get("shapeInGameWidth")).intValue();
        shapeInGameHeight = ((Double) configs.get("shapeInGameHeight")).intValue();*/
        boardRows = configs.get("boardRows");
        boardCols = configs.get("boardCols");

        cellWidth =  configs.get("cellWidth");
        cellHeight = configs.get("cellHeight");

        gameFrameWidth =  configs.get("gameFrameWidth");
        gameFrameHeight =  configs.get("gameFrameHeight");

        shapeInGameWidth = configs.get("shapeInGameWidth");
        shapeInGameHeight =  configs.get("shapeInGameHeight");
    }
}