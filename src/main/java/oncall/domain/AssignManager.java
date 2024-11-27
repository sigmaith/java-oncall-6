package oncall.domain;

import java.time.Month;
import oncall.domain.constants.CustomDayOfWeek;
import oncall.domain.constants.Holiday;

public class AssignManager {
    private final Workers weekday;
    private final Workers weekend;
    private Worker mustUsedWeekday = null;
    private Worker mustUsedWeekend = null;

    public AssignManager(final Workers weekday, final Workers weekend) {
        this.weekday = weekday;
        this.weekend = weekend;
    }

    public Workers assign(final Month month, final CustomDayOfWeek startDayOfWeek) {
        Workers assignedWorkers = new Workers();
        int criteria = startDayOfWeek.ordinal();
        for (int date = 1; date <= month.minLength(); date++) {
            CustomDayOfWeek day = CustomDayOfWeek.from((criteria + (date - 1)) % 7);
            if (!day.isWeekend() && !Holiday.isHoliday(month, date)) { // 평일인 경우
                assignWhenWeekdays(assignedWorkers);
            }
            if (day.isWeekend() || Holiday.isHoliday(month, date)) { // 토,일,공휴일확인
                assignWhenWeekend(assignedWorkers);
            }
        }
        return assignedWorkers;
    }

    private void assignWhenWeekdays(final Workers assignedWorkers) {
        if (assignedWorkers.isEmtpy() || !isDuplicatedWeekdayWorker(assignedWorkers)) { // 비어있거나 중복되지 않을 때
            if (mustUsedWeekday == null) { // 당장 배정해야하는 사람이 없으면
                Worker worker = weekday.pollFirstWorker();
                assignedWorkers.offerLastWorker(worker);
                weekday.offerLastWorker(worker);
                return;
            }
            assignedWorkers.offerLastWorker(mustUsedWeekday);
            mustUsedWeekday = null;
            return;
        }
        processWhenContinuousWorkWeekday(assignedWorkers); // 중복될때
    }

    private boolean isDuplicatedWeekdayWorker(final Workers assignedWorkers) {
        return assignedWorkers.peekLastWorker().equals(weekday.peekFirstWorker());
    }

    private void processWhenContinuousWorkWeekday(final Workers assignedWorkers) {
        Worker duplicated = weekday.pollFirstWorker();
        Worker tmp = weekday.pollFirstWorker();
        assignedWorkers.offerLastWorker(tmp);
        mustUsedWeekday = duplicated;
        weekday.offerLastWorker(duplicated);
        weekday.offerLastWorker(tmp);
    }

    private void assignWhenWeekend(final Workers assignedWorkers) {
        if (assignedWorkers.isEmtpy() || !isDuplicatedWeekendWorker(assignedWorkers)) {
            if (mustUsedWeekend == null) {
                Worker worker = weekend.pollFirstWorker();
                assignedWorkers.offerLastWorker(worker);
                weekend.offerLastWorker(worker);
                return;
            }
            assignedWorkers.offerLastWorker(mustUsedWeekend);
            mustUsedWeekday = null;
            return;
        }
        processWhenContinuousWorkWeekend(assignedWorkers); // 중복될때
    }

    private boolean isDuplicatedWeekendWorker(final Workers assignedWorkers) {
        return assignedWorkers.peekLastWorker().equals(weekend.peekFirstWorker());
    }

    private void processWhenContinuousWorkWeekend(final Workers assignedWorkers) {
        Worker duplicated = weekend.pollFirstWorker();
        Worker tmp = weekend.pollFirstWorker();
        assignedWorkers.offerLastWorker(tmp);
        mustUsedWeekend = duplicated;
        weekend.offerLastWorker(duplicated);
        weekend.offerLastWorker(tmp);
    }
}
