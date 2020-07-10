package client.gui.xocontrols;

import javax.swing.*;
import java.awt.*;

public class XOButton extends JButton {
    public XOButton(){
        super();
        configButton();
    }

    public XOButton(String text){
        super(text);
        configButton();
    }

    private void configButton() {
        setPreferredSize(new Dimension(120, 40));
        this.setFocusable(false);
    }
}
