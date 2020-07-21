package server;

import java.net.Socket;

public class ClientHandler extends Thread{
    private XOServer server;
    private Socket socket;

    public ClientHandler(XOServer server, Socket socket){
        this.server = server;
        this.socket = socket;
    }

    @Override
    public void run() {

    }
}
