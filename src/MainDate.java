import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class MainDate {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Date date = new Date();
        System.out.println(date);
        System.out.println(System.currentTimeMillis() - start);
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.getTime());

        LocalDate ld = LocalDate.now();
        LocalTime lt = LocalTime.now();
        LocalDateTime ldt = LocalDateTime.of(ld, lt);
        System.out.println(ldt);


    }
}
