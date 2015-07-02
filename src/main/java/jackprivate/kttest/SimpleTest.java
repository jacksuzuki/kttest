package jackprivate.kttest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

public class SimpleTest {

    public static void main(String[] args) throws IOException, InterruptedException, MemcachedException, TimeoutException {
        XMemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses("localhost:11211"));
        XMemcachedClient client=(XMemcachedClient) builder.build();
        
        long startTime = System.currentTimeMillis();
        for(int i=0;i<10000;i++){
            client.setWithNoReply("test"+i, 60, "test"+i);
        }
        
        long endTime = System.currentTimeMillis();
        
        
        for(int i=0;i<10000;i++){
            System.out.println((String)client.get("test"+i));
        }
        
        System.out.println("consume time="+ ((endTime-startTime)/1000));
    }

}
