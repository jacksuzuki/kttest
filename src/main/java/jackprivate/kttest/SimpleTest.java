package jackprivate.kttest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

public class SimpleTest {

    public static void main(String[] args) throws IOException  {
        String dataname = args[0];
        int count = Integer.valueOf(args[1]);
        int offset = Integer.valueOf(args[2]);
        XMemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses("localhost:2010"));
        XMemcachedClient client=(XMemcachedClient) builder.build();
        
        try{
            StringBuilder sb = new StringBuilder();
            for(int i=0;i<20;i++){
                sb.append("1234567890");
            }
            String message = sb.toString();
            long startTime = System.currentTimeMillis();
            for(long i=0;i<100*1000*1000;i++){
                client.setWithNoReply(dataname+i, 0, message+i);
                while(client.getConnector().getNoReplyOpsFlowControl().permits()<10000){
                    System.out.println("done="+ i +". available connection= "+ client.getConnector().getNoReplyOpsFlowControl().permits());
                    Thread.sleep(1000);
                }
            }
            
            long endTime = System.currentTimeMillis();
     
            
            System.out.println("It tooks "+ ((endTime-startTime))+"ms.");
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            client.shutdown();
        }
        
        
       
    }

}
