package xo.server;

import xo.server.data.DataBase;

import java.io.File;
import java.util.Scanner;

public class Main {
    static int serverPort = 8000;

    public static void main(String[] args) {
        try {
            makePort(args);

            DataBase.load();
        } catch (Exception e){
            e.printStackTrace();
        }
        XOServer.makeNewInstance(serverPort).start();
    }

    private static void makePort(String[] args) {
        if(args.length == 0)
            return;

        File portFile = new File(args[0]);
        if(!portFile.exists())
            return;

        try (Scanner fileReader = new Scanner(portFile)){
            String portString = fileReader.nextLine();
            if (portString != null && portString.length() > 0 && portString.length() < 10){
                serverPort = Integer.valueOf(portString);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}