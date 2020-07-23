package xo.client.gui.panels.review;

import xo.client.gui.GameFrame;
import xo.client.gui.panels.MainMenuPanel;
import xo.client.gui.panels.ShapePanel;
import xo.client.gui.panels.review.BoardReviewPanel;
import xo.client.gui.xocontrols.XOButton;
import xo.model.Player;
import xo.server.data.Configs;
import xo.util.timer.XOTimer;
import xo.util.timer.XOTimerTask;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameReviewerPanel extends JPanel{
    private JLabel myUserLabel, enemyUserLabel;
    private ShapePanel myShape, enemyShape;
    private XOButton resumeButton, restartButton, pauseButton, exitButton;

    private BoardReviewPanel boardPanel;

    private XOTimer timer;

    private Player myPlayer, enemyPlayer;
    private ArrayList <char [][]> boards;

    public GameReviewerPanel(Player myPlayer, Player enemyPlayer, ArrayList <char [][]> boards) {
        this.myPlayer = myPlayer;
        this.enemyPlayer = enemyPlayer;
        this.boards = boards;

        makeBoard();

        configPanel();

        makeLabels();

        makeButtons();

        makeShapes();

        customizeLabels();

        layoutComponent();

        startReview();
    }

    private void customizeLabels() {
        int labelsWidth = ((int) boardPanel.getPreferredSize().getWidth() - 2 * (int) myShape.getPreferredSize().getWidth()) / 2;
        int labelsHeight = (int) myShape.getPreferredSize().getHeight();

        myUserLabel.setPreferredSize(new Dimension(labelsWidth, labelsHeight));
        myUserLabel.setHorizontalAlignment(SwingConstants.LEFT);
        myUserLabel.setFont(GameFrame.getCustomFont(0));

        enemyUserLabel.setPreferredSize(new Dimension(labelsWidth, labelsHeight));
        enemyUserLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        enemyUserLabel.setFont(GameFrame.getCustomFont(0));
    }

    private void makeBoard() {
        boardPanel = new BoardReviewPanel();
    }

    private void configPanel() {
        setPreferredSize(new Dimension(Configs.gameFrameWidth, Configs.gameFrameHeight));
    }

    private void makeButtons() {
        pauseButton = new XOButton(120, 40, "Pause");
        pauseButton.setFont(GameFrame.getCustomFont(0));

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                timer.pauseTimer();
            }
        });

        resumeButton = new XOButton(120, 40, "Resume");
        resumeButton.setFont(GameFrame.getCustomFont(0));

        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                timer.resumeTimer();
            }
        });

        restartButton = new XOButton(120, 40, "Restart");
        restartButton.setFont(GameFrame.getCustomFont(0));

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                startReview();
            }
        });

        exitButton = new XOButton(120, 40, "Exit");
        exitButton.setFont(GameFrame.getCustomFont(0));

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                GameFrame.switchPanelTo(new MainMenuPanel());
            }
        });
    }

    private void makeLabels() {
        myUserLabel = new JLabel(myPlayer.getUsername());
        myUserLabel.setFont(GameFrame.getCustomFont(0));

        enemyUserLabel = new JLabel(enemyPlayer.getUsername());
        enemyUserLabel.setFont(GameFrame.getCustomFont(0));
    }

    private void makeShapes() {
        myShape = new ShapePanel(Configs.shapeInGameWidth, Configs.shapeInGameHeight, myPlayer.getShape());
        enemyShape = new ShapePanel(Configs.shapeInGameWidth, Configs.shapeInGameHeight, enemyPlayer.getShape());
    }

    private void updateReviewGame(){
        revalidate();
        repaint();
    }

    private void layoutComponent() {
        setLayout(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();

        // first row
        grid.gridx = 0;
        grid.gridy = 0;
        grid.insets = new Insets(0, 0, 0, 50);
        add(myShape, grid);

        grid.gridx = 1;
        grid.gridy = 0;
        grid.insets = new Insets(0, 0, 0, 50);
        add(myUserLabel, grid);

        grid.gridx = 2;
        grid.gridy = 0;
        grid.insets = new Insets(0, 50, 0, 0);
        add(enemyUserLabel, grid);

        grid.gridx = 3;
        grid.gridy = 0;
        grid.insets = new Insets(0, 50, 0, 0);
        add(enemyShape, grid);

        // second row
        grid.gridx = 0;
        grid.gridy = 1;
        grid.gridwidth = 4;
        grid.insets = new Insets(20, 0, 0, 0);
        add(boardPanel, grid);

        // third row
        grid.gridx = 1;
        grid.gridy = 2;
        grid.gridwidth = 1;
        grid.insets = new Insets(20, 4, 0, 4);
        add(pauseButton, grid);

        grid.gridx = 2;
        grid.gridy = 2;
        grid.gridwidth = 1;
        grid.insets = new Insets(20, 4, 0, 4);
        add(restartButton, grid);

        // fourth row
        grid.gridx = 1;
        grid.gridy = 3;
        grid.gridwidth = 1;
        grid.insets = new Insets(20, 4, 0, 4);
        add(resumeButton, grid);

        grid.gridx = 2;
        grid.gridy = 3;
        grid.gridwidth = 1;
        grid.insets = new Insets(20, 4, 0, 4);
        add(exitButton, grid);
    }

    private void startReview(){
        final int[] ind = {0};

        timer = new XOTimer(300, new XOTimerTask() {
            @Override
            public void startFunction() {
                boardPanel.updateBoard(boards.get(ind[0]));
            }

            @Override
            public void periodFunction() {
                boardPanel.updateBoard(boards.get(ind[0]));
                ind[0]++;
            }

            @Override
            public boolean finishedCondition() {
                return ind[0] == boards.size();
            }
        });
        timer.start();
    }
}
