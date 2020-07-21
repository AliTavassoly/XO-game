package xo.client.gui.util;

import java.awt.*;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class FontLoader {
    private static FontLoader instance;
    public static Map<String, Font> fontMap = new HashMap<>();

    private FontLoader() {
    }

    public static FontLoader getInstance() {
        if (instance == null) {
            return instance = new FontLoader();
        } else {
            return instance;
        }
    }

    public Font getFont(String path){
        path = "/fonts" + path;
        if (!fontMap.containsKey(path)) {
            try (InputStream in = new BufferedInputStream(
                    this.getClass().getResourceAsStream(path))) {
                Font font = Font.createFont(Font.TRUETYPE_FONT, in);
                fontMap.put(path, font);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fontMap.get(path);
    }
}
