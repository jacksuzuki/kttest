package jackprivate.kttest;

import static org.iq80.leveldb.impl.Iq80DBFactory.bytes;
import static org.iq80.leveldb.impl.Iq80DBFactory.factory;

import java.io.File;
import java.io.IOException;

import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;

public class LevelClient {

    public static void main(String[] args) throws InterruptedException, IOException {
        long startTime = System.currentTimeMillis();
        Options options = new Options();
        options.createIfMissing(true);
        DB db = factory.open(new File("example"), options);
        try {
            byte[] emptyByte = new byte[0];
            for (long i = 100000000; i < 150000000; i++) {
                db.put(bytes(String.valueOf(i)), emptyByte);
                if (i % 1000000 == 0) {
                    System.out.println(i + "done");
                }
            }
        } finally {
            // Make sure you close the db to shutdown the
            // database and avoid resource leaks.
            db.close();
            System.out.println("done"+(System.currentTimeMillis()-startTime));
        }
    }

}
