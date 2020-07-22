package xo.server;

import xo.model.Packet;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Mapper {
    public static void login(String username, String password, ClientHandler clientHandler){

    }

    public static void register(String username, String password, ClientHandler clientHandler){

    }

    public static void logout(ClientHandler clientHandler){
    }

    public static void invokeFunction(Packet packet) {
        for (Method method : xo.client.Mapper.class.getMethods()) {
            if (method.getName().equals(packet.getFunctionName())) {
                try {
                    method.invoke(null, packet.getArgs());
                    break;
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
