package util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class ImageLoader {
    private static ImageLoader instance;
    public static Map<String, BufferedImage> imageMap = new HashMap<>();

    private ImageLoader() {
    }

    public static ImageLoader getInstance() {
        if (instance == null) {
            return instance = new ImageLoader();
        } else {
            return instance;
        }
    }

    public BufferedImage getImage(String path) {
        if (!imageMap.containsKey(path)) {
            try {
                imageMap.put(path, ImageIO.read(this.getClass().getResourceAsStream(path)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return imageMap.get(path);
    }
}
