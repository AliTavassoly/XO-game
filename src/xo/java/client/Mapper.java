package client;

public class Mapper {
    private static Mapper instance;

    private Mapper(){ }

    public static Mapper getInstance(){
        if(instance == null)
            instance = new Mapper();
        return instance;
    }

    public void markCell(Character character, int i, int j){

    }
}
