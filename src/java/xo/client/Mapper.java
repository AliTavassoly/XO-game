package xo.client;

import xo.client.gui.panels.GamePanel;
import xo.model.Account;
import xo.model.Packet;
import xo.model.Player;
import xo.model.XOClient;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Mapper {
    public static void setCurrentAccount(Account account) {
        XOClient.getInstance().setCurrentAccount(account);
        System.out.println("auth from client: " + account.getAuthToken());
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

    public static void loginRequest(){
        XOClient.getInstance().login();
    }

    public static void logoutRequest(){
        XOClient.getInstance().logout();
    }

    public static void registerRequest(){
        XOClient.getInstance().register();
    }

    public static void newGameRequest(Player myPlayer, Player enemyPlayer){
        XOClient.getInstance().newGame(myPlayer, enemyPlayer);
    }

    public static void sendSetClient(XOClient client){
        Packet packet = new Packet("setClientRequest", new Object[]{client}); // edited
        XOClient.sendPacket(packet);
    }

    public static void sendNewGame(){
        Packet packet = new Packet("newGameRequest",
                null);
        XOClient.sendPacket(packet);
    }

    public static void sendMarkCell(Character character, int i, int j) {

    }

    public static void sendRegister(String username, String password) {
        Packet packet = new Packet("registerRequest",
                new Object[]{username, password});
        XOClient.sendPacket(packet);
    }

    public static void sendLogin(String username, String password) {
        Packet packet = new Packet("loginRequest",
                new Object[]{username, password});
        XOClient.sendPacket(packet);
    }

    public static void sendLogout() {
        Packet packet = new Packet("logoutRequest", null);
        packet.setAuthToken(XOClient.getInstance().currentAccount.getAuthToken());
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
