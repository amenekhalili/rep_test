package ir.fararayaneh.erp.utils.encription;

import android.util.Base64;
import android.util.Log;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESHelper {

    private final static String ALGHORITM = "AES";
    private final static String TRANSFORMATION = "AES/CBC/PKCS7Padding";
    private final static String CHARSET_NAME = "UTF-8";

    public static String encriptionAES(String key, String msg) {
        String cipherTextString = null;
        byte[] raw = Base64.decode(key, Base64.DEFAULT);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, ALGHORITM);
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(new byte[16]));
            cipherTextString = Base64.encodeToString(cipher.doFinal(msg.getBytes(Charset.forName(CHARSET_NAME))), Base64.DEFAULT);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            Log.i("arash", "encriptionAES:"+e.getMessage());
        }
        return cipherTextString;
    }

    public static String decriptionAES(String key, String value) {
        String plainText = null;
        try {
            byte[] raw = Base64.decode(key, Base64.DEFAULT);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, ALGHORITM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(new byte[16]));
            byte[] original = cipher.doFinal(Base64.decode(value, Base64.DEFAULT));
            plainText = new String(original, Charset.forName(CHARSET_NAME));
        } catch (NoSuchAlgorithmException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
            Log.i("arash", "decriptionAES: "+e.getMessage());
        }


        return plainText;

    }
}
