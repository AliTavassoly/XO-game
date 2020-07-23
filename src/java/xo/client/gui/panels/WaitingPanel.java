package xo.client.gui.panels;

import xo.client.Mapper;
import xo.client.gui.GameFrame;
import xo.client.gui.util.ImageLoader;
import xo.client.gui.xocontrols.XOButton;
import xo.server.data.Configs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class WaitingPanel extends JPanel {
    private XOButton cancelButton;
    private static BufferedImage background;

    public WaitingPanel(){
        configPanel();

        makeButtons();

        componentLayout();
    }

    private void configPanel(){
        setPreferredSize(new Dimension(Configs.gameFrameWidth, Configs.gameFrameHeight));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

        if(background == null)
            background =  ImageLoader.getInstance().getImage("/waiting_background.png");

        g2.drawImage(background.getScaledInstance(
                Configs.gameFrameWidth, Configs.gameFrameHeight,
                Image.SCALE_SMOOTH),
                0, 0,
                Configs.gameFrameWidth, Configs.gameFrameHeight,
                null);
    }

    private void makeButtons(){
        cancelButton = new XOButton("Cancel");
        cancelButton.setFont(GameFrame.getCustomFont(0));

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Mapper.cancelWaitingForGameRequest();
            }
        });
    }

    private void componentLayout(){
        setLayout(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();

        grid.gridx = 0;
        grid.gridy = 0;
        grid.insets = new Insets(200, 0, 0, 0);
        add(cancelButton, grid);
    }
}
