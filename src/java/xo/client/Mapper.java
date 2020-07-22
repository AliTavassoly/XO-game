package xo.client;

import xo.client.gui.panels.GamePanel;
import xo.model.Account;
import xo.model.Packet;
import xo.model.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Mapper {
    public static void markCell(Character character, int i, int j) {

    }

    public static void setCurrentAccount(Account account) {
        XOClient.getInstance().setCurrentAccount(account);
    }

    public static void setCurrentPlayer(Player player) {
        XOClient.getInstance().setCurrentPlayer(player);
    }

    public static void setCurrentGamePanel(GamePanel gamePanel) {
        XOClient.getInstance().setCurrentGamePanel(gamePanel);
    }

    public static void updateGame(char[][] newBoard) {
        XOClient.getInstance().getCurrentGamePanel().updateGame(newBoard);
    }

    public static void register(String username, String password) {
        Packet packet = new Packet("register",
                new Object[]{username, password});
        XOClient.sendPacket(packet);
    }

    public static void login(String username, String password) {
        Packet packet = new Packet("login",
                new Object[]{username, password});
        XOClient.sendPacket(packet);
    }

    public static void logout() {
        Packet packet = new Packet("login", null);
        XOClient.sendPacket(packet);
    }

    public static void invokeFunction(Packet packet) {
        for (Method method : Mapper.class.getMethods()) {
            if (method.getName().equals(packet.getFunctionName())) {
                try {
                    method.invoke(null, packet.getArgs());
                    break;
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
