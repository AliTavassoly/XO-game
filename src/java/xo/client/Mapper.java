package xo.client;

import xo.client.gui.panels.GamePanel;
import xo.model.Account;
import xo.model.AccountInfo;
import xo.model.Packet;
import xo.model.Player;
import xo.server.ClientHandler;
import xo.server.XOServer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Mapper {
    public static void setCurrentGamePanel(GamePanel gamePanel) {
        XOClient.getInstance().setCurrentGamePanel(gamePanel);
    }

    public static void updateGameRequest(char[][] newBoard) {
        XOClient.getInstance().updateBoard(newBoard);
    }

    public static void newGameRequest(String username){
        Packet packet = new Packet("newGameRequest",
                new Object[]{username});
        packet.setAuthToken(XOClient.getInstance().currentAccount.getAuthToken());
        XOClient.sendPacket(packet);
    }

    public static void newGameResponse(Player myPlayer, Player enemyPlayer){
        XOClient.getInstance().newGame(myPlayer, enemyPlayer);
    }

    public static void markCellRequest(char character, int i, int j) {
        Packet packet = new Packet("markCellRequest",
                new Object[]{character, i, j});
        XOClient.sendPacket(packet);
    }

    public static void markCellResponse(char [][] board){
        XOClient.getInstance().afterMarkingCell(board);
    }

    public static void registerRequest(String username, String password) {
        Packet packet = new Packet("registerRequest",
                new Object[]{username, password});
        XOClient.sendPacket(packet);
    }

    public static void registerResponse(Account account){
        XOClient.getInstance().register(account);
    }

    public static void loginRequest(String username, String password) {
        Packet packet = new Packet("loginRequest",
                new Object[]{username, password});
        XOClient.sendPacket(packet);
    }

    public static void loginResponse(Account account){
        XOClient.getInstance().login(account);
    }

    public static void logoutRequest() {
        Packet packet = new Packet("logoutRequest", null);
        packet.setAuthToken(XOClient.getInstance().currentAccount.getAuthToken());
        XOClient.sendPacket(packet);
    }

    public static void logoutResponse(){
        XOClient.getInstance().logout();
    }

    public static void cancelWaitingForGameRequest() {
        Packet packet = new Packet("cancelWaitingForGameRequest", null);
        packet.setAuthToken(XOClient.getInstance().currentAccount.getAuthToken());
        XOClient.sendPacket(packet);
    }

    public static void cancelWaitingForGameResponse(){
        XOClient.getInstance().cancelWaitingForGame();
    }

    public static void updateAccountRequest(){
        Packet packet = new Packet("updateAccountRequest", null);
        packet.setAuthToken(XOClient.getInstance().currentAccount.getAuthToken());
        XOClient.sendPacket(packet);
    }

    public static void updateAccountResponse(Account account){
        XOClient.getInstance().updateAccount(account);
    }


    public static void startUpdateAccountInfo(){
        Packet packet = new Packet("startUpdateAccountInfo", null);
        packet.setAuthToken(XOClient.getInstance().currentAccount.getAuthToken());
        XOClient.sendPacket(packet);
    }

    public static void updateAccountInfo(ArrayList<AccountInfo> accountInfo){
        XOClient.getInstance().updateAccountInfo(accountInfo);
    }

    public static void stopUpdateAccountInfo(){
        Packet packet = new Packet("stopUpdateAccountInfo", null);
        packet.setAuthToken(XOClient.getInstance().currentAccount.getAuthToken());
        XOClient.sendPacket(packet);
    }

    public static void statusRequest() {
        Packet packet = new Packet("statusRequest", null);
        packet.setAuthToken(XOClient.getInstance().currentAccount.getAuthToken());
        XOClient.sendPacket(packet);
    }

    public static void statusResponse() {
        XOClient.getInstance().statusResponse();
    }

    public static void surrenderRequest(){
        Packet packet = new Packet("surrenderRequest", null);
        packet.setAuthToken(XOClient.getInstance().currentAccount.getAuthToken());
        XOClient.sendPacket(packet);
    }

    public static void wonGame(){
        XOClient.getInstance().wonGame();
    }

    public static void lostGame(){
        XOClient.getInstance().lostGame();
    }

    public static void waitForGame(){
        XOClient.getInstance().waitForGame();
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