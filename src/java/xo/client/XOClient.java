package xo.client;

import xo.client.gui.GameFrame;
import xo.client.gui.panels.GamePanel;
import xo.client.gui.panels.LogisterPanel;
import xo.client.gui.panels.MainMenuPanel;
import xo.client.network.Receiver;
import xo.client.network.Sender;
import xo.model.Account;
import xo.model.Packet;
import xo.model.Player;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;

public class XOClient /*extends Thread*/{
    private static XOClient instance;

    public Account currentAccount;
    public Player currentPlayer;
    public GamePanel currentGamePanel;

    private Socket socket;
    private Receiver receiver;
    private Sender sender;

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

    public GamePanel getCurrentGamePanel(){
        return currentGamePanel;
    }

    /*@Override
    public void run() {
        try {
            InputStream socketInputStream = socket.getInputStream();
            PrintStream socketPrinter = new PrintStream(socket.getOutputStream());

            receiver = new Receiver(socketInputStream);
            sender = new Sender(socketPrinter);

            receiver.start();

            //Mapper.sendSetClient(XOClient.this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

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

    public void setCurrentPlayer(Player player){
        currentPlayer = player;
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

    public void newGame(Player myPlayer, Player enemyPlayer) {
        GamePanel gamePanel = new GamePanel(myPlayer, enemyPlayer);
        currentGamePanel = gamePanel;
        GameFrame.switchPanelTo(gamePanel);
    }

    public void updateBoard(char [][] board){
        currentGamePanel.updateGame(board);
    }

    public static void sendPacket(Packet packet){
        getInstance().sender.sendPacket(packet);
    }
}
