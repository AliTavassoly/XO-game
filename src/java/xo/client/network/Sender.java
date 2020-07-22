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

            System.out.println("salam start");
            objectString = Data.getMapper().writeValueAsString(packet);
            System.out.println("salam end");

            System.out.println(objectString);

            printStream.println(objectString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
