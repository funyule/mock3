package cn.zaink.mock3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zaink
 **/
@SpringBootApplication(scanBasePackages = "cn.zaink.mock3")
public class Mock3Application {

    public static void main(String[] args) {
        SpringApplication.run(Mock3Application.class, args);
    }
}
