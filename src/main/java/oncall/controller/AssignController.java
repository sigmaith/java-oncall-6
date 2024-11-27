package oncall.controller;

import java.time.Month;
import java.util.function.Supplier;
import oncall.controller.dto.DateInfo;
import oncall.controller.dto.MonthAndStartDayOfWeek;
import oncall.controller.dto.WorkersInfo;
import oncall.domain.AssignManager;
import oncall.domain.Workers;
import oncall.domain.constants.CustomDayOfWeek;
import oncall.view.InputView;
import oncall.view.OutputView;

public class AssignController {
    private final InputView inputView;
    private final OutputView outputView;

    public AssignController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        DateInfo dateInfo = retry(this::getDateInfo);
        WorkersInfo workersInfo = retry(this::getWorkersInfo);
        AssignManager assignManager = new AssignManager(workersInfo.weekday(), workersInfo.weekend());
        Workers assignment = assignManager.assign(dateInfo.month(), dateInfo.startDayOfWeek());
        outputView.printAssignmentOfWorkers(assignment, dateInfo);
    }

    private DateInfo getDateInfo() {
        MonthAndStartDayOfWeek monthAndStartDayOfWeek = inputView.askMonthAndStartDayOfWeek();
        Month month = Month.of(monthAndStartDayOfWeek.month());
        CustomDayOfWeek startDayOfWeek = CustomDayOfWeek.from(monthAndStartDayOfWeek.startDayOfWeek());
        return new DateInfo(month, startDayOfWeek);
    }

    private WorkersInfo getWorkersInfo() {
        Workers weekday = inputView.askWeekdayWorkers();
        Workers weekend = inputView.askWeekendWorkers();
        weekend.validateContains(weekday);
        return new WorkersInfo(weekday, weekend);
    }

    private static <T> T retry(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
