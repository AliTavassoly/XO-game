package xo.client.gui.panels;

import xo.client.Mapper;
import xo.client.configs.Configs;
import xo.client.gui.GameFrame;
import xo.client.gui.util.ImageLoader;
import xo.client.gui.xocontrols.XOButton;
import xo.model.AccountInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

public class ScoreboardPanel extends JPanel {
    private XOButton backButton;
    private static BufferedImage background;

    private JScrollPane scrollPane;
    private JPanel listPanel;

    public ScoreboardPanel(){
        configPanel();

        makeList();

        makeButtons();

        componentLayout();

        Mapper.startUpdateAccountInfo();
    }

    private void makeList() {
        listPanel = new JPanel();
        listPanel.setPreferredSize(new Dimension(Configs.scoreBoardListPanelWidth, Configs.scoreBoardListPanelHeight));

        scrollPane = new JScrollPane(listPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(Configs.gameFrameWidth / 2 - Configs.scoreBoardListPanelWidth / 2,
                50,
                Configs.scoreBoardListPanelWidth, Configs.scoreBoardListPanelHeight);
        scrollPane.setPreferredSize(new Dimension(Configs.scoreBoardListPanelWidth, Configs.scoreBoardListPanelHeight));
    }

    private void configPanel(){
        setPreferredSize(new Dimension(Configs.gameFrameWidth, Configs.gameFrameHeight));
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

    private void makeButtons(){
        backButton = new XOButton("Back");
        backButton.setFont(GameFrame.getCustomFont(0));

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Mapper.stopUpdateAccountInfo();
                GameFrame.switchPanelTo(new MainMenuPanel());
            }
        });
    }

    private void componentLayout(){
        setLayout(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();

        grid.gridx = 0;
        grid.gridy = 0;
        grid.insets = new Insets(0, 0, 0, 0);
        add(scrollPane, grid);

        grid.gridx = 0;
        grid.gridy = 1;
        grid.insets = new Insets(20, 0, 0, 0);
        add(backButton, grid);
    }

    public synchronized void updateList(ArrayList<AccountInfo> accounts){
        if(accounts == null)
            return;

        Collections.sort(accounts);

        listPanel.removeAll();
        listPanel.setLayout(new GridBagLayout());
        listPanel.setPreferredSize(new Dimension(Configs.scoreBoardListPanelWidth,
                accounts.size() * 26));
        GridBagConstraints grid = new GridBagConstraints();

        grid.gridx = 0;
        grid.gridy = 0;
        grid.anchor = GridBagConstraints.ABOVE_BASELINE;
        grid.insets = new Insets(0, 10, 0, 10);
        JLabel userLabel = new JLabel("User");
        userLabel.setFont(GameFrame.getCustomFont(0));
        listPanel.add(userLabel, grid);

        grid.gridx = 1;
        grid.gridy = 0;
        grid.insets = new Insets(0, 10, 0, 10);
        JLabel pointLabel = new JLabel("Point");
        pointLabel.setFont(GameFrame.getCustomFont(0));
        listPanel.add(pointLabel, grid);

        grid.gridx = 2;
        grid.gridy = 0;
        grid.insets = new Insets(0, 10, 0, 10);
        JLabel onlineLabel = new JLabel("Status");
        onlineLabel.setFont(GameFrame.getCustomFont(0));
        listPanel.add(onlineLabel, grid);

        for(int i = 0; i < accounts.size(); i++){
            grid.gridx = 0;
            grid.gridy = i + 1;
            grid.insets = new Insets(0, 10, 0, 10);
            userLabel = new JLabel(accounts.get(i).getUsername());
            userLabel.setFont(GameFrame.getCustomFont(0));
            listPanel.add(userLabel, grid);

            grid.gridx = 1;
            grid.gridy = i + 1;
            grid.insets = new Insets(0, 10, 0, 10);
            pointLabel = new JLabel(String.valueOf(accounts.get(i).getPoint()));
            pointLabel.setFont(GameFrame.getCustomFont(0));
            listPanel.add(pointLabel, grid);

            grid.gridx = 2;
            grid.gridy = i + 1;
            grid.insets = new Insets(0, 10, 0, 10);
            onlineLabel = new JLabel(String.valueOf(accounts.get(i).getStatus()));
            onlineLabel.setFont(GameFrame.getCustomFont(0));
            if(accounts.get(i).getStatus() == AccountInfo.status.ONLINE)
                onlineLabel.setForeground(Color.GREEN);
            else
                onlineLabel.setForeground(Color.RED);
            listPanel.add(onlineLabel, grid);
        }
    }
}
