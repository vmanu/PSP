/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteweb;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author oscar
 */
public class LoginClientWeb {

    public static String generateHMacSHA256(final String key, final String data)
            throws InvalidKeyException, NoSuchAlgorithmException {

        final Mac hMacSHA256 = Mac.getInstance("HmacSHA256");
        byte[] hmacKeyBytes = key.getBytes(StandardCharsets.UTF_8);
        final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA256");
        hMacSHA256.init(secretKey);
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        byte[] res = hMacSHA256.doFinal(dataBytes);

        return DatatypeConverter.printBase64Binary(res);
    }

}
