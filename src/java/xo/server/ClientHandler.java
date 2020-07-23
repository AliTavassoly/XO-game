package xo.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import xo.client.XOClient;
import xo.model.Player;
import xo.server.data.Data;
import xo.model.Packet;
import xo.util.XOException;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler extends Thread {
    private Socket socket;
    private String authToken;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
    public String getAuthToken() {
        return this.authToken;
    }

    public ClientHandler(){}

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(socket.getInputStream());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        System.out.println("server started to read client ... : ");

        try {
            while (true) {
                String message = scanner.nextLine();

                Packet packet = getPacket(message);

                try {
                    if (authToken != null)
                        XOServer.getInstance().checkAuthToken(packet.getAuthToken());
                } catch (XOException e) {
                    e.printStackTrace();
                    continue;
                }

                Mapper.invokeFunction(packet, this);
            }
        } catch (Exception e) {
            XOServer.getInstance().removeClientHandler(authToken); // ?????
            e.printStackTrace();
        }
    }

    public void sendPacket(Packet packet) {
        try {
            String objectString;

            objectString = Data.getNetworkMapper().writeValueAsString(packet);

            new PrintStream(socket.getOutputStream()).println(objectString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Packet getPacket(String message) {
        ObjectMapper mapper = Data.getNetworkMapper();
        Packet packet = null;
        try {
            packet = mapper.readValue(message, Packet.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return packet;
    }
}
