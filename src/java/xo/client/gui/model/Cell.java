package xo.client.gui.model;

import xo.client.gui.xocontrols.XOButton;
import xo.server.data.Configs;
import xo.client.gui.util.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Cell extends XOButton {
    private final int row, col;
    private final int width, height;
    private char value;

    private static BufferedImage xImage;
    private static BufferedImage oImage;

    public Cell(int row, int col){
        this.row = row;
        this.col = col;

        width = Configs.cellWidth;
        height = Configs.cellHeight;
        value = '.';

        loadImages();

        configButton();
    }

    private void loadImages() {
        if(xImage == null)
            xImage = ImageLoader.getInstance().getImage("/X.png");

        if(oImage == null)
            oImage = ImageLoader.getInstance().getImage("/O.png");
    }

    private void configButton() {
        setPreferredSize(new Dimension(width, height));
    }

    public void setValue(char value){
        this.value = value;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

        if(value == 'X'){
            g2.drawImage(xImage.getScaledInstance(
                    width, height,
                    Image.SCALE_SMOOTH),
                    0, 0,
                    width, height,
                    null);
        } else if(value == 'O'){
            g2.drawImage(oImage.getScaledInstance(
                    width, height,
                    Image.SCALE_SMOOTH),
                    0, 0,
                    width, height,
                    null);
        }
    }
}
