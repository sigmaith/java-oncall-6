package oncall.domain;

import java.util.Objects;
import oncall.exception.CustomException;
import oncall.exception.ErrorMessage;

public class Worker {
    private final String nickName;

    private Worker(final String nickName) {
        validateLength(nickName);
        this.nickName = nickName;
    }

    public static Worker from(final String nickName) {
        return new Worker(nickName);
    }

    private void validateLength(final String nickName) {
        if (nickName.length() < 1 || 5 < nickName.length()) {
            throw CustomException.from(ErrorMessage.INVALID_INPUT);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Worker worker)) {
            return false;
        }
        return Objects.equals(nickName, worker.nickName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickName);
    }
}
