package oncall.view;

import oncall.controller.dto.DateInfo;
import oncall.domain.Workers;
import oncall.domain.constants.CustomDayOfWeek;
import oncall.domain.constants.Holiday;

public class OutputView {
    public void printAssignment(Workers assignment, DateInfo dateInfo) {
        StringBuilder sb = new StringBuilder();
        for (int date = 1; date <= dateInfo.month().minLength(); date++) {
            sb.append(dateInfo.month().getValue()).append("월 ").append(date).append("일 ");
            sb.append(CustomDayOfWeek.from((dateInfo.customDayOfWeek().ordinal() + (date - 1)) % 7).getKoreanName());
            if (Holiday.isHoliday(dateInfo.month(), date)) {
                sb.append("(휴일)");
            }
            sb.append(" ").append(assignment.pollFirst().toString()).append("\n");
        }
        System.out.println(sb);
    }
}
