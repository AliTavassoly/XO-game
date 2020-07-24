package xo.client;

import xo.client.configs.Configs;
import xo.server.data.DataBase;

public class Main {
    static String serverIP = "localhost";
    static int serverPort = 8000;

    public static void main(String[] args) {
        try {
            Configs.loadConfigs();
        } catch (Exception e){
            e.printStackTrace();
        }
        XOClient.makeNewInstance(serverIP, serverPort).start();
    }
}
