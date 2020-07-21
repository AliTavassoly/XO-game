package server;

public class Mapper {
    private static Mapper instance;

    private Mapper(){

    }

    public static Mapper getInstance(){
        if(instance == null)
            instance = new Mapper();
        return instance;
    }
}
