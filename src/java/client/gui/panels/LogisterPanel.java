package client.gui.panels;

import client.Main;
import client.gui.GameFrame;
import client.gui.xocontrols.XOButton;
import client.gui.xocontrols.XOJTextField;
import data.Configs;
import data.DataBase;
import util.ImageLoader;
import util.XOException;

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
        loginButton = new XOButton("Login");

        registerButton = new XOButton("Register");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    Main.login(userField.getText(), passwordField.getText());

                    DataBase.save();

                    GameFrame.switchPanelTo(new MainMenuPanel());
                } catch (XOException xoException){
                    error = xoException.getMessage();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    Main.register(userField.getText(), passwordField.getText());
                    DataBase.save();
                } catch (XOException xoException){
                    error = xoException.getMessage();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void makeLabels() {
        userLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");
    }

    private void makeFields() {
        userField = new XOJTextField(10);
        passwordField = new XOJTextField(10);
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
