package xo.server.logic;

import xo.model.Player;
import xo.server.data.Configs;
import xo.util.XOException;

public class Game {
    private char [][] board;
    private int boardCols, boardRows, winCondition;

    private Player player0, player1;

    public char[][] getBoard(){
        return board;
    }

    public Player getPlayer0(){
        return player0;
    }
    public Player getPlayer1(){return player1;}

    public Game(){}

    public Game(Player player0, Player player1){
        boardRows = Configs.boardRows;
        boardCols = Configs.boardCols;
        winCondition = Configs.winCondition;

        this.player0 = player0;
        this.player1 = player1;

        board = new char[boardRows][boardCols];

        configGame();
    }

    private void configGame() {
        for(int i = 0; i < boardRows; i++){
            for(int j = 0; j < boardCols; j++){
                board[i][j] = '.';
            }
        }
    }

    private char whoWonHorizontally(){
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

    private char whoWonVertically(){
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

    private char whoWonNegativeDiagonalN(){
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
                if(flag && board[i][j] != '.')
                    return board[i][j];
            }
        }
        return '.';
    }

    private char whoWonPositiveDiagonalN(){
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
                if(flag && board[i][j] != '.')
                    return board[i][j];
            }
        }
        return '.';
    }

    private boolean isInBoard(int i, int j){
        return i >= 0 && j >= 0 && i < boardRows && j < boardCols;
    }

    public void markCell(char character, int i, int j) throws XOException{
        if(player0.isMyTurn() && player0.getShape() != character)
            throw new XOException("Wrong turn move!");
        if(player1.isMyTurn() && player1.getShape() != character)
            throw new XOException("Wrong turn move!");
        if(board[i][j] != '.')
            throw new XOException("Cell is not empty!");

        board[i][j] = character;
        changeTurn();
    }

    private void changeTurn(){
        player0.changeTurn();
        player1.changeTurn();
    }

    public char whoWonGame(){
        if(whoWonVertically() != '.')
            return whoWonVertically();
        if(whoWonHorizontally() != '.')
            return whoWonHorizontally();
        if(whoWonNegativeDiagonalN() != '.')
            return whoWonNegativeDiagonalN();
        if(whoWonPositiveDiagonalN() != '.')
            return whoWonPositiveDiagonalN();
        return '.';
    }

    public boolean isGameEnded(){
        return whoWonVertically() != '.' ||
                whoWonHorizontally() != '.' ||
                whoWonNegativeDiagonalN() != '.' ||
                whoWonPositiveDiagonalN() != '.';
    }
}
