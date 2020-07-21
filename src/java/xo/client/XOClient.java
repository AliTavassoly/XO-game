package xo.client;

import xo.client.gui.GameFrame;
import xo.data.Data;
import xo.data.DataBase;
import xo.model.Account;
import xo.model.Player;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;

public class XOClient extends Thread{
    public static Account currentAccount;
    public static Player currentPlayer;

    private Socket socket;
    private ClientReceiver receiver;
    private ClientSender sender;

    private int authToken;

    public XOClient(String serverIP, int serverPort){
        try{
            this.socket = new Socket(serverIP, serverPort);
            System.out.println("Connected to Server at: " + serverIP + ":" + serverPort);

            DataBase.load();
            GameFrame.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void login(String username, String password) throws Exception {
        //Data.checkAccount(username, password);
        currentAccount = Data.getAccount(username);
    }

    public static void register(String username, String password) throws Exception {
        //Data.addAccount(username, password);
        currentAccount = Data.getAccount(username);
    }

    public static void logout(){
        currentAccount = null;
        currentPlayer = null;
    }

    @Override
    public void run() {
        try {
            InputStream socketInputStream = socket.getInputStream();
            PrintStream socketPrinter = new PrintStream(socket.getOutputStream());

            receiver = new ClientReceiver(socketInputStream);
            sender = new ClientSender(socketPrinter);

            receiver.start();
            sender.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        receiver.interrupt();
    }

    private boolean isStillAlive() {
        return (socket.isConnected() && sender.isAlive());
    }
}
