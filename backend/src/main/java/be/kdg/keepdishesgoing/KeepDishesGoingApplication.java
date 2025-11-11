package be.kdg.keepdishesgoing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.modulith.Modulith;

@Modulith
@SpringBootApplication
@EnableScheduling
public class KeepDishesGoingApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeepDishesGoingApplication.class, args);
    }

}
