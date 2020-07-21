package server;

import data.Data;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class XOServer extends Thread {
    private ArrayList<ClientHandler> clients;
    private ServerSocket serverSocket;

    public XOServer(int serverPort) {
        try {
            this.serverSocket = new ServerSocket(serverPort);
            this.clients = new ArrayList<>();

            System.out.println("Server Started at: " + serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void login(String username, String password) throws Exception {
        Data.checkAccount(username, password);
    }

    public static void register(String username, String password) throws Exception {
        Data.addAccount(username, password);
    }

    public static void logout(){

    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(this, socket);
                clients.add(clientHandler);

                System.out.println("New Client Added :" + socket.getRemoteSocketAddress().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
