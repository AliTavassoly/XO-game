package xo.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import xo.data.Data;
import xo.data.DataBase;
import xo.model.Packet;
import xo.model.Player;

import java.io.PrintStream;

public class ClientSender extends Thread {
    private PrintStream printStream;

    public ClientSender(PrintStream printStream) {
        this.printStream = printStream;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            System.out.println("started to send... :");
            //Player player = new Player(ClientMain.client.toString(), 'X', true);

            Packet packet = new Packet("login", new Object[]{5, "a",
                    new char[][] {{'X', 'O'}, {'X', 'O'}}});

            sendObject(packet);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendObject(Object object) {
        ObjectMapper mapper = Data.getMapper();
        String objectString = null;

        try {
            /*objectString = mapper.writeValueAsString(object);
            System.out.println(objectString);*/

            objectString = Data.getGson().toJson(object);
            System.out.println(objectString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        printStream.println(objectString);
    }
}
