package xo.client.gui.panels;

import xo.client.Mapper;
import xo.client.gui.GameFrame;
import xo.client.gui.xocontrols.XOButton;
import xo.client.gui.xocontrols.XOJTextField;
import xo.data.Configs;
import xo.client.gui.util.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class LogisterPanel extends JPanel {
    private XOJTextField userField;
    private XOJTextField passwordField;

    private JLabel userLabel;
    private JLabel passwordLabel;

    private XOButton loginButton;
    private XOButton registerButton;

    private static BufferedImage background;

    private String error;

    public LogisterPanel(){
        configPanel();

        makeLabels();

        makeFields();

        makeButtons();

        layoutComponent();
    }

    private void configPanel() {
        setPreferredSize(new Dimension(Configs.gameFrameWidth, Configs.gameFrameHeight));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

        if(background == null)
            background = ImageLoader.getInstance().getImage("/logister_background.png");

        g2.drawImage(background.getScaledInstance(
                Configs.gameFrameWidth, Configs.gameFrameHeight,
                Image.SCALE_SMOOTH),
                0, 0,
                Configs.gameFrameWidth, Configs.gameFrameHeight,
                null);
    }

    private void makeButtons() {
        loginButton = new XOButton(100, 40, "Login");
        loginButton.setFont(GameFrame.getCustomFont(0));

        registerButton = new XOButton(100, 40,"Register");
        registerButton.setFont(GameFrame.getCustomFont(0));

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    Mapper.sendLogin(userField.getText(), passwordField.getText());
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    Mapper.sendRegister(userField.getText(), passwordField.getText());
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void makeLabels() {
        userLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");

        userLabel.setFont(GameFrame.getCustomFont(0));
        passwordLabel.setFont(GameFrame.getCustomFont(0));
    }

    private void makeFields() {
        userField = new XOJTextField(10);
        passwordField = new XOJTextField(10);

        userField.setFont(GameFrame.getCustomFont(0));
        passwordField.setFont(GameFrame.getCustomFont(0));
    }

    private void layoutComponent() {
        setLayout(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();

        // first row
        grid.gridx = 0;
        grid.gridy = 0;
        add(userLabel, grid);

        grid.gridx = 1;
        grid.gridy = 0;
        add(userField, grid);

        // second row
        grid.gridx = 0;
        grid.gridy = 1;
        grid.insets = new Insets(20, 0, 0, 0);
        add(passwordLabel, grid);

        grid.gridx = 1;
        grid.gridy = 1;
        grid.insets = new Insets(20, 0, 0, 0);
        add(passwordField, grid);

        // third row
        grid.gridx = 0;
        grid.gridy = 2;
        grid.insets = new Insets(270, 0, 0, 0);
        add(loginButton, grid);

        grid.gridx = 1;
        grid.gridy = 2;
        grid.insets = new Insets(270, 0, 0, 0);
        add(registerButton, grid);
    }
}
