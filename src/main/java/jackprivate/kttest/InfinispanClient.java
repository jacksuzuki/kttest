package jackprivate.kttest;

import net.sf.ehcache.Element;

import org.infinispan.Cache;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;

public class InfinispanClient {

    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.eviction().maxEntries(20000L).persistence().passivation(false).addSingleFileStore().preload(true).shared(false)
                .fetchPersistentState(true).ignoreModifications(false).purgeOnStartup(false)
                .location("/home/jack/git/kttest/infinispan");
        EmbeddedCacheManager manager = new DefaultCacheManager();
        manager.defineConfiguration("custom-cache", builder.build());
        Cache<Long, Boolean> cache = manager.getCache("custom-cache");

        for (long i = 10000000; i < 20000000; i++) {

            cache.put(Long.valueOf(i), true);
            if (i % 100000 == 0) {
                System.out.println(i + "done");
            }
        }

        System.out.println("done" + (System.currentTimeMillis() - startTime));

    }

}
