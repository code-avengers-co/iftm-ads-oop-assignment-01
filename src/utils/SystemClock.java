package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SystemClock {
    private static LocalDateTime virtualTime = LocalDateTime.now();

    public static LocalDateTime now() {
        return virtualTime;
    }

    public static LocalDate today() {
        return virtualTime.toLocalDate();
    }

    public static void advanceDays(int days) {
        if (days > 0) {
            virtualTime = virtualTime.plusDays(days);
            System.out.println("Time travel successful! New system date is: " + virtualTime.toLocalDate());
        }
    }
}
