package org.chimerax.prometheus.service;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import java.io.File;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 14-Jun-20
 * Time: 7:26 AM
 */
@Service
public class RSAService {

    private String privateKey;
    private String publicKey;

    public String getPublicKey() {
        return publicKey;
    }

    public String decrypt(String data) {
        byte[] bytes = Base64.getDecoder().decode(data.getBytes());
        return new String(_decrypt(bytes, generatePrivateKey(privateKey)));
    }

    @SneakyThrows
    private static byte[] _decrypt(final byte[] data, final PrivateKey privateKey) {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    @PostConstruct
    private void postConstruct() throws Exception {
        publicKey = loadPublicKeyContent();
        privateKey = loadPrivateKeyContent().trim();
    }

    @SneakyThrows
    private static PrivateKey generatePrivateKey(String content) {
        byte[] bytes = Base64.getDecoder().decode(content.getBytes());
        PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(bytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(ks);
    }

    @SneakyThrows
    private static String loadPrivateKeyContent() {
        File file = new File("src/main/resources/static/key.txt");
        byte[] bytes = Files.readAllBytes(file.toPath());
        return new String(bytes);
    }

    @SneakyThrows
    private static String loadPublicKeyContent() {
        File file = new File("src/main/resources/static/pub");
        byte[] bytes = Files.readAllBytes(file.toPath());
        return new String(bytes);
    }

}
