package xo.server;

import xo.data.Data;
import xo.data.DataBase;
import xo.model.Account;
import xo.model.Game;
import xo.model.Packet;
import xo.model.Player;
import xo.util.XOException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class XOServer extends Thread {
    private static XOServer instance;
    private ArrayList<ClientHandler> clients;
    private ArrayList<ClientHandler> waitingForGame;
    private Map<ClientHandler, Game> games;
    private ServerSocket serverSocket;

    private XOServer(int serverPort) {
        try {
            this.serverSocket = new ServerSocket(serverPort);
            this.clients = new ArrayList<>();
            this.waitingForGame = new ArrayList<>();
            this.games = new HashMap<>();

            System.out.println("Server Started at: " + serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static XOServer makeNewInstance(int serverPort){
        return instance = new XOServer(serverPort);
    }

    public static XOServer getInstance(){
        return instance;
    }

    public static void login(String username, String password, ClientHandler clientHandler) throws XOException {
        Data.checkAccount(username, password);
        DataBase.save();
        Mapper.sendLogin(username, clientHandler);
    }

    public static void register(String username, String password, ClientHandler clientHandler) throws XOException {
        Data.addAccount(username, password);
        DataBase.save();
        Mapper.sendRegister(username, clientHandler);
    }

    public static void logout(ClientHandler clientHandler){
        DataBase.save();
        Mapper.sendLogout(clientHandler);
    }

    public synchronized void newGame(ClientHandler clientHandler){
        System.out.println("waiting games size start: " + waitingForGame.size());
        waitingForGame.add(clientHandler);
        if(waitingForGame.size() >= 2){
            System.out.println("new Game Debugger : -2 ");
            ClientHandler clientHandler0 = waitingForGame.remove(waitingForGame.size() - 1);
            ClientHandler clientHandler1 = waitingForGame.remove(0);

            System.out.println("new Game Debugger : -1 ");

            Player player0 = clientHandler0.getPlayer('X', true);
            Player player1 = clientHandler1.getPlayer('O', false);

            System.out.println("new Game Debugger : 0 ");

            Game game = new Game();
            games.put(clientHandler0, game);
            games.put(clientHandler1, game);

            System.out.println("new Game Debugger : 1 ");

            Mapper.sendNewGame(player0, player1, clientHandler0);

            System.out.println("new Game Debugger : 2 ");

            Mapper.sendNewGame(player1, player0, clientHandler1);

            System.out.println("new Game Debugger : 3 ");
        }
        System.out.println("waiting games size end: " + waitingForGame.size());
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(this, socket);
                clients.add(clientHandler);
                clientHandler.start();

                System.out.println("New Client Added :" + socket.getRemoteSocketAddress().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void removeClientHandler(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        clientHandler.disconnect();
    }
}
