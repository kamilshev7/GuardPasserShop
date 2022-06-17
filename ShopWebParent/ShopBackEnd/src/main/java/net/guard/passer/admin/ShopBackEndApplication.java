package net.guard.passer.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"net.guard.passer.common.entity", "net.guard.passer.admin.user"})
public class ShopBackEndApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopBackEndApplication.class, args);
    }
}
