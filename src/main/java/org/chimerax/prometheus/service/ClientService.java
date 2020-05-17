package org.chimerax.prometheus.service;

import lombok.AllArgsConstructor;
import org.chimerax.prometheus.api.dto.ClientConverter;
import org.chimerax.prometheus.api.dto.ClientDTO;
import org.chimerax.prometheus.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 21-Apr-20
 * Time: 5:04 PM
 */

@Service
@AllArgsConstructor
public class ClientService {

    private ClientRepository clientRepository;
    private ClientConverter clientConverter;

    public Optional<ClientDTO> findByClientId(final String clientId) {
        return clientRepository.findByClientId(clientId)
                .map(clientConverter::convertToClientDTO);
    }

}
