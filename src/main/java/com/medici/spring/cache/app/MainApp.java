package com.medici.spring.cache.app;

import java.util.Calendar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author TOSS
 *
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan("com.medici.spring.cache")
public class MainApp {

    public static void main(String[] args) throws Exception {

	Calendar instance = Calendar.getInstance();
	int year = instance.get(Calendar.YEAR);
	int month = instance.get(Calendar.MONTH);
	instance.set(year, month, 15, 22, 0);

	Calendar instance_ = Calendar.getInstance();
	year = instance_.get(Calendar.YEAR);
	month = instance_.get(Calendar.MONTH);
	instance_.set(year, month, 16, 12, 47);
	System.out.println(instance.getTime());
	System.out.println(instance_.getTime());
	System.out.println(instance);
	System.out.println(instance_);

	SpringApplication.run(MainApp.class, args);
    }
}