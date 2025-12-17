package com.reparaya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// 👇 ESTA LÍNEA ES LA MAGIA: Le dice que busque en TODAS las carpetas de reparaya
// @ComponentScan("com.reparaya")
public class ReparaYaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReparaYaApplication.class, args);
    }

}
