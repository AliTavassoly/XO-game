package xo.client.network;

import xo.data.Data;

import java.io.PrintStream;

public class Sender{
    private PrintStream printStream;

    public Sender(PrintStream printStream) {
        this.printStream = printStream;
    }

    public void sendPacket(Object object) {
        try {
            String objectString;

            objectString = Data.getMapper().writeValueAsString(object);
            System.out.println(objectString);

            printStream.println(objectString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
