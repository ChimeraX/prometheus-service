package org.chimerax.prometheus.repository;

import org.chimerax.common.repository.ChimeraXRepository;
import org.chimerax.prometheus.entity.GrantedAccess;

import java.util.Optional;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 24-Apr-20
 * Time: 9:56 PM
 */
public interface GrantedAccessRepository extends ChimeraXRepository<Long, GrantedAccess> {

    Optional<GrantedAccess> findByCode(final String code);
}
