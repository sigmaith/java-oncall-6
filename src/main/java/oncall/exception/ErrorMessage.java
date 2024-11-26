package oncall.exception;

public enum ErrorMessage {
    INVALID_INPUT_ERROR("유효하지 않은 입력값입니다. 다시 입력해주세요.");
    private final String message;

    ErrorMessage(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
