package xo.client;

import xo.client.gui.GameFrame;
import xo.client.gui.panels.BoardPanel;
import xo.client.gui.panels.GamePanel;
import xo.client.network.Receiver;
import xo.client.network.Sender;
import xo.data.Data;
import xo.data.DataBase;
import xo.model.Account;
import xo.model.Packet;
import xo.model.Player;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;

public class XOClient extends Thread{
    private static XOClient instance;

    public Account currentAccount;
    public Player currentPlayer;
    public GamePanel currentGamePanel;

    private Socket socket;
    private Receiver receiver;
    private Sender sender;

    private XOClient(String serverIP, int serverPort){
        try{
            this.socket = new Socket(serverIP, serverPort);
            System.out.println("Connected to Server at: " + serverIP + ":" + serverPort);

            DataBase.load();
            GameFrame.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static XOClient makeInstance(String serverIP, int serverPort){
        return instance = new XOClient(serverIP, serverPort);
    }

    public static XOClient getInstance(){
        return instance;
    }

    public GamePanel getCurrentGamePanel(){
        return currentGamePanel;
    }

    @Override
    public void run() {
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

    public static void sendPacket(Packet packet){
        getInstance().sender.sendPacket(packet);
    }
}
