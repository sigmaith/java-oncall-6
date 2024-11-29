package oncall.domain.constants;

import java.util.Arrays;
import oncall.exception.CustomException;
import oncall.exception.ErrorMessage;

public enum CustomDayOfWeek {
    MONDAY("월"),
    TUESDAY("화"),
    WEDNESDAY("수"),
    THURSDAY("목"),
    FRIDAY("금"),
    SATURDAY("토"),
    SUNDAY("일");
    private final String koreanName;

    CustomDayOfWeek(final String koreanName) {
        this.koreanName = koreanName;
    }

    public static CustomDayOfWeek from(String name) {
        return Arrays.stream(values())
                .filter(c -> c.koreanName.equals(name))
                .findFirst().orElseThrow(() -> CustomException.from(ErrorMessage.INVALID_INPUT));

    }
}
