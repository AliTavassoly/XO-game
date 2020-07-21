package model;

import data.Configs;

public class Game {
    private Character [][] board;
    private int boardCols, boardRows, winCondition;

    public Game(){
        boardRows = Configs.boardRows;
        boardCols = Configs.boardCols;

        configGame();
    }

    private void configGame() {
        for(int i = 0; i < boardRows; i++){
            for(int j = 0; j < boardCols; j++){
                board[i][i] = '.';
            }
        }
    }

    private Character whoWonHorizontally(){
        for(int i = 0; i < boardRows; i++){
            for(int j = 0; j <= boardCols - winCondition; j++){
                boolean flag = true;
                for(int k = j; k < j + winCondition; k++){
                    if (board[i][k] != board[i][j]) {
                        flag = false;
                        break;
                    }
                }

                if(flag && board[i][j] != '.')
                    return board[i][j];
            }
        }
        return '.';
    }

    private Character whoWonVertically(){
        for(int i = 0; i <= boardRows - winCondition; i++){
            for(int j = 0; j < boardCols; j++){
                boolean flag = true;
                for(int k = i; k < i + winCondition; k++){
                    if (board[k][j] != board[i][j]) {
                        flag = false;
                        break;
                    }
                }

                if(flag && board[i][j] != '.')
                    return board[i][j];
            }
        }
        return '.';
    }

    private Character whoWonNegativeDiagonalN(){
        for(int i = 0; i < boardRows; i++){
            for(int j = 0; j < boardCols; j++){
                int currentI = i, currentJ = j;
                boolean flag = true;

                for(int k = 0; k < winCondition; k++){
                    if(!isInBoard(currentI, currentJ))
                        flag = false;
                    else if(board[currentI][currentJ] != board[i][j])
                        flag = false;
                    currentI++;
                    currentJ++;
                }
                if(flag)
                    return board[i][j];
            }
        }
        return '.';
    }

    private Character whoWonPositiveDiagonalN(){
        for(int i = 0; i < boardRows; i++){
            for(int j = 0; j < boardCols; j++){
                int currentI = i, currentJ = j;
                boolean flag = true;

                for(int k = 0; k < winCondition; k++){
                    if(!isInBoard(currentI, currentJ))
                        flag = false;
                    else if(board[currentI][currentJ] != board[i][j])
                        flag = false;
                    currentI++;
                    currentJ--;
                }
                if(flag)
                    return board[i][j];
            }
        }
        return '.';
    }

    private boolean isInBoard(int i, int j){
        return i >= 0 && j >= 0 && i < boardRows && j < boardCols;
    }

    private boolean isGameEnded(){
        return whoWonVertically() != '.' ||
                whoWonHorizontally() != '.' ||
                whoWonNegativeDiagonalN() != '.' ||
                whoWonPositiveDiagonalN() != '.';
    }
}
