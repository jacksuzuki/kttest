package jackprivate.kttest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

public class SimpleTest {

    public static void main(String[] args) throws IOException  {
        String target = args[0];
        String dataname = args[1];
        long count = Long.valueOf(args[2]);
        long offset = Long.valueOf(args[3]);
        
        System.out.println("target="+target+"dataname="+dataname+"count="+count+"offset="+offset);
        XMemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(target));
        XMemcachedClient client=(XMemcachedClient) builder.build();
        
        try{
            StringBuilder sb = new StringBuilder();
            for(int i=0;i<20;i++){
                sb.append("1234567890");
            }
            String message = sb.toString();
            long startTime = System.currentTimeMillis();
            for(long i=offset;i<count+offset;i++){
                client.set(dataname+i, 0, message+i);
                if(i%100000==0){
                    System.out.println("done="+ i);
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
