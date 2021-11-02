package ServiceImpl;

import Service.IEncryptDecryptService;
import javax.crypto.Cipher;


import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Arrays;


public class EncryptionDecryptionServiceImpl implements IEncryptDecryptService {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";
    private static final String key = "ABCHBJBJCBJBCJDBJCDCDJJDDDDDJJJJ";

    public byte[] encryptFileData(byte[] content) throws Exception { //Standard encryption algorithm can be used

        return doCrypto(Cipher.ENCRYPT_MODE, key, content);
    }

    public byte[] decryptFileData(byte[] content) throws Exception { //Standard decryption algorithm can be used
        return doCrypto(Cipher.DECRYPT_MODE, key, content);
    }

    private static byte[] doCrypto(int cipherMode, String key, byte[] inputBytes) throws Exception {
        try {

            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);

            byte[] outputBytes = cipher.doFinal(inputBytes);
            //System.out.println(Arrays.toString(outputBytes));
            return outputBytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputBytes;
    }

}

