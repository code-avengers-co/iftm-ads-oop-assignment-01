package utils;

public class CreationIdUtils {
    private static int nextProductId = 1;
    private static int nextPersonId = 1;
    private static int nextUserId = 1;

    public static int generateProductId(){
        int idToReturn = nextProductId;
        nextProductId++;
        return idToReturn;
    }

    public static int generatePersonId(){
        int idToReturn = nextPersonId;
        nextPersonId++;
        return idToReturn;
    }

    public static int generateUserId(){
        int idToReturn = nextUserId;
        nextUserId++;
        return idToReturn;
    }
}
