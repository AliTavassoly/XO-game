package client;

public class ClientMapper {
    private static ClientMapper instance;

    private ClientMapper(){ }

    public static ClientMapper getInstance(){
        if(instance == null)
            instance = new ClientMapper();
        return instance;
    }

    public void markCell(Character character, int i, int j){

    }
}
