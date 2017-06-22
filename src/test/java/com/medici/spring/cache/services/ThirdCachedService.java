package com.medici.spring.cache.services;

import java.util.Map;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheManager = "requestScopedCacheManager", cacheNames = "thirdCachedService")
public class ThirdCachedService<K, T> {

    private Map<K, T> content;

    @Cacheable
    public Map<K, T> getCachedString() {
	return content;
    }

    public Map<K, T> getNonCachedString() {
	return content;
    }

    public void setString(Map<K, T> object) {
	this.content = object;
    }

    @CacheEvict
    public void clearCache() {
    }

}
