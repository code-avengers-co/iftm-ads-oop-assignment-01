package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SystemClock {
    private static int daysOffset = 0;

    public static LocalDateTime now() {
        // Retorna a hora atual real + os dias avançados.
        // O withNano(0) evita o bug de arredondamento do MySQL no DATETIME.
        return LocalDateTime.now().plusDays(daysOffset).withNano(0);
    }

    public static LocalDate today() {
        return LocalDate.now().plusDays(daysOffset);
    }

    public static void advanceDays(int days) {
        if (days > 0) {
            daysOffset += days;
            System.out.println("Time travel successful! New system date is: " + today());
        }
    }
}
