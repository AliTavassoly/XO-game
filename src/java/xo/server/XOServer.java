package xo.server;

import xo.model.Account;
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

    private Map<String, String> keys; // AuthToken / Username
    private ArrayList<String> waitRoom;

    private XOServer(int serverPort) {
        try {
            this.serverSocket = new ServerSocket(serverPort);
            this.keys = new HashMap<>();
            this.waitRoom = new ArrayList<>();

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
        Data.addAccountDetail(username, password);
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
        waitRoom.add(username);
        if(waitRoom.size() >= 2){

            String user0 = waitRoom.remove(0);
            String user1 = waitRoom.remove(0);

            Player player0 = new Player(user0, 'X', true);
            Player player1 = new Player(user1, 'O', false);

            Game game = new Game(player0, player1);

            Data.getAccountDetails(user0).setCurrentGame(game);
            Data.getAccountDetails(user1).setCurrentGame(game);

            Mapper.newGameResponse(player0, player1, Data.getAccountDetails(player0.getUsername()).getClientHandler());
            Mapper.newGameResponse(player1, player0, Data.getAccountDetails(player1.getUsername()).getClientHandler());
        } else {
            Mapper.waitForGame(clientHandler);
        }
    }

    public synchronized void removeGameWaiter(String authToken){
        waitRoom.remove(keys.get(authToken));
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

    public void clientHandlerDisconnected(String authToken) {
        String username = keys.get(authToken);
        Data.getAccountDetails(username).setClientHandler(null);
        Data.getAccountDetails(username).setCurrentGame(null);

        removeGameWaiter(authToken);
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

            afterMarkCell(game);
        } catch (XOException e){
            e.printStackTrace();
        }
    }

    public void afterMarkCell(Game game){
        String user0 = game.getPlayer0().getUsername();
        String user1 = game.getPlayer1().getUsername();

        ClientHandler clientHandler0 = Data.getAccountDetails(user0).getClientHandler();
        ClientHandler clientHandler1 = Data.getAccountDetails(user1).getClientHandler();

        if(game.isGameEnded()){
            if(game.whoWonGame() == game.getPlayer0().getShape())
                wonGame(clientHandler0);
            else
                lostGame(clientHandler0);

            if(game.whoWonGame() == game.getPlayer1().getShape())
                wonGame(clientHandler1);
            else
                lostGame(clientHandler1);
        }
    }

    public void cancelWaitingForGame(ClientHandler clientHandler) {
        removeGameWaiter(clientHandler.getAuthToken());
        Mapper.cancelWaitingForGameResponse(clientHandler);
    }

    public void surrender(ClientHandler clientHandler) {
        Game game = Data.getAccountDetails(keys.get(clientHandler.getAuthToken())).getCurrentGame();

        String user0 = game.getPlayer0().getUsername();
        String user1 = game.getPlayer1().getUsername();

        ClientHandler clientHandler0 = Data.getAccountDetails(user0).getClientHandler();
        ClientHandler clientHandler1 = Data.getAccountDetails(user1).getClientHandler();

        if(clientHandler.getAuthToken().equals(clientHandler0.getAuthToken())){
            lostGame(clientHandler0);
            wonGame(clientHandler1);
        } else {
            lostGame(clientHandler1);
            wonGame(clientHandler0);
        }
    }

    private void wonGame(ClientHandler clientHandler){
        Account account = Data.getAccountDetails(keys.get(clientHandler.getAuthToken())).getAccount();

        account.setTotalGames(account.getTotalGames() + 1);
        account.setWinGames(account.getWinGames() + 1);
        account.setPoint(account.getWinGames() - account.getLostGames());
        DataBase.save();

        Mapper.wonGame(clientHandler);
    }

    private void lostGame(ClientHandler clientHandler){
        Account account = Data.getAccountDetails(keys.get(clientHandler.getAuthToken())).getAccount();

        account.setTotalGames(account.getTotalGames() + 1);
        account.setLostGames(account.getLostGames() + 1);
        account.setPoint(account.getWinGames() - account.getLostGames());
        DataBase.save();

        Mapper.lostGame(clientHandler);
    }
}
