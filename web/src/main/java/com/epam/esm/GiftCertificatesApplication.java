package com.epam.esm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.Locale;

@SpringBootApplication
public class GiftCertificatesApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        SpringApplication.run(GiftCertificatesApplication.class, args);
    }
}