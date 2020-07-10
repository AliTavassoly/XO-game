package server;

public class ServerMapper {
    private static ServerMapper instance;

    private ServerMapper(){

    }

    public static ServerMapper getInstance(){
        if(instance == null)
            instance = new ServerMapper();
        return instance;
    }
}
