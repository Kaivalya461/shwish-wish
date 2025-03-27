package in.kvapps.shwish_wish.util;

import lombok.experimental.UtilityClass;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@UtilityClass
public class DecryptUtil {
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
            e.printStackTrace();
        }
        return null;
    }
}
