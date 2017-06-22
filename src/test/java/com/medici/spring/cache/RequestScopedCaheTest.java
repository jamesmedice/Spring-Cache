package com.medici.spring.cache;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.BiConsumer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.medici.spring.cache.config.CacheConfig;
import com.medici.spring.cache.config.RequestScopedCacheManager;
import com.medici.spring.cache.services.CachedService;
import com.medici.spring.cache.services.SecondCachedService;
import com.medici.spring.cache.services.ThirdCachedService;

@ContextConfiguration(classes = { CacheConfig.class, RequestScopedCacheManager.class, CachedService.class, SecondCachedService.class, ThirdCachedService.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class RequestScopedCaheTest {

    @Autowired
    CachedService cachedService;

    @Autowired
    SecondCachedService secondCachedService;

    @Autowired
    ThirdCachedService thirdCachedService;

    @Autowired
    RequestScopedCacheManager cacheManager;

    @Before
    public void setup() {
	cachedService.clearCache();
    }

    @Test
    public void shouldGetNormalValue() {
	cachedService.setString("originalValue");
	assertThat(cachedService.getCachedString(), is("originalValue"));
    }

    @Test
    public void shouldCacheInThirdThread() {
	Map<String, List<Date>> standard = new HashMap<>();
	standard.put("today", Arrays.asList(new Date()));

	thirdCachedService.setString(standard);

	thirdCachedService.getCachedString().forEach(new BiConsumer<String, List<Date>>() {

	    @Override
	    public void accept(String t, List<Date> u) {

		System.out.println(t);
		for (Date date : u) {
		    System.out.println(date);
		}

	    }
	});

    }

    @Test
    public void shouldCacheInThisThread() {
	cachedService.setString("originalValue");
	cachedService.getCachedString();
	cachedService.setString("newValue");
	assertThat(cachedService.getCachedString(), is("originalValue"));
    }

    @Test
    public void shouldNotCacheInASecondThread() throws Exception {
	cachedService.setString("originalValue");
	cachedService.getCachedString();
	cachedService.setString("newValue");
	final ExecutorService executorService = Executors.newFixedThreadPool(1);
	final Future<String> futureForSecondThread = executorService.submit(new Callable<String>() {
	    @Override
	    public String call() throws Exception {
		return cachedService.getCachedString();
	    }
	});
	assertThat(cachedService.getCachedString(), is("originalValue"));
	assertThat(futureForSecondThread.get(), is("newValue"));
	executorService.shutdownNow();
    }

    @Test
    public void shouldSupportMultipleCaches() {
	cachedService.setString("originalValue");
	secondCachedService.setString("originalValueForSecondCache");
	assertThat(cachedService.getCachedString(), is("originalValue"));
	assertThat(secondCachedService.getCachedString(), is("originalValueForSecondCache"));
    }

    public void shouldClearCachesWhenClearingCacheManager() {
	cachedService.setString("originalValue");
	cacheManager.clearCaches();
	cachedService.setString("newValue");
	assertThat(cachedService.getCachedString(), is("newValue"));

    }

}
