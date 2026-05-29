package hemija.app;

public class UserSession {
    private static int loggedInId;

    public static void setLoggedInId(int id) {
        loggedInId = id;
    }

    public static int getLoggedInId() {
        return loggedInId;
    }
}
