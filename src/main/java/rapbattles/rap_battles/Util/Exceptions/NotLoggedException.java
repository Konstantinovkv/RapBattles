package rapbattles.rap_battles.Util.Exceptions;

public class NotLoggedException extends Exception {

    private String message;

    public NotLoggedException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
