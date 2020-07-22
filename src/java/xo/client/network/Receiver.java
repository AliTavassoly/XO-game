package xo.client.network;

import com.fasterxml.jackson.databind.ObjectMapper;
import xo.client.Mapper;
import xo.data.Data;
import xo.model.Packet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Receiver extends Thread {
    private InputStream inputStream;

    public Receiver(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(inputStream);
            while (true) {
                System.out.println("client started to read ... : ");
                String message = scanner.nextLine();
                System.out.println(message);

                ObjectMapper mapper = Data.getMapper();

                Packet packet = (Packet) mapper.readValue(message, Object.class);

                Mapper.invokeFunction(packet);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
