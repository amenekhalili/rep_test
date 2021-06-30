package ir.fararayaneh.erp.utils.encription;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import ir.fararayaneh.erp.commons.CommonsFormat;
import ir.fararayaneh.erp.utils.logger.ThrowableLoggerHelper;

public class HashHelper {

    private static final String HASH_FORMAT ="%032x%n";
    public static String hashProcess(String plain, String algorithm) {
        MessageDigest messageDigest=null;
        try {
            messageDigest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            ThrowableLoggerHelper.logMyThrowable("HashHelper***hashProcess/"+e.getMessage());
        }
        // Calculate Message Digest as bytes:
        assert messageDigest != null;
        byte[] digest = messageDigest.digest(plain.getBytes(StandardCharsets.UTF_8));
        // Convert to 32-char long String:
        String myHash=String.format(HASH_FORMAT, new BigInteger(1, digest));
        ThrowableLoggerHelper.logMyThrowable("my hash is :"+String.format(CommonsFormat.FORMAT_1,getSaltString(myHash,2),myHash));
        return String.format(CommonsFormat.FORMAT_1,getSaltString(myHash,2),myHash.replace("\n", ""));
    }

    private static String getSaltString(String saltChars, int length) {
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() <= length) {
            int index = (int) (rnd.nextFloat() * saltChars.length());
            salt.append(saltChars.charAt(index));
        }
        return salt.toString();
    }
}
