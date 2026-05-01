package utils;

public class CreationIdUtils {
    private static int nextProductId = 1;

    public static int getNextProductId(){
        int idToReturn = nextProductId;
        nextProductId++;
        return idToReturn;
    }
}
