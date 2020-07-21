package client;

public class ClientMain {
    static String serverIP = "localhost";
    static int serverPort = 8000;

    public static XOClient client;

    public static void main(String[] args) {
        client = new XOClient(serverIP, serverPort);
        client.start();
    }
}
