package xo.client.gui.panels;

import xo.client.Mapper;
import xo.client.XOClient;
import xo.client.gui.GameFrame;
import xo.client.gui.xocontrols.XOButton;
import xo.server.data.Configs;
import xo.client.gui.util.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class MainMenuPanel extends JPanel{
    private XOButton multiPlayer, status, logout, scoreBoard;

    private static BufferedImage background;

    public MainMenuPanel(){
        configPanel();

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
            background = ImageLoader.getInstance().getImage("/mainmenu_background.png");

        g2.drawImage(background.getScaledInstance(
                Configs.gameFrameWidth, Configs.gameFrameHeight,
                Image.SCALE_SMOOTH),
                0, 0,
                Configs.gameFrameWidth, Configs.gameFrameHeight,
                null);
    }

    private void makeButtons() {
        multiPlayer = new XOButton("Multiplayer");
        multiPlayer.setFont(GameFrame.getCustomFont(0));

        status = new XOButton("Status");
        status.setFont(GameFrame.getCustomFont(0));

        scoreBoard = new XOButton("Scoreboard");
        scoreBoard.setFont(GameFrame.getCustomFont(0));

        logout = new XOButton("Logout");
        logout.setFont(GameFrame.getCustomFont(0));

        multiPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Mapper.newGameRequest(XOClient.getInstance().currentAccount.getUsername());
            }
        });

        status.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });

        scoreBoard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });

        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Mapper.logoutRequest();
                GameFrame.switchPanelTo(new LogisterPanel());
            }
        });
    }

    private void layoutComponent() {
        setLayout(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();

        grid.gridx = 0;
        grid.gridy = 0;
        grid.insets = new Insets(20, 0, 20, 0);
        add(multiPlayer, grid);

        grid.gridx = 0;
        grid.gridy = 1;
        grid.insets = new Insets(20, 0, 20, 0);
        add(status, grid);

        grid.gridx = 0;
        grid.gridy = 2;
        grid.insets = new Insets(20, 0, 20, 0);
        add(scoreBoard, grid);

        grid.gridx = 0;
        grid.gridy = 3;
        grid.insets = new Insets(20, 0, 20, 0);
        add(logout, grid);
    }
}
