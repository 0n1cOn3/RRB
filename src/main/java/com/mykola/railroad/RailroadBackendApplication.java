package com.mykola.railroad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication
public class RailroadBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(RailroadBackendApplication.class, args);
    }

}
