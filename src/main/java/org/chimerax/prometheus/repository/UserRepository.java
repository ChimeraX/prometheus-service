package org.chimerax.prometheus.repository;

import org.chimerax.common.repository.ChimeraXRepository;
import org.chimerax.prometheus.entity.User;

import java.util.Optional;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 24-Apr-20
 * Time: 9:03 PM
 */
public interface UserRepository extends ChimeraXRepository<Long, User> {


    Optional<User> findByEmail(final String email);

    boolean existsByEmail(final String email);
}
