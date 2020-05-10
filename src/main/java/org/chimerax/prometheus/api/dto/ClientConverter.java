package org.chimerax.prometheus.api.dto;

import org.chimerax.prometheus.entity.Client;
import org.springframework.stereotype.Component;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 21-Apr-20
 * Time: 5:24 PM
 */

@Component
public class ClientConverter {

    public ClientDTO convertToClientDTO(final Client client) {

        return new ClientDTO()
                .setName(client.getName())
                .setLogo(client.getLogo())
                .setClientId(client.getClientId())
                .setScope(client.getScope());

    }
}
