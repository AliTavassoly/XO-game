package xo.client;

public class Main {
    static String serverIP = "localhost";
    static int serverPort = 8000;

    public static XOClient client;

    public static void main(String[] args) {
        client = XOClient.makeInstance(serverIP, serverPort);
        client.start();
    }
}
