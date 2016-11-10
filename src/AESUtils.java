import java.io.*;
import java.security.*;

import javax.crypto.*;
import javax.crypto.spec.*;

public class AESUtils {

    private static final String ALGORITHM = "AES";
    //private static final int KEY_SIZE = 128;// No use because we use MD5 to generate 128-bit length encodedKey
    private static final int CACHE_SIZE = 1024;
    
    /**
     * Generate Encoded-SecretKey for SecretKeySpec.
     * Password will be the seed.
     * 
     * @param seed 
     * @return encodedKey
     * @throws Exception
     */
    public static byte[] getEncodedSecretKey(String password) throws Exception {
        byte[] passwordToBytes = password.getBytes();
    	MessageDigest md = MessageDigest.getInstance("MD5");
    	byte[] encodedKey = md.digest(passwordToBytes);
    	return encodedKey;
    }
    
    /**
     * Use encodedKey to encrypt source file
     * 
     * @param encodedKey
     * @param sourceFilePath
     * @param destFilePath
     * @throws Exception
     */
    public static void encryptFile(byte[] encodedKey, String sourceFilePath, String destFilePath) throws Exception {
        File sourceFile = new File(sourceFilePath);
        File destFile = new File(destFilePath); 
        if (sourceFile.exists() && sourceFile.isFile()) {
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            destFile.createNewFile();
            InputStream in = new FileInputStream(sourceFile);
            OutputStream out = new FileOutputStream(destFile);
            
            SecretKeySpec secretKeySpec = new SecretKeySpec(encodedKey, ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM); 
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            CipherInputStream cin = new CipherInputStream(in, cipher);
            byte[] cache = new byte[CACHE_SIZE];
            int nRead = 0;
            while ((nRead = cin.read(cache)) != -1) {
                out.write(cache, 0, nRead);
                out.flush();
            }
            out.close();
            cin.close();
            in.close();
        }
    }
    
    /**
     * Use encodedKey to decrypt the encrypted file
     * 
     * @param key
     * @param sourceFilePath
     * @param destFilePath
     * @throws Exception
     */
    public static void decryptFile(byte[] encodedKey, String sourceFilePath, String destFilePath) throws Exception {
        File sourceFile = new File(sourceFilePath);
        File destFile = new File(destFilePath); 
        if (sourceFile.exists() && sourceFile.isFile()) {
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            destFile.createNewFile();
            FileInputStream in = new FileInputStream(sourceFile);
            FileOutputStream out = new FileOutputStream(destFile);

            SecretKeySpec secretKeySpec = new SecretKeySpec(encodedKey, ALGORITHM); 
            Cipher cipher = Cipher.getInstance(ALGORITHM); 
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            CipherOutputStream cout = new CipherOutputStream(out, cipher);
            byte[] cache = new byte[CACHE_SIZE];
            int nRead = 0;
            while ((nRead = in.read(cache)) != -1) {
                cout.write(cache, 0, nRead);
                cout.flush();
            }
            cout.close();
            out.close();
            in.close();
        }
    }
}