package org.chimerax.prometheus.service;

import lombok.AllArgsConstructor;
import org.chimerax.common.exception.NoSuchKeyException;
import org.chimerax.common.security.jwt.JWTServiceHelper;
import org.chimerax.common.security.jwt.JWTServiceHelperFactory;
import org.chimerax.prometheus.entity.SigningKey;
import org.chimerax.prometheus.repository.SigningKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 26-Apr-20
 * Time: 2:51 AM
 */
@Component
@AllArgsConstructor
public class JWTServiceHelperFactoryImpl extends JWTServiceHelperFactory {

    private SigningKeyRepository signingKeyRepository;

    private final Map<String, SigningKey> cachedKeys = new HashMap<>();

    @Override
    public JWTServiceHelper get(final String signingKeyId) throws NoSuchKeyException {
        final SigningKey cachedKey = cachedKeys.get(signingKeyId);
        if (cachedKey != null) {
            return build(cachedKey);
        } else {
            final Optional<SigningKey> signingKeyOptional = signingKeyRepository.findByKeyId(signingKeyId);
            final SigningKey signingKey = signingKeyOptional.orElseThrow(() -> new NoSuchKeyException(signingKeyId));
            cachedKeys.put(signingKey.getKeyId(), signingKey);
            return build(signingKey);
        }
    }

    @Override
    public JWTServiceHelper getRandomHelper() {
        return build(signingKeyRepository.findRandomKey());
    }

    private JWTServiceHelper build(final SigningKey signingKey) {
        return JWTServiceHelper.builder()
                .signingKeyId(signingKey.getKeyId())
                .signingKey(signingKey.getKey())
                .issuer(getIssuer())
                .validity(getValidity())
                .build();
    }
}
