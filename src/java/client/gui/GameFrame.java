package client.gui;

import client.gui.panels.GameBoardPanel;
import client.gui.panels.GamePanel;
import client.gui.panels.LogisterPanel;
import data.Configs;

import javax.swing.*;
import java.util.TimerTask;

public class GameFrame extends JFrame {
    private static GameFrame instance;

    private GameFrame(){
        configFrame();

        createAndRunFrameUpdater();
    }

    private void createAndRunFrameUpdater() {
        java.util.Timer timer = new java.util.Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                instance.repaint();
                instance.revalidate();
            }
        };

        long delay = 0;
        long period = 50L;
        timer.schedule(task, delay, period);
    }

    public static GameFrame getInstance(){
        if(instance == null)
            instance = new GameFrame();
        return instance;
    }

    private void configFrame() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setSize(Configs.gameFrameWidth, Configs.gameFrameHeight);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        this.setContentPane(new LogisterPanel());

        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        this.setVisible(true);
    }

    public static void switchPanelTo(JPanel panel) {
        instance.getContentPane().setVisible(false);
        instance.setContentPane(panel);
    }
}
