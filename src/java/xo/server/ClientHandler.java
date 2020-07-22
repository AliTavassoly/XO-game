package xo.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import xo.data.Data;
import xo.model.Packet;
import xo.model.Player;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import java.lang.reflect.*;

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
                System.out.println("server started to read client ... : ");
                String message = scanner.nextLine();
                System.out.println(message);

                ObjectMapper mapper = Data.getMapper();

                Packet packet = (Packet)mapper.readValue(message, Object.class);

                Mapper.invokeFunction(packet);
            }
        } catch (IOException ioException){
            ioException.printStackTrace();
        }
    }

    public void sendPacket(Packet packet) {
        try {
            String objectString;

            objectString = Data.getMapper().writeValueAsString(packet);
            System.out.println(objectString);

            new PrintStream(socket.getOutputStream()).println(objectString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
