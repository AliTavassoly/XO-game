package xo.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import xo.model.Player;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler extends Thread{
    private XOServer server;
    private Socket socket;

    private Gson gson;

    public ClientHandler(XOServer server, Socket socket){
        this.server = server;
        this.socket = socket;

        makeGson();
    }

    private void makeGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        //gsonBuilder.registerTypeAdapter(Player.class, new AbstractAdapter<Player>());
        gson = gsonBuilder.create();
    }

    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(socket.getInputStream());
            while (true){
                System.out.println("started to read ... : ");
                String message = scanner.nextLine();
                System.out.println(message);
                /*Object object = gson.fromJson(message, Object.class);
                System.out.println("received obj: " + object);*/

                //System.out.println("cast: " + (Player)object);

                JsonParser parser = new JsonParser();
                Object object = parser.parse(message);
                System.out.println("object :" + object);
                System.out.println("cast :" + (Player)object);

            }
        } catch (IOException ioException){
            ioException.printStackTrace();
        }
    }
}
