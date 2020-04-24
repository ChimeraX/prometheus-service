package org.chimerax.prometheus.repository;

import org.chimerax.commonservice.api.repository.ChimeraXRepository;
import org.chimerax.prometheus.entity.User;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 24-Apr-20
 * Time: 9:03 PM
 */
public interface UserRepository extends ChimeraXRepository<Long, User> {

    @Query("select user from User user where user.email=:username or user.username=:username")
    Optional<User> findByUsernameOrEmail(final String username);

    Optional<User> findByUsername(final String username);
}
