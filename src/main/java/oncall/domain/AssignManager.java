package oncall.domain;

import java.time.Month;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;
import oncall.controller.dto.DateInfo;
import oncall.domain.constants.CustomDayOfWeek;
import oncall.domain.constants.Holiday;

public class AssignManager {
    private final Workers weekday;
    private final Workers weekend;
    private Worker weekdayMustAssigned = null;
    private Worker weekendMustAssigned = null;

    public AssignManager(final Workers weekday, final Workers weekend) {
        this.weekday = weekday;
        this.weekend = weekend;
    }

    public Workers assign(DateInfo dateInfo) {
        Deque<Worker> assignment = new ArrayDeque<>(); // 바로 new Workers 못함 (5명 이상, 35명 이하 제한)
        int criteria = dateInfo.customDayOfWeek().ordinal();
        Month month = dateInfo.month();
        for (int date = 1; date <= month.minLength(); date++) {
            CustomDayOfWeek today = CustomDayOfWeek.from((criteria + (date - 1)) % 7);
            processInWeekday(new DateInfo(month, today), date, assignment);
            processInHoliday(new DateInfo(month, today), date, assignment);
        }
        return new Workers(assignment);
    }

    private void processInWeekday(DateInfo dateInfo, int date, Deque<Worker> assignment) { // 평일 process
        if (!isHoliday(dateInfo, date)) {
            if (isInDangerContinuousWork(assignment, weekday)) {
                processInDangerWeekday(assignment);
                return;
            }
            processNotInDangerWeekday(assignment);
        }
    }

    private void processInHoliday(DateInfo dateInfo, int date, Deque<Worker> assignment) { // 주말/공휴일 process
        if (isHoliday(dateInfo, date)) {
            if (isInDangerContinuousWork(assignment, weekend)) {
                processInDangerWeekend(assignment);
                return;
            }
            processNotInDangerWeekend(assignment);
        }
    }

    private boolean isHoliday(DateInfo dateInfo, int date) {
        return dateInfo.customDayOfWeek().isWeekend() || Holiday.isHoliday(dateInfo.month(), date);
    }

    private boolean isInDangerContinuousWork(Deque<Worker> assignment, Workers resource) {
        return Objects.equals(assignment.peekLast(), resource.peekFirst());
    }

    private void processInDangerWeekday(Deque<Worker> assignment) {
        Worker danger = weekday.pollFirst(), safe = weekday.pollFirst();
        assignment.add(safe);
        weekday.offerLast(danger);
        weekday.offerLast(safe);
        weekdayMustAssigned = danger;
    }

    private void processNotInDangerWeekday(Deque<Worker> assignment) {
        if (weekdayMustAssigned != null) {
            assignment.add(weekdayMustAssigned);
            weekdayMustAssigned = null;
            return;
        }
        Worker worker = weekday.pollFirst();
        assignment.add(worker);
        weekday.offerLast(worker);
    }

    private void processInDangerWeekend(Deque<Worker> assignment) {
        Worker danger = weekend.pollFirst(), safe = weekend.pollFirst();
        assignment.add(safe);
        weekend.offerLast(danger);
        weekend.offerLast(safe);
        weekendMustAssigned = danger;
    }

    private void processNotInDangerWeekend(Deque<Worker> assignment) {
        if (weekendMustAssigned != null) {
            assignment.offerLast(weekendMustAssigned);
            weekendMustAssigned = null;
            return;
        }
        Worker worker = weekend.pollFirst();
        assignment.offerLast(worker);
        weekend.offerLast(worker);
    }
}
