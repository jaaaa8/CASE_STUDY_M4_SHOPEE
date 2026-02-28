package com.example.case_study_mdl_4_shopee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.awt.*;
import java.net.URI;

@SpringBootApplication
public class CaseStudyMdl4ShopeeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CaseStudyMdl4ShopeeApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void openBrowser() {
        String url = "http://localhost:8080";
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
