package xo.client.gui;

import xo.client.gui.panels.LogisterPanel;
import xo.server.data.Configs;
import xo.client.gui.util.FontLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.TimerTask;

public class GameFrame extends JFrame {
    private static GameFrame instance;

    private GameFrame() {
        configFrame();

        makeMouseListener();

        createAndRunFrameUpdater();
    }

    private void makeMouseListener() {
        this.addMouseMotionListener(new MouseMotionListener() {
            public void mouseDragged(MouseEvent mouseEvent) {
                GameFrame.this.setLocation(
                        (int)mouseEvent.getLocationOnScreen().getX() - Configs.gameFrameWidth / 2,
                        (int)mouseEvent.getLocationOnScreen().getY() - Configs.gameFrameHeight / 2
                        );
            }
            public void mouseMoved(MouseEvent mouseEvent) { }
        });
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

    public static GameFrame getInstance() {
        if (instance == null)
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

    public static Font getCustomFont(int style, int size) {
        Font font = null;
        font = FontLoader.getInstance().getFont("/font.ttf");
        return font.deriveFont(style, size);
    }

    public static Font getCustomFont(int style) {
        Font font = null;
        font = FontLoader.getInstance().getFont("/font.ttf");
        return font.deriveFont(style, 20);
    }
}
