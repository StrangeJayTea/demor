package com.strange.jay.camera;

import com.strange.jay.camera.services.CameraServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class CameraServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CameraServiceApplication.class, args);
    }

}
