package oncall.controller;

import java.time.Month;
import java.util.function.Supplier;
import oncall.controller.dto.DateInfo;
import oncall.controller.dto.RawDateInfo;
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

    }

    private DateInfo getDateInfo() {
        RawDateInfo rawDateInfo = inputView.askMonthAndStartDayOfWeek();
        int rawMonth = rawDateInfo.month();
        String rawStartDayOfWeekName = rawDateInfo.StartDayOfWeek();
        return new DateInfo(Month.of(rawMonth), CustomDayOfWeek.from(rawStartDayOfWeekName));
    }

    private static <T> T retry(Supplier<T> supplier) { // 이거 왜 static이지??
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
