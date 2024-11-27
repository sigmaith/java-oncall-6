package oncall.view;

import java.time.Month;
import oncall.controller.dto.DateInfo;
import oncall.domain.Workers;
import oncall.domain.constants.CustomDayOfWeek;
import oncall.domain.constants.Holiday;

public class OutputView {
    public void printAssignmentOfWorkers(final Workers assignment, final DateInfo dateInfo) {
        Month month = dateInfo.month();
        CustomDayOfWeek startDayOfWeek = dateInfo.startDayOfWeek();
        int criteria = startDayOfWeek.ordinal();

        StringBuilder sb = new StringBuilder();
        for (int date = 1; date <= month.minLength(); date++) {
            CustomDayOfWeek day = CustomDayOfWeek.from((criteria + (date - 1)) % 7);
            sb.append(month.getValue()).append("월 ").append(date).append("일 ").append(day.getKoreanName());
            if (Holiday.isHoliday(month, date)) {
                sb.append("(휴일)");
            }
            sb.append(" ").append(assignment.pollFirstWorker().getNickName()).append("\n");
        }
        System.out.println(sb.toString());
    }
}
