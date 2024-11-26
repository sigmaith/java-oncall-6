package oncall.domain.constants;

public enum CustomDayOfWeek {
    MONDAY(false, "월"),
    TUESDAY(false, "화"),
    WEDNESDAY(false, "수"),
    THURSDAY(false, "목"),
    FRIDAY(false, "금"),
    SATURDAY(true, "토"),
    SUNDAY(true, "일");
    private final boolean isWeekend;
    private final String name;

    CustomDayOfWeek(final boolean isWeekend, final String name) {
        this.isWeekend = isWeekend;
        this.name = name;
    }
}
