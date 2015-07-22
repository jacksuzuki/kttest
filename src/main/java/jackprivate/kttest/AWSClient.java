package jackprivate.kttest;

import java.nio.ByteBuffer;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClient;
import com.amazonaws.services.kms.model.EncryptRequest;
import com.amazonaws.services.kms.model.ListKeysResult;

public class AWSClient {

    public static void main(String[] args) throws InterruptedException {
        AWSCredentials awsCredentials = new BasicAWSCredentials("AKIAJWEFTCT6CHI2NO3A", "Fe11Kj+UKpbwi2oKi4aHpgfBlMv3mhuUgZK7DWKY");
        AWSKMS client = new AWSKMSClient(awsCredentials);
        client.setRegion(Region.getRegion(Regions.AP_NORTHEAST_1));
        ListKeysResult keys = client.listKeys();
        
        keys.getKeys().forEach(entry -> System.out.println(entry));
        
//        KeyListEntry testKey = keys.getKeys().get(0);
//        EncryptRequest encryptRequest = new EncryptRequest();
//        encryptRequest.setKeyId("d8ebde94-3884-4213-b4bd-3178f6bee2c3");
//        ByteBuffer 
//        encryptRequest.setPlaintext(plaintext);
        
        String keyId = "arn:aws:kms:ap-northeast-1:955013456663:key/d8ebde94-3884-4213-b4bd-3178f6bee2c3";
//                GenerateDataKeyRequest dataKeyRequest = new GenerateDataKeyRequest();
//                dataKeyRequest.setKeyId(keyId);
//                dataKeyRequest.setKeySpec("AES_128");
//                GenerateDataKeyResult dataKeyResult = client.generateDataKey(dataKeyRequest);
//                ByteBuffer plaintextKey = dataKeyResult.getPlaintext();
//                ByteBuffer encryptedKey = dataKeyResult.getCiphertextBlob();
                
                ByteBuffer plaintext = ByteBuffer.wrap(new byte[]{1,2,3,4,5,6,7,8,9,0});
                
                EncryptRequest req = new EncryptRequest().withKeyId(keyId).withPlaintext(plaintext);
                        ByteBuffer ciphertext = client.encrypt(req).getCiphertextBlob();
                        System.out.println( ciphertext);
                        

        System.out.println( "done");

    }
}
