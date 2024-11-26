package oncall.domain;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import oncall.exception.CustomException;
import oncall.exception.ErrorMessage;

public class Workers {
    private final Deque<Worker> workers;

    public Workers() {
        this.workers = new ArrayDeque<>();
    }

    public Workers(final Deque<Worker> workers) {
        validateNoDuplicates(workers);
        validateNumberOfWorkers(workers);
        this.workers = workers;
    }

    public static Workers from(final List<String> rawWorkers) {
        List<Worker> workers = rawWorkers.stream().map(Worker::from).toList();
        return new Workers(new ArrayDeque<>(workers));
    }

    public void validateContains(final Workers workers) {
        Set<Worker> criteria = new HashSet<>(this.workers);
        Set<Worker> target = new HashSet<>(workers.getWorkers());
        if (!criteria.equals(target)) {
            throw CustomException.from(ErrorMessage.INVALID_INPUT_ERROR);
        }
    }

    public boolean isEmtpy() {
        return workers.isEmpty();
    }

    public void addWorker(final Worker worker) {
        workers.addLast(worker);
    }

    private Deque<Worker> getWorkers() {
        return workers;
    }

    private void validateNumberOfWorkers(final Deque<Worker> workers) {
        if (workers.size() < 5 || workers.size() > 35) {
            throw CustomException.from(ErrorMessage.INVALID_INPUT_ERROR);
        }
    }

    private void validateNoDuplicates(final Deque<Worker> workers) {
        // 아 worker는 equals로 중복검사가 안된다...
        // sol1) String일때 중복검사 해야됨아니면 string 빼내서 하던가
        // sol2) Worker에 equals, hashCode재정의 시 해결됨
        Set<Worker> workersNotDuplicated = new HashSet<>(workers);
        if (workersNotDuplicated.size() != workers.size()) {
            throw CustomException.from(ErrorMessage.INVALID_INPUT_ERROR);
        }
    }
}
