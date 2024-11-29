package oncall.domain.constants;

import java.time.Month;

public enum Holiday {
    NEW_YEARS_DAY(Month.JANUARY, 1),
    INDEPENDENCE_MOVEMENT_DAY(Month.MARCH, 1),
    CHILDREN_DAY(Month.MAY, 5),
    MEMORIAL_DAY(Month.JUNE, 6),
    NATIONAL_LIBERATION_DAY(Month.AUGUST, 15),
    NATIONAL_FOUNDATION_DAY_OF_KOREA(Month.OCTOBER, 3),
    HANGUL_PROCLAMATION_DAY(Month.OCTOBER, 9),
    CHRISTMAS_DAY(Month.DECEMBER, 25);
    private final Month month;
    private final int date;

    Holiday(final Month month, final int date) {
        this.month = month;
        this.date = date;
    }
}