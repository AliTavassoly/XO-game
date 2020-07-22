package xo.server;

import xo.data.DataBase;

public class Main {
    static int serverPort = 8000;

    public static void main(String[] args) {
        try {
            DataBase.load();
        } catch (Exception e){
            e.printStackTrace();
        }
        XOServer.makeNewInstance(serverPort).start();
    }
}