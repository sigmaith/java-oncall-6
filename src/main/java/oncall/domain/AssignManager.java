package oncall.domain;

import java.time.Month;
import java.util.ArrayList;
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
        Workers assignment = Workers.from(new ArrayList<>());
        int criteria = dateInfo.customDayOfWeek().ordinal();
        Month month = dateInfo.month();
        for (int date = 1; date <= month.minLength(); date++) {
            CustomDayOfWeek today = CustomDayOfWeek.from((criteria + (date - 1)) % 7);
            processInWeekday(new DateInfo(month, today), date, assignment);
            processInHoliday(new DateInfo(month, today), date, assignment);
        }
        return assignment;
    }

    private void processInWeekday(DateInfo dateInfo, int date, Workers assignment) { // 평일 process
        if (!isHoliday(dateInfo, date)) {
            if (isInDangerContinuousWork(assignment, weekday)) {
                processInDangerWeekday(assignment);
                return;
            }
            processNotInDangerWeekday(assignment);
        }
    }

    private void processInHoliday(DateInfo dateInfo, int date, Workers assignment) { // 주말/공휴일 process
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

    private boolean isInDangerContinuousWork(Workers assignment, Workers resource) {
        return Objects.equals(assignment.peekLast(), resource.peekFirst());
    }

    private void processInDangerWeekday(Workers assignment) {
        Worker danger = weekday.pollFirst(), safe = weekday.pollFirst();
        assignment.offerLast(safe);
        weekday.offerLast(danger);
        weekday.offerLast(safe);
        weekdayMustAssigned = danger;
    }

    private void processNotInDangerWeekday(Workers assignment) {
        if (weekdayMustAssigned != null) {
            assignment.offerLast(weekdayMustAssigned);
            weekdayMustAssigned = null;
            return;
        }
        Worker worker = weekday.pollFirst();
        assignment.offerLast(worker);
        weekday.offerLast(worker);
    }

    private void processInDangerWeekend(Workers assignment) {
        Worker danger = weekend.pollFirst(), safe = weekend.pollFirst();
        assignment.offerLast(safe);
        weekend.offerLast(danger);
        weekend.offerLast(safe);
        weekendMustAssigned = danger;
    }

    private void processNotInDangerWeekend(Workers assignment) {
        if (weekendMustAssigned != null) {
            assignment.offerLast(weekendMustAssigned);
            weekendMustAssigned = null;
            return;
        }
        Worker worker = weekend.peekFirst();
        assignment.offerLast(worker);
        weekend.offerLast(worker);
    }
}
