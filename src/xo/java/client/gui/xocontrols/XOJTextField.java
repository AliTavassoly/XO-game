package client.gui.xocontrols;

import javax.swing.*;

public class XOJTextField extends JTextField {
    public XOJTextField(){
        configField();
    }

    public XOJTextField(int columns){
        super(columns);
        configField();
    }

    private void configField(){
        this.setBorder(null);
    }
}
