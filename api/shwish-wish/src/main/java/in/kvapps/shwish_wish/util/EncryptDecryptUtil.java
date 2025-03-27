package in.kvapps.shwish_wish.util;

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@UtilityClass
@Log4j2
public class EncryptDecryptUtil {
    public String decrypt(String value, String key) {
        try {
            // Initialize the cipher for decryption
            Cipher cipher = Cipher.getInstance("AES");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

            // Decode the Base64-encoded encrypted message
            byte[] decodedBytes = Base64.getDecoder().decode(value);

            // Perform the decryption
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);

            return new String(decryptedBytes);
        } catch (Exception e) {
            log.error("Decryption Error -> errorMessage: {}", e.getMessage(), e);
        }
        return null;
    }

    public String encrypt(String value, String key) {
        try {
            // Encrypt the message
            Cipher cipher = Cipher.getInstance("AES");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(value.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            log.error("Encryption Error -> errorMessage: {}", e.getMessage(), e);
        }
        return null;
    }
}
