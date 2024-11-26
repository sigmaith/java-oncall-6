package oncall.controller.dto;

import java.time.Month;
import oncall.domain.constants.CustomDayOfWeek;

public record DateInfo(
        Month month,
        CustomDayOfWeek startDayOfWeek
) {
}
