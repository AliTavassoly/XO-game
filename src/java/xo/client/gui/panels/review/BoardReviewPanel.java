package xo.client.gui.panels.review;

import xo.client.Mapper;
import xo.client.gui.model.Cell;
import xo.server.data.Configs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardReviewPanel extends JPanel {
    private Cell[][] cells;

    public BoardReviewPanel() {
        configGameBoard();

        makeBoard();

        drawBoard();
    }

    private void makeBoard() {
        cells = new Cell[Configs.boardRows][Configs.boardCols];

        for (int i = 0; i < Configs.boardCols; i++) {
            for (int j = 0; j < Configs.boardRows; j++) {
                cells[i][j] = new Cell(i, j);
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
        for (int i = 0; i < Configs.boardCols; i++) {
            for (int j = 0; j < Configs.boardRows; j++) {
                cells[j][i].setBounds(i * Configs.cellWidth, j * Configs.cellHeight,
                        Configs.cellWidth, Configs.cellHeight);
                add(cells[j][i]);
            }
        }
    }

    public void updateBoard(char[][] board) {
        for (int i = 0; i < Configs.boardRows; i++) {
            for (int j = 0; j < Configs.boardCols; j++) {
                cells[i][j].setValue(board[i][j]);
            }
        }
    }
}
