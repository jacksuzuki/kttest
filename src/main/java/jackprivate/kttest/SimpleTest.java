package jackprivate.kttest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

public class SimpleTest {

    public static void main(String[] args) throws IOException, InterruptedException, MemcachedException, TimeoutException {
        XMemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses("localhost:2010"));
        XMemcachedClient client=(XMemcachedClient) builder.build();
        long startTime = System.currentTimeMillis();
        for(int i=0;i<1000000;i++){
            client.setWithNoReply("test"+i, 60, "test"+i);
            while(client.getConnector().getNoReplyOpsFlowControl().permits()<10000){
                System.out.println("available connection = "+client.getConnector().getNoReplyOpsFlowControl().permits());
                Thread.sleep(1000);
            }
        }
        
        long endTime = System.currentTimeMillis();
 
        
        System.out.println("It tooks "+ ((endTime-startTime)/1000)+"ms.");
        
        client.shutdown();
    }

}
