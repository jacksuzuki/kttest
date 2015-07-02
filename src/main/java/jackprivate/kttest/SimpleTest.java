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
        
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<20;i++){
            sb.append("1234567890");
        }
        String message = sb.toString();
        long startTime = System.currentTimeMillis();
        for(long i=0;i<100*1000*1000;i++){
            client.setWithNoReply("basedata"+i, 0, message+i);
            while(client.getConnector().getNoReplyOpsFlowControl().permits()<10000){
                System.out.println("done="+ i +". available connection= "+ client.getConnector().getNoReplyOpsFlowControl().permits());
                Thread.sleep(1000);
            }
        }
        
        long endTime = System.currentTimeMillis();
 
        
        System.out.println("It tooks "+ ((endTime-startTime))+"ms.");
        
        client.shutdown();
    }

}
