package xo.client.gui.panels;

import xo.client.gui.model.Cell;
import xo.data.Configs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardPanel extends JPanel {
    private Cell[][] cells;

    public BoardPanel(){
        configGameBoard();

        makeBoard();

        drawBoard();
    }

    private void makeMouseListener(Cell cell){
        cell.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
    }

    private void makeBoard() {
        cells = new Cell[Configs.boardRows][Configs.boardCols];

        for(int i = 0; i < Configs.boardCols; i++){
            for(int j = 0; j < Configs.boardRows; j++) {
                cells[i][j] = new Cell(i, j);
                makeMouseListener(cells[i][j]);
            }
        }
    }

    private void configGameBoard() {
        int width = Configs.cellWidth * Configs.boardCols;
        int height = Configs.cellHeight * Configs.boardRows;
        setPreferredSize(new Dimension(width, height));
        setLayout(null);
    }

    private void drawBoard() {
        for(int i = 0; i < Configs.boardCols; i++){
            for(int j = 0; j < Configs.boardRows; j++) {
                cells[j][i].setBounds(i * Configs.cellWidth, j * Configs.cellHeight,
                        Configs.cellWidth, Configs.cellHeight);
                add(cells[j][i]);
            }
        }
    }

    public void mark(Character character, int i, int j){
        cells[i][j].setValue(character);
    }
}
