package xo.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import xo.data.Data;
import xo.data.DataBase;
import xo.model.Packet;
import xo.model.Player;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler extends Thread{
    private XOServer server;
    private Socket socket;

    public ClientHandler(XOServer server, Socket socket){
        this.server = server;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(socket.getInputStream());
            while (true){
                System.out.println("started to read ... : ");
                String message = scanner.nextLine();
                System.out.println(message);

                Packet object = Data.getGson().fromJson(message, Packet.class);
                System.out.println("received obj: " + object);
                System.out.println("username: " +  object.getFunctionName());
                System.out.println("username: " +  object.getArgs().length);
                System.out.println("arg1: " +  object.getArgs()[0]);
                System.out.println("arg2: " +  object.getArgs()[1]);
                System.out.println("arg3: " +  object.getArgs()[2]);

                int integer =  (int)object.getArgs()[0];
                System.out.println("int: " + integer);

                String string = (String) object.getArgs()[1];
                System.out.println("string: " + string);

                char[][] characters = (char[][])object.getArgs()[2];

                //System.out.println("cast: " + (Player)object);

               /* ObjectMapper mapper = DataBase.getMapper();
                Object object = mapper.readValue(message, Object.class);*/
            }
        } catch (IOException ioException){
            ioException.printStackTrace();
        }
    }
}
