package jackprivate.kttest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

public class SpeedTest {

    public static void main(String[] args) throws IOException {
        String target = args[0];
        String dataname = args[1];
        int count = Integer.valueOf(args[2]);
        XMemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(target));
        XMemcachedClient client=(XMemcachedClient) builder.build();
        
        try {
            StringBuilder sb = new StringBuilder();
            for(int i=0;i<20;i++){
                sb.append("1234567890");
            }
            String message = sb.toString();
            long startTime = System.currentTimeMillis();
            for(long i=0;i<count;i++){
                client.set(dataname+"_"+i, 0, message+"_"+i);
            }
            
            long endTime = System.currentTimeMillis();
 
            
            System.out.println("It tooks "+ ((endTime-startTime))+"ms.");
            
            
        } catch (Exception e) {
            e.printStackTrace();
            
        } finally{
            client.shutdown();
        }
    }

}
