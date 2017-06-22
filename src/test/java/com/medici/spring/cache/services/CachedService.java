package com.medici.spring.cache.services;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheManager = "requestScopedCacheManager", cacheNames = "cachedService")
public class CachedService {

	private String string;

	@Cacheable
	public String getCachedString() {
		return string;
	}

	public String getNonCachedString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	@CacheEvict
	public void clearCache() {
	}

}
