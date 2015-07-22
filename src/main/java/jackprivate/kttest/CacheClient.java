package jackprivate.kttest;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.DiskStoreConfiguration;
import net.sf.ehcache.config.MemoryUnit;
import net.sf.ehcache.config.PersistenceConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration.Strategy;

public class CacheClient {

    public static void main(String[] args) throws InterruptedException {
        // Configuration configuration = new Configuration();
        // DiskStoreConfiguration diskStoreConfiguration = new
        // DiskStoreConfiguration();
        // diskStoreConfiguration.setPath("/home/jack/git/kttest/cache");
        //
        // CacheManager singletonManager = new CacheManager(configuration);
        // Cache memoryOnlyCache = new Cache("testCache", 5000, true, true, 5,
        // 2);
        //
        // singletonManager.addCache(memoryOnlyCache);
        // Cache test = singletonManager.getCache("testCache");

        Configuration cacheManagerConfig = new Configuration().diskStore(new DiskStoreConfiguration()
                .path("/home/jack/git/kttest/cache"));
        CacheConfiguration cacheConfig = new CacheConfiguration().name("my-cache")
                .maxBytesLocalHeap(16, MemoryUnit.MEGABYTES)
                .persistence(new PersistenceConfiguration().strategy(Strategy.DISTRIBUTED));

        cacheManagerConfig.addCache(cacheConfig);

        CacheManager cacheManager = new CacheManager(cacheManagerConfig);
        Ehcache test = cacheManager.getEhcache("my-cache");

        for (long i = 10000000; i < 20000000; i++) {
//            System.out.println(test.get(Long.valueOf(i)));
            test.put(new Element(Long.valueOf(i), true));
            if (i % 100000 == 0) {
                System.out.println(i + "done");
            }
        }
        
        cacheManager.shutdown();
        System.out.println( "done");

    }

}
