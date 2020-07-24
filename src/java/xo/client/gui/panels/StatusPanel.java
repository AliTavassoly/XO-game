package xo.client.gui.panels;

import xo.client.XOClient;
import xo.client.gui.GameFrame;
import xo.client.gui.util.ImageLoader;
import xo.client.gui.xocontrols.XOButton;
import xo.server.data.Configs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class StatusPanel extends JPanel {
    private JLabel usernameLabel, wonLabel, lostLabel, pointLabel;
    private XOButton backButton;

    private static BufferedImage background;

    public StatusPanel(){
        configPanel();

        makeLabels();

        makeButtons();

        layoutComponent();
    }

    private void configPanel(){
        setPreferredSize(new Dimension(Configs.gameFrameWidth, Configs.gameFrameHeight));
    }

    private void makeButtons() {
        backButton = new XOButton(150, 40, "Back");
        backButton.setFont(GameFrame.getCustomFont(0));

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                GameFrame.switchPanelTo(new MainMenuPanel());
            }
        });
    }

    private void makeLabels(){
        usernameLabel = new JLabel("Username: " + XOClient.getInstance().currentAccount.getUsername());
        usernameLabel.setFont(GameFrame.getCustomFont(0));

        pointLabel = new JLabel("Point: " + XOClient.getInstance().currentAccount.getPoint());
        pointLabel.setFont(GameFrame.getCustomFont(0));

        wonLabel = new JLabel("Won: " + XOClient.getInstance().currentAccount.getWinGames());
        wonLabel.setFont(GameFrame.getCustomFont(0));

        lostLabel = new JLabel("Lost: " + XOClient.getInstance().currentAccount.getLostGames());
        lostLabel.setFont(GameFrame.getCustomFont(0));
    }

    private void layoutComponent() {
        setLayout(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();

        grid.gridx = 0;
        grid.gridy = 0;
        grid.insets = new Insets(20, 0, 20, 0);
        add(usernameLabel, grid);

        grid.gridx = 0;
        grid.gridy = 1;
        grid.insets = new Insets(20, 0, 20, 0);
        add(pointLabel, grid);

        grid.gridx = 0;
        grid.gridy = 2;
        grid.insets = new Insets(20, 0, 20, 0);
        add(wonLabel, grid);

        grid.gridx = 0;
        grid.gridy = 3;
        grid.insets = new Insets(20, 0, 20, 0);
        add(lostLabel, grid);

        grid.gridx = 0;
        grid.gridy = 4;
        grid.insets = new Insets(20, 0, 20, 0);
        add(backButton, grid);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

        if(background == null)
            background =  ImageLoader.getInstance().getImage("/main_menu_background.png");

        g2.drawImage(background.getScaledInstance(
                Configs.gameFrameWidth, Configs.gameFrameHeight,
                Image.SCALE_SMOOTH),
                0, 0,
                Configs.gameFrameWidth, Configs.gameFrameHeight,
                null);
    }
}
