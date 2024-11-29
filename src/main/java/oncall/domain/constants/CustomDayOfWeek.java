package oncall.domain.constants;

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
}
