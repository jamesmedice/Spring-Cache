package com.medici.spring.cache.web;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medici.spring.cache.services.SlowService;

@RestController
public class RestService {

    @Autowired
    SlowService slowService;

    @RequestMapping("/timer")
    String timer() {
	return IntStream.range(0, 20).mapToObj(i -> i + " -" + slowService.getCurrentTime()).collect(Collectors.joining());
    }

    @RequestMapping("/clock")
    String home() {
	return IntStream.range(0, 20).mapToObj(i -> i + " -" + slowService.getConstante()).collect(Collectors.joining());
    }
}
