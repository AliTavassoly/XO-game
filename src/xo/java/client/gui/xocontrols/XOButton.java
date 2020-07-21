package client.gui.xocontrols;

import javax.swing.*;
import java.awt.*;

public class XOButton extends JButton {
    private int width, height;

    public XOButton() {
        super();
        configButton();
    }

    public XOButton(String text) {
        super(text);
        configButton();
    }

    public XOButton(int width, int height, String text) {
        super(text);

        this.width = width;
        this.height = height;

        configButton();
    }

    private void configButton() {
        if (width == 0 || height == 0)
            setPreferredSize(new Dimension(150, 40));
        else
            setPreferredSize(new Dimension(width, height));
        this.setFocusable(false);
    }
}
