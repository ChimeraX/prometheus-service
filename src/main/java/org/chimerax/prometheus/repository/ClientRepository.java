package org.chimerax.prometheus.repository;

import org.chimerax.commonservice.api.repository.ChimeraXRepository;
import org.chimerax.prometheus.entity.Client;

import java.util.Optional;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 21-Apr-20
 * Time: 11:21 AM
 */
public interface ClientRepository extends ChimeraXRepository<Long, Client> {

    Optional<Client> findByClientId(final String clientId);
}
