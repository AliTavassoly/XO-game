package xo.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import xo.data.Data;
import xo.model.Packet;
import xo.model.Player;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import java.lang.reflect.*;

public class ClientHandler extends Thread{
    private XOServer server;
    private Socket socket;

    public ClientHandler(XOServer server, Socket socket){
        this.server = server;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(socket.getInputStream());
            while (true){
                System.out.println("server started to read client ... : ");
                String message = scanner.nextLine();
                System.out.println(message);

                ObjectMapper mapper = Data.getMapper();
                Object object = mapper.readValue(message, Object.class);
                //System.out.println();

                Packet packet = (Packet)object;

                for(Method method: this.getClass().getMethods()){
                    if(method.getName().equals(packet.getFunctionName())){
                        System.out.println("Method found ...");
                        try {
                            method.invoke(null, packet.getArgs());
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }

            }
        } catch (IOException ioException){
            ioException.printStackTrace();
        }
    }

    public void sendPacket(){

    }

    public static void temp(double a, String b, char[][] c, Player p){
        System.out.println("int temp: " + a);
        System.out.println("int temp: " + b);
        System.out.println("int temp: " + c[0][0]);
        System.out.println("int temp: " + p);
    }
}
