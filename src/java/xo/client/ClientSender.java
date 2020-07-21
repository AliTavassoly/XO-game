package xo.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import xo.model.Player;

import java.io.PrintStream;

public class ClientSender extends Thread{
    private PrintStream printStream;

    public ClientSender(PrintStream printStream){
        this.printStream = printStream;
    }

    @Override
    public void run() {
        while(!isInterrupted()) {
            System.out.println("started to send... :");
            Player player = new Player(ClientMain.client.toString(), 'X', true);
            sendObject(player);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendObject(Object object){
        GsonBuilder gsonBuilder = new GsonBuilder();
        //gsonBuilder.registerTypeAdapter(Player.class, new AbstractAdapter<Player>());
        Gson gson = gsonBuilder.create();
        String jsonObject = gson.toJson(object);
        printStream.println(jsonObject);
    }
}
