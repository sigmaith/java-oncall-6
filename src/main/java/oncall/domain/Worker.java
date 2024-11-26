package oncall.domain;

import java.util.Objects;
import oncall.exception.CustomException;
import oncall.exception.ErrorMessage;

public class Worker {
    private final String nickName;

    public Worker(final String nickName) {
        validateName(nickName);
        this.nickName = nickName;
    }

    // equals()와 hashCode() 재정의
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Worker worker = (Worker) obj;
        return Objects.equals(nickName, worker.nickName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickName);
    }
    //

    public static Worker from(final String nickName) {
        return new Worker(nickName);
    }

    public String getNickName() {
        return nickName;
    }

    private void validateName(final String nickName) {
        if (nickName.length() < 1 || nickName.length() > 5) {
            throw CustomException.from(ErrorMessage.INVALID_INPUT_ERROR);
        }
    }
}
