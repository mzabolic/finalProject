package pl.coderslab.finalproject.holidayevent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
@Slf4j
public class HolidayEventTester implements CommandLineRunner {
    private final HolidayEventRepository holidayEventRepository;
    @Override
    public void run(String... args) throws Exception {
        Long test = holidayEventRepository.count();
        log.info("Liczba rekordow: {}" , test);
    }

}
