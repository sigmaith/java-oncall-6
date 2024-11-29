package oncall.domain;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import oncall.exception.CustomException;
import oncall.exception.ErrorMessage;

public class Workers {
    private final Deque<Worker> workers;

    public static Workers from(final List<String> workerNames) {
        return new Workers(workerNames);
    }

    private Workers(final List<String> workerNames) {
        validateNoDuplicates(workerNames);
        validateLength(workerNames);
        this.workers = new ArrayDeque<>(workerNames.stream().map(Worker::from).toList());
    }

    private void validateNoDuplicates(final List<String> workerNames) {
        List<String> workerNamesDistinct = workerNames.stream().distinct().toList();
        if (workerNamesDistinct.size() != workerNames.size()) {
            throw CustomException.from(ErrorMessage.INVALID_INPUT);
        }
    }

    private void validateLength(final List<String> workerNames) {
        if (workerNames.size() < 5 || 35 < workerNames.size()) {
            throw CustomException.from(ErrorMessage.INVALID_INPUT);
        }
    }

    public static void validateWeekdayAndWeekend(final Workers weekday, final Workers weekend) {
        Set<Worker> weekdaySet = new HashSet<>(weekday.getWorkers());
        Set<Worker> weekendSet = new HashSet<>(weekend.getWorkers());
        if (!Objects.equals(weekdaySet, weekendSet)) {
            throw CustomException.from(ErrorMessage.INVALID_INPUT);
        }
    }

    private Deque<Worker> getWorkers() {
        return workers;
    }
}
