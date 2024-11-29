package oncall.exception;

public class CustomException extends IllegalArgumentException {
    private CustomException(ErrorMessage message) {
        super(message.getMessage());
    }

    public static CustomException from(ErrorMessage message) {
        return new CustomException(message);
    }
}
