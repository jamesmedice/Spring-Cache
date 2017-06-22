package com.medici.spring.cache.services;

import java.util.Calendar;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheManager = "requestScopedCacheManager", cacheNames = "cachedService")
public class SlowService {

    @Cacheable
    public String getCurrentTime() {
	try {
	    Thread.sleep(2L);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	return Calendar.getInstance().getTime().toString();
    }

    public String getConstante() {
	try {
	    Thread.sleep(90L);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	return Calendar.getInstance().getTime().toString();
    }

}
