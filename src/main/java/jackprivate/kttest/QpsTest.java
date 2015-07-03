package jackprivate.kttest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

public class QpsTest {

    public static void main(String[] args) throws IOException {
        String dataname = args[0];
        int qps = Integer.valueOf(args[1]);
        int second = Integer.valueOf(args[2]);
        XMemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses("localhost:2010"));
        XMemcachedClient client=(XMemcachedClient) builder.build();
        
        try {
            StringBuilder sb = new StringBuilder();
            for(int i=0;i<20;i++){
                sb.append("1234567890");
            }
            String message = sb.toString();
            long startTime = System.currentTimeMillis();
            for(long i=0;i<second;i++){
                for(int j=0;j<qps;j++){
                    client.setWithNoReply(dataname+"_"+i+"_"+j, 0, message+"_"+i+"_"+j);
                }
                System.out.println("done="+ i +". available connection= "+ client.getConnector().getNoReplyOpsFlowControl().permits());
                Thread.sleep(1000);
            }
            
            long endTime = System.currentTimeMillis();
 
            
            System.out.println("It tooks "+ ((endTime-startTime))+"ms.");
            
            
        } catch (Exception e) {
            e.printStackTrace();
            client.shutdown();
        }
    }

}
