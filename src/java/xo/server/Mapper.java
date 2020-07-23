package xo.server;

import xo.client.XOClient;
import xo.server.data.Data;
import xo.model.Account;
import xo.model.Packet;
import xo.model.Player;
import xo.util.XOException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Mapper {
    public static void loginRequest(String username, String password, ClientHandler clientHandler) {
        try {
            XOServer.login(username, password, clientHandler);
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
            XOServer.register(username, password, clientHandler);
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
        XOServer.logout(clientHandler);
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
