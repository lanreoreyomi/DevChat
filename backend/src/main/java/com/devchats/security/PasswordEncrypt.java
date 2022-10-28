package com.devchats.security;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


public class PasswordEncrypt {
  private final Logger logger = LoggerFactory.getLogger(PasswordEncrypt.class);

  public String encryptValue(String data, String key) {
    byte[] encryptedPass = null;

    try {
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");
      cipher.init(Cipher.ENCRYPT_MODE, secretKey);
      encryptedPass = cipher.doFinal(data.getBytes("UTF-8"));
    } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
        | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException e) {
      logger.error(e.getMessage());
    }

    return Base64.getEncoder().encodeToString(encryptedPass);
  }

  public String decryptValue(String data, String key) {
    byte[] decryptedPass = null;

    try {
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");
      cipher.init(Cipher.DECRYPT_MODE, secretKey);
      decryptedPass = cipher.doFinal(Base64.getDecoder().decode(data));
    } catch (NoSuchAlgorithmException | NoSuchPaddingException
        | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
      logger.error(e.getMessage());
    }

    return new String(decryptedPass);
  }
}
