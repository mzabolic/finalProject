package pl.coderslab.finalproject;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;



@SpringBootApplication

public class FinalProjectApplication {


    public static void main(String[] args) {
        SpringApplication.run(FinalProjectApplication.class, args);
    }


    @Component
    @RequiredArgsConstructor
    @Slf4j
    public static class Tester implements CommandLineRunner {

        @Override
        public void run(String... args) throws Exception {
//            log.info(analyticsService.basicStats().toString());
        }
    }
}
