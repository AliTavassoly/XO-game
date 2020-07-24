package xo.server;

import xo.client.XOClient;
import xo.model.AccountInfo;
import xo.server.data.Data;
import xo.model.Account;
import xo.model.Packet;
import xo.model.Player;
import xo.util.XOException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Mapper {
    public static void loginRequest(String username, String password, ClientHandler clientHandler) {
        try {
            XOServer.getInstance().login(username, password, clientHandler);
        } catch (XOException e) {
            e.printStackTrace();
        }
    }

    public static void loginResponse(String username, ClientHandler clientHandler){
        Account account = Data.getAccountDetails(username).getAccount();

        Packet packet = new Packet("loginResponse", new Object[]{account});
        clientHandler.sendPacket(packet);
    }

    public static void registerRequest(String username, String password, ClientHandler clientHandler) {
        try {
            XOServer.getInstance().register(username, password, clientHandler);
        } catch (XOException e) {
            e.printStackTrace();
        }
    }

    public static void registerResponse(String username, ClientHandler clientHandler) {
        Account account = Data.getAccountDetails(username).getAccount();

        Packet packet = new Packet("registerResponse", new Object[]{account});
        clientHandler.sendPacket(packet);
    }

    public static void logoutRequest(ClientHandler clientHandler) {
        XOServer.getInstance().logout(clientHandler);
    }

    public static void logoutResponse(ClientHandler clientHandler) {
        Packet packet = new Packet("logoutResponse", new Object[]{});
        clientHandler.sendPacket(packet);
    }

    public static void newGameRequest(String username, ClientHandler clientHandler){
        XOServer.getInstance().addNewGameWaiter(username, clientHandler);
    }

    public static void newGameResponse(Player myPlayer, Player enemyPlayer, ClientHandler clientHandler){
        Packet packet = new Packet("newGameResponse", new Object[]{myPlayer, enemyPlayer});
        clientHandler.sendPacket(packet);
    }

    public static void markCellRequest(char character, int i, int j, ClientHandler clientHandler) {
        XOServer.getInstance().markCell(character, i, j, clientHandler);
    }

    public static void markCellResponse(char[][] board, ClientHandler clientHandler) {
        Packet packet = new Packet("markCellResponse", new Object[]{board});
        clientHandler.sendPacket(packet);
    }

    public static void cancelWaitingForGameRequest(ClientHandler clientHandler){
        XOServer.getInstance().cancelWaitingForGame(clientHandler);
    }

    public static void cancelWaitingForGameResponse(ClientHandler clientHandler) {
        Packet packet = new Packet("cancelWaitingForGameResponse", new Object[]{});
        clientHandler.sendPacket(packet);
    }

    public static void startUpdateAccountInfo(ClientHandler clientHandler){
        XOServer.getInstance().startUpdateAccountInfo(clientHandler);
    }

    public static void updateAccountInfo(ArrayList<AccountInfo> accounts, ClientHandler clientHandler){
        Packet packet = new Packet("updateAccountInfo", new Object[]{accounts});
        clientHandler.sendPacket(packet);
    }

    public static void stopUpdateAccountInfo(ClientHandler clientHandler){
        XOServer.getInstance().stopUpdateAccountInfo(clientHandler);
    }

    public static void updateAccountResponse(Account account, ClientHandler clientHandler) {
        Packet packet = new Packet("updateAccountResponse", new Object[]{account});
        clientHandler.sendPacket(packet);
    }

    public static void statusRequest(ClientHandler clientHandler) {
        XOServer.getInstance().statusRequest(clientHandler);
    }

    public static void statusResponse(Account account, ClientHandler clientHandler) {
        Packet packet = new Packet("updateAccountResponse", new Object[]{account});
        clientHandler.sendPacket(packet);

        packet = new Packet("statusResponse", new Object[]{});
        clientHandler.sendPacket(packet);
    }

    public static void surrenderRequest(ClientHandler clientHandler){
        XOServer.getInstance().surrender(clientHandler);
    }

    public static void lostGame(ClientHandler clientHandler){
        Packet packet = new Packet("lostGame", new Object[]{});
        clientHandler.sendPacket(packet);
    }

    public static void wonGame(ClientHandler clientHandler){
        Packet packet = new Packet("wonGame", new Object[]{});
        clientHandler.sendPacket(packet);
    }

    public static void waitForGame(ClientHandler clientHandler){
        Packet packet = new Packet("waitForGame", new Object[]{});
        clientHandler.sendPacket(packet);
    }

    public static void invokeFunction(Packet packet, ClientHandler clientHandler) {
        for (Method method : Mapper.class.getMethods()) {
            if (method.getName().equals(packet.getFunctionName())) {
                try {
                    method.invoke(null, addElementToArray(packet.getArgs(), clientHandler));
                    break;
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Object[] addElementToArray(Object[] oldArgs, Object object) {
        Object[] newArgs;
        if (oldArgs != null) {
            newArgs = new Object[oldArgs.length + 1];
            for (int i = 0; i < oldArgs.length; i++)
                newArgs[i] = oldArgs[i];
            newArgs[newArgs.length - 1] = object;
        } else {
            newArgs = new Object[1];
            newArgs[0] = object;
        }
        return newArgs;
    }
}
