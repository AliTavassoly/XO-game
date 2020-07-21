package client.gui.panels;

import client.gui.util.ImageLoader;

import javax.swing.*;
import java.awt.*;

public class ShapePanel extends JPanel {
    private Character shape;
    private boolean status;
    private int width, height;

    public ShapePanel(Character shape) {
        this.shape = shape;

        status = false;

        configShape();
    }

    public ShapePanel(int width, int height, Character shape) {
        this.shape = shape;
        this.width = width;
        this.height = height;

        status = false;

        configShape();
    }

    private void configShape() {
        setPreferredSize(new Dimension(width, height));
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        if (status) {
            g2.drawImage(ImageLoader.getInstance().getImage("/" + shape + ".png").getScaledInstance(
                    width, height,
                    Image.SCALE_SMOOTH),
                    0, 0,
                    width, height,
                    null);
        } else {
            g2.drawImage(ImageLoader.getInstance().getImage("/" + shape + "_disable.png").getScaledInstance(
                    width, height,
                    Image.SCALE_SMOOTH),
                    0, 0,
                    width, height,
                    null);
        }
    }

    public void changeStatus(){
        status = !status;
    }
}
