package xo.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import xo.data.DataBase;
import xo.model.Player;
import xo.model.XOClient;
import xo.data.Data;
import xo.model.Packet;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientHandler extends Thread {
    private XOServer server;
    private Socket socket;
    private XOClient client;

    public void setClient(XOClient client){
        this.client = client;
    }

    public ClientHandler(XOServer server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(socket.getInputStream());
            while (socket.isConnected()) {
                System.out.println("server started to read client ... : ");
                String message = scanner.nextLine();
                System.out.println(message);

                ObjectMapper mapper = Data.getMapper();

                Packet packet = (Packet) mapper.readValue(message, Object.class);

                Mapper.invokeFunction(packet, this);
            }

            if (!socket.isConnected())
                XOServer.getInstance().removeClientHandler(this);
        } catch (IOException | NoSuchElementException ioException) {
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

    public void disconnect() {
        //if (client.currentAccount != null) {
            System.out.println(Data.getAccount(client.currentAccount.getUsername()));
            Data.getAccount(client.currentAccount.getUsername()).setAuthToken(0);
            DataBase.save();
        //}
    }

    public Player getPlayer(char shape, boolean isMyTurn){
        System.out.println("Debugger in getPlayer1: " + client);
        System.out.println("Debugger in getPlayer2: " + client.currentAccount);
        return new Player(client.currentAccount.getUsername(), shape, isMyTurn);
    }
}
