package xo.client.gui.panels;

import xo.client.gui.util.ImageLoader;
import xo.server.data.Configs;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class LostPanel extends JPanel {
    private static BufferedImage background;

    public LostPanel(){
        configPanel();
    }

    private void configPanel(){
        setPreferredSize(new Dimension(Configs.gameFrameWidth, Configs.gameFrameHeight));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

        if(background == null)
            background =  ImageLoader.getInstance().getImage("/lost_background.png");

        g2.drawImage(background.getScaledInstance(
                Configs.gameFrameWidth, Configs.gameFrameHeight,
                Image.SCALE_SMOOTH),
                0, 0,
                Configs.gameFrameWidth, Configs.gameFrameHeight,
                null);
    }
}
