package xo.server;

import xo.data.Data;
import xo.data.DataBase;
import xo.model.Account;
import xo.model.Packet;
import xo.model.Player;
import xo.model.XOClient;
import xo.util.XOException;

import java.lang.ref.Cleaner;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.SecureRandom;

public class Mapper {
    public static void loginRequest(String username, String password, ClientHandler clientHandler) {
        try {
            XOServer.login(username, password, clientHandler);
        } catch (XOException e) {
            e.printStackTrace();
        }
    }

    public static void registerRequest(String username, String password, ClientHandler clientHandler) {
        try {
            XOServer.register(username, password, clientHandler);
        } catch (XOException e) {
            e.printStackTrace();
        }
    }

    public static void logoutRequest(ClientHandler clientHandler) {
        XOServer.logout(clientHandler);
    }

    public static void newGameRequest(ClientHandler clientHandler){
        XOServer.getInstance().newGame(clientHandler);
    }

    public static void setClientRequest(XOClient client, ClientHandler clientRequest){
        clientRequest.setClient(client);
    }

    public static void sendLogin(String username, ClientHandler clientHandler) {
        Account account = Data.getAccount(username);
        account.setAuthToken(new SecureRandom().nextInt());
        DataBase.save();

        System.out.println("auth from server: " + account.getAuthToken());

        Packet packet = new Packet("setCurrentAccount", new Object[]{account});
        clientHandler.sendPacket(packet);

        packet = new Packet("loginRequest", new Object[]{});
        clientHandler.sendPacket(packet);
    }

    public static void sendRegister(String username, ClientHandler clientHandler) {
        Account account = Data.getAccount(username);
        account.setAuthToken(new SecureRandom().nextInt());

        Packet packet = new Packet("setCurrentAccount", new Object[]{account});
        clientHandler.sendPacket(packet);

        packet = new Packet("registerRequest", new Object[]{});
        clientHandler.sendPacket(packet);
    }

    public static void sendLogout(ClientHandler clientHandler) {
        Packet packet = new Packet("setCurrentAccount", new Object[]{null});
        clientHandler.sendPacket(packet);

        packet = new Packet("logoutRequest", new Object[]{});
        clientHandler.sendPacket(packet);
    }

    public static void sendNewGame(Player myPlayer, Player enemyPlayer, ClientHandler clientHandler){
        Packet packet = new Packet("newGameRequest", new Object[]{myPlayer, enemyPlayer});
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
