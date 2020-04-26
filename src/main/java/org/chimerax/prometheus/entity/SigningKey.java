package org.chimerax.prometheus.entity;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.crypto.spec.SecretKeySpec;
import javax.persistence.*;
import java.security.Key;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 26-Apr-20
 * Time: 2:13 AM
 */

@Data
@Entity
@Table(name = "signing_keys", schema = "keys")
@Accessors(chain = true)
public class SigningKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String keyId;

    private byte[] keyData;

    @Transient
    public Key getKey() {
        return new SecretKeySpec(keyData, SignatureAlgorithm.HS256.getJcaName());
    }
}
