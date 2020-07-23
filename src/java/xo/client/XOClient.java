package xo.client;

import xo.client.gui.GameFrame;
import xo.client.gui.panels.GamePanel;
import xo.client.gui.panels.LogisterPanel;
import xo.client.gui.panels.MainMenuPanel;
import xo.client.gui.panels.WaitingPanel;
import xo.client.gui.panels.endgame.LostPanel;
import xo.client.gui.panels.endgame.WonPanel;
import xo.client.network.Receiver;
import xo.client.network.Sender;
import xo.model.Account;
import xo.model.Packet;
import xo.model.Player;
import xo.server.ClientHandler;
import xo.util.timer.XODelayTask;
import xo.util.timer.XODelayTimer;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

public class XOClient {
    private static XOClient instance;

    public Account currentAccount;
    public GamePanel currentGamePanel;

    private Socket socket;
    private Receiver receiver;
    private Sender sender;

    public ArrayList<char [][]> reviewBoards;
    public Player myPlayerReview, enemyPlayerReview;

    public XOClient(){
    }

    private XOClient(String serverIP, int serverPort){
        try{
            this.socket = new Socket(serverIP, serverPort);

            GameFrame.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static XOClient makeNewInstance(String serverIP, int serverPort){
        return instance = new XOClient(serverIP, serverPort);
    }

    public static XOClient getInstance(){
        return instance;
    }

    public void start() {
        try {
            InputStream socketInputStream = socket.getInputStream();
            PrintStream socketPrinter = new PrintStream(socket.getOutputStream());

            receiver = new Receiver(socketInputStream);
            sender = new Sender(socketPrinter);

            receiver.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCurrentAccount(Account account){
        currentAccount = account;
    }

    public void setCurrentGamePanel(GamePanel gamePanel){
        currentGamePanel = gamePanel;
    }

    public void login(Account account){
        setCurrentAccount(account);
        GameFrame.switchPanelTo(new MainMenuPanel());
    }

    public void register(Account account){
        setCurrentAccount(account);
        GameFrame.switchPanelTo(new MainMenuPanel());
    }

    public void logout(){
        setCurrentAccount(null);
        GameFrame.switchPanelTo(new LogisterPanel());
    }

    private void makeReview(Player myPlayer, Player enemyPlayer){
        reviewBoards = new ArrayList<>();
        this.myPlayerReview = myPlayer;
        this.enemyPlayerReview = enemyPlayer;
    }

    private void updateReview(char [][] board){
        reviewBoards.add(board);
    }

    public void newGame(Player myPlayer, Player enemyPlayer) {
        GamePanel gamePanel = new GamePanel(myPlayer, enemyPlayer);
        currentGamePanel = gamePanel;

        makeReview(myPlayer, enemyPlayer);

        GameFrame.switchPanelTo(gamePanel);
    }

    public void afterMarkingCell(char [][] board){
        updateBoard(board);
        XOClient.getInstance().changeTurn();
        updateReview(board);
    }

    public void updateBoard(char [][] board){
        currentGamePanel.updateGame(board);
    }

    public void changeTurn(){
        currentGamePanel.changeTurn();
    }

    public void wonGame() {
        GameFrame.switchPanelTo(new WonPanel());
        XODelayTimer delayTimer = new XODelayTimer(2500, new XODelayTask() {
            @Override
            public void delayAction() {
                GameFrame.switchPanelTo(new MainMenuPanel());
            }
        });
        delayTimer.start();
    }

    public void lostGame() {
        GameFrame.switchPanelTo(new LostPanel());
        XODelayTimer delayTimer = new XODelayTimer(2500, new XODelayTask() {
            @Override
            public void delayAction() {
                GameFrame.switchPanelTo(new MainMenuPanel());
            }
        });
        delayTimer.start();
    }

    public void waitForGame() {
        GameFrame.switchPanelTo(new WaitingPanel());
    }

    public void cancelWaitingForGame() {
        GameFrame.switchPanelTo(new MainMenuPanel());
    }

    public static void sendPacket(Packet packet){
        getInstance().sender.sendPacket(packet);
    }
}
