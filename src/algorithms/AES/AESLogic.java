package algorithms.AEC;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESLogic {

    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";

    public static String encrypt(String plaintext, String key) throws Exception {
        byte[]        keyBytes  = key.getBytes("UTF-8");
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
        Cipher        cipher    = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypted = cipher.doFinal(plaintext.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt(String ciphertext, String key) throws Exception {
        byte[]        keyBytes  = key.getBytes("UTF-8");
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
        Cipher        cipher    = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
        return new String(decrypted, "UTF-8");
    }

    public static String validateKey(String key) {
        if (key == null || key.isEmpty())
            return "Key cannot be empty!";
        int len = key.length();
        if (len != 16 && len != 24 && len != 32)
            return "Key must be 16, 24, or 32 characters  (got " + len + ")";
        return null;
    }

    public static String keyLabel(String key) {
        return "AES-" + (key.length() * 8);
    }
}