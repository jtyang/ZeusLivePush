package com.ksyun.media.streamer.util;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/* compiled from: CredtpUtil */
public class b {
    public static final String a = "ISO-8859-1";

    public static byte[] a(byte[] bArr, byte[] bArr2) {
        SecureRandom secureRandom = new SecureRandom();
        try {
            Key generateSecret = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(bArr2));
            Cipher instance = Cipher.getInstance("DES");
            instance.init(2, generateSecret, secureRandom);
            return instance.doFinal(bArr);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            return null;
        } catch (InvalidKeySpecException e3) {
            e3.printStackTrace();
            return null;
        } catch (NoSuchPaddingException e4) {
            e4.printStackTrace();
            return null;
        } catch (BadPaddingException e5) {
            e5.printStackTrace();
            return null;
        } catch (IllegalBlockSizeException e6) {
            e6.printStackTrace();
            return null;
        }
    }
}
