package com.kltn.medical_consultation;

import com.kltn.medical_consultation.utils.ICheckBCryptPasswordEncoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class MedicalConsultationApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicalConsultationApplication.class, args);
    }

    @Bean
    public ICheckBCryptPasswordEncoder passwordEncoder(){
        return new ICheckBCryptPasswordEncoder();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void serverReady(){}
}
