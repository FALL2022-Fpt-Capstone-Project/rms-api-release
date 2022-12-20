package vn.com.fpt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RmsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RmsApiApplication.class, args);
    }

}
