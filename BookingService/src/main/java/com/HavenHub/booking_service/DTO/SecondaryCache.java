package com.HavenHub.booking_service.DTO;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SecondaryCache<K, V> {

      private final Map<K, CacheEntry<V>> cache = new ConcurrentHashMap<>();
      private final long expiryTime = 5 * 60 * 1000; // Expiry time in milliseconds

      public void put(K key, V value) {
            cache.put(key, new CacheEntry<>(value, System.currentTimeMillis()));
      }

      public V get(K key) {
            CacheEntry<V> entry = cache.get(key);
            if (entry != null && System.currentTimeMillis() - entry.getTimestamp() <= expiryTime) {
                  return entry.getValue();
            }
            // Cache expired, remove it
            cache.remove(key);
            return null;
      }

      public void invalidate(K key) {
            cache.remove(key);
      }

      public void clear() {
            cache.clear();
      }

      // Nested class to handle cache entries
      //buy
      private static class CacheEntry<V> {
            private final V value;
            private final long timestamp;

            public CacheEntry(V value, long timestamp) {
                  this.value = value;
                  this.timestamp = timestamp;
            }

            public V getValue() {
                  return value;
            }

            public long getTimestamp() {
                  return timestamp;
            }
      }
}
