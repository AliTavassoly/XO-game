package xo.data;

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

    static public void setConfigs(Map<String, Object> configs) {
        boardRows = ((Double) configs.get("boardRows")).intValue();
        boardCols = ((Double) configs.get("boardCols")).intValue();

        cellWidth = ((Double) configs.get("cellWidth")).intValue();
        cellHeight = ((Double) configs.get("cellHeight")).intValue();

        gameFrameWidth = ((Double) configs.get("gameFrameWidth")).intValue();
        gameFrameHeight = ((Double) configs.get("gameFrameHeight")).intValue();

        shapeInGameWidth = ((Double) configs.get("shapeInGameWidth")).intValue();
        shapeInGameHeight = ((Double) configs.get("shapeInGameHeight")).intValue();
    }
}