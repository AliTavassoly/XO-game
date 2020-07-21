package client;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class ClientSender extends Thread{
    private PrintStream printStream;

    public ClientSender(PrintStream printStream){
        this.printStream = printStream;
    }

    @Override
    public void run() {
        while(!isInterrupted()) {

        }
    }
}
