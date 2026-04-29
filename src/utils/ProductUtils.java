package utils;

public class ProductUtils {
    private static int currentId = 1;

    public static int getNextId(){
        int idToReturn = currentId;
        currentId++;
        return idToReturn;
    }
}
