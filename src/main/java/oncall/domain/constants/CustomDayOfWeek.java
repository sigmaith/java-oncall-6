package oncall.domain.constants;

import java.util.Arrays;
import oncall.exception.CustomException;
import oncall.exception.ErrorMessage;

public enum CustomDayOfWeek {
    MONDAY("월", false),
    TUESDAY("화", false),
    WEDNESDAY("수", false),
    THURSDAY("목", false),
    FRIDAY("금", false),
    SATURDAY("토", true),
    SUNDAY("일", true);
    private final String koreanName;
    private final boolean isWeekend;

    CustomDayOfWeek(final String koreanName, final boolean isWeekend) {
        this.koreanName = koreanName;
        this.isWeekend = isWeekend;
    }

    public static CustomDayOfWeek from(String name) {
        return Arrays.stream(values())
                .filter(c -> c.koreanName.equals(name))
                .findFirst().orElseThrow(() -> CustomException.from(ErrorMessage.INVALID_INPUT));

    }

    public static CustomDayOfWeek from(int ith) {
        return Arrays.stream(values())
                .filter(c -> c.ordinal() == ith)
                .findFirst().orElseThrow(() -> CustomException.from(ErrorMessage.INVALID_INPUT));
    }

    public boolean isWeekend() {
        return isWeekend;
    }
}
