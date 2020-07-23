package xo.client.gui.panels;

import xo.client.gui.GameFrame;
import xo.client.gui.xocontrols.XOButton;
import xo.server.data.Configs;
import xo.model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel {
    private JLabel myUserLabel, enemyUserLabel;
    private ShapePanel myShape, enemyShape;
    private XOButton surrenderButton;

    private Player myPlayer, enemyPlayer;

    private BoardPanel boardPanel;

    public Player getMyPlayer(){
        return myPlayer;
    }

    public GamePanel(Player myPlayer, Player enemyPlayer){
        this.myPlayer = myPlayer;
        this.enemyPlayer = enemyPlayer;

        makeBoard();

        configPanel();

        makeLabels();

        makeButtons();

        makeShapes();

        customizeLabels();

        layoutComponent();
    }
   
    private void customizeLabels() {
        int labelsWidth = ((int) boardPanel.getPreferredSize().getWidth() - 2 * (int)myShape.getPreferredSize().getWidth()) / 2;
        int labelsHeight = (int)myShape.getPreferredSize().getHeight();

        myUserLabel.setPreferredSize(new Dimension(labelsWidth, labelsHeight));
        myUserLabel.setHorizontalAlignment(SwingConstants.LEFT);
        myUserLabel.setFont(GameFrame.getCustomFont(0));

        enemyUserLabel.setPreferredSize(new Dimension(labelsWidth, labelsHeight));
        enemyUserLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        enemyUserLabel.setFont(GameFrame.getCustomFont(0));
    }

    private void makeBoard() {
        boardPanel = new BoardPanel(this);
    }

    private void configPanel() {
        setPreferredSize(new Dimension(Configs.gameFrameWidth, Configs.gameFrameHeight));
    }

    private void makeButtons() {
        surrenderButton = new XOButton(150, 40, "Surrender");
        surrenderButton.setFont(GameFrame.getCustomFont(0));

        surrenderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
    }

    private void makeLabels() {
        myUserLabel = new JLabel(myPlayer.getUsername());
        myUserLabel.setFont(GameFrame.getCustomFont(0));

        enemyUserLabel = new JLabel(enemyPlayer.getUsername());
        enemyUserLabel.setFont(GameFrame.getCustomFont(0));
    }

    private void makeShapes(){
        myShape = new ShapePanel(Configs.shapeInGameWidth, Configs.shapeInGameHeight, myPlayer.getShape());
        enemyShape = new ShapePanel(Configs.shapeInGameWidth, Configs.shapeInGameHeight, enemyPlayer.getShape());

        if(myPlayer.isMyTurn())
            myShape.setStatus(true);
        else
            enemyShape.setStatus(true);
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
        grid.gridx = 0;
        grid.gridy = 2;
        grid.gridwidth = 4;
        add(surrenderButton, grid);
    }

    public void updateGame(char[][] newBoard){
        boardPanel.updateBoard(newBoard);
        revalidate();
        repaint();
    }
}
