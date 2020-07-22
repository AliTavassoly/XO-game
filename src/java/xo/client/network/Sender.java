package xo.client.network;

import xo.data.Data;
import xo.model.Packet;

import java.io.PrintStream;

public class Sender{
    private PrintStream printStream;

    public Sender(PrintStream printStream) {
        this.printStream = printStream;
    }

    public void sendPacket(Packet packet) {
        try {
            String objectString;

            objectString = Data.getMapper().writeValueAsString(packet);
            System.out.println(objectString);

            printStream.println(objectString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
