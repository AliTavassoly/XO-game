package xo.server;

public class Main {
    static int serverPort = 8000;

    public static void main(String[] args) {
        new XOServer(serverPort).start();
    }
}