package xo.server;

import xo.model.Player;
import xo.server.data.Data;
import xo.server.data.DataBase;
import xo.server.logic.Game;
import xo.util.AuthToken;
import xo.util.XOException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class XOServer extends Thread {
    private static XOServer instance;
    private ServerSocket serverSocket;

    private Map<String, String> keys;
    private ArrayList<String> waitingForGame;

    private XOServer(int serverPort) {
        try {
            this.serverSocket = new ServerSocket(serverPort);
            this.keys = new HashMap<>();
            this.waitingForGame = new ArrayList<>();

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
        Data.getAccountDetails(username).getAccount().setAuthToken(AuthToken.generateAuthToken());
        Data.getAccountDetails(username).setClientHandler(clientHandler);

        XOServer.getInstance().addAccountToServer(username,
                Data.getAccountDetails(username).getAccount().getAuthToken(),
                clientHandler);

        Mapper.loginResponse(username, clientHandler);
    }

    public static void register(String username, String password, ClientHandler clientHandler) throws XOException {
        Data.addAccount(username, password);
        Data.getAccountDetails(username).getAccount().setAuthToken(AuthToken.generateAuthToken());
        Data.getAccountDetails(username).setClientHandler(clientHandler);

        XOServer.getInstance().addAccountToServer(username, Data.getAccountDetails(username).getAccount().getAuthToken(),
                clientHandler);

        DataBase.save();

        Mapper.registerResponse(username, clientHandler);
    }

    public static void logout(ClientHandler clientHandler){
        Mapper.logoutResponse(clientHandler);
    }

    public synchronized void addNewGameWaiter(String username, ClientHandler clientHandler){
        waitingForGame.add(username);
        if(waitingForGame.size() >= 2){

            String user0 = waitingForGame.remove(0);
            String user1 = waitingForGame.remove(0);

            Player player0 = new Player(user0, 'X', true);
            Player player1 = new Player(user1, 'O', false);

            Game game = new Game(player0, player1);

            Data.getAccountDetails(user0).setCurrentGame(game);
            Data.getAccountDetails(user1).setCurrentGame(game);

            Mapper.newGameResponse(player0, player1, Data.getAccountDetails(player0.getUsername()).getClientHandler());
            Mapper.newGameResponse(player1, player0, Data.getAccountDetails(player1.getUsername()).getClientHandler());
        }
    }

    public synchronized void removeGameWaiter(String username){
        waitingForGame.remove(username);
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
                //clients.add(clientHandler);
                clientHandler.start();

                System.out.println("New Client Added :" + socket.getRemoteSocketAddress().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void removeClientHandler(String authToken) {
        String username = keys.get(authToken);
        Data.getAccountDetails(username).setClientHandler(null);
        Data.getAccountDetails(username).setCurrentGame(null);
    }

    public synchronized void addAccountToServer(String username, String authToken, ClientHandler clientHandler){
        clientHandler.setAuthToken(authToken);
        XOServer.getInstance().keys.put(authToken, username);
    }

    public synchronized void checkAuthToken(String autoToken) throws XOException{
        if(autoToken != null && !keys.containsKey(autoToken))
            throw new XOException("AuthToken is not correct!");
    }

    public void markCell(char character, int i, int j, ClientHandler clientHandler) {
        Game game = Data.getAccountDetails(keys.get(clientHandler.getAuthToken())).getCurrentGame();
        try {
            game.markCell(character, i, j);

            String user0 = game.getPlayer0().getUsername();
            String user1 = game.getPlayer1().getUsername();

            Mapper.markCellResponse(game.getBoard(), Data.getAccountDetails(user0).getClientHandler());
            Mapper.markCellResponse(game.getBoard(), Data.getAccountDetails(user1).getClientHandler());
        } catch (XOException e){
            e.printStackTrace();
        }
    }
}
