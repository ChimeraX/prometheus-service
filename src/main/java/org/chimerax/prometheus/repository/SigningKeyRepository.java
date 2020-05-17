package org.chimerax.prometheus.repository;

import org.chimerax.common.repository.ChimeraXRepository;
import org.chimerax.prometheus.entity.SigningKey;

import java.util.Optional;
import java.util.Random;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 26-Apr-20
 * Time: 2:18 AM
 */
public interface SigningKeyRepository extends ChimeraXRepository<Integer, SigningKey> {

    default SigningKey findRandomKey() {
        final long count = count();
        final long random = new Random().nextLong();
        final long position = Math.abs(random) % count;
        return findFirstByIdGreaterThanAndIdLessThan(position, count);
    }

    SigningKey findFirstByIdGreaterThanAndIdLessThan(final long greaterThan, final long lesserThan);

    Optional<SigningKey> findByKeyId(final String keyId);
}
