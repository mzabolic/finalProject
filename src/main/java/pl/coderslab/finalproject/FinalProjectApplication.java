package pl.coderslab.finalproject;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.finalproject.holidayevent.HolidayEventRepository;


@SpringBootApplication

public class FinalProjectApplication {


    public static void main(String[] args) {
        SpringApplication.run(FinalProjectApplication.class, args);
    }


}
