package org.arturs.firstSpring.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class RateLimitConfig {

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    /**
     * Get or create a bucket for the given IP address
     * Rate limit: 20 requests per minute per IP
     */
    public Bucket resolveBucket(String key) {
        return cache.computeIfAbsent(key, k -> createNewBucket());
    }

    private Bucket createNewBucket() {
        // Allow 20 requests per minute
        Bandwidth limit = Bandwidth.builder()
                .capacity(10)
                .refillIntervally(10, Duration.ofMinutes(1))
                .build();
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

    /**
     * Remove expired buckets to prevent memory leak
     */
    public void cleanupCache() {
        cache.entrySet().removeIf(entry -> entry.getValue().getAvailableTokens() == 0);
    }
}
