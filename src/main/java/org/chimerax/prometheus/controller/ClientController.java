package org.chimerax.prometheus.controller;

import lombok.AllArgsConstructor;
import org.chimerax.prometheus.api.dto.ClientDTO;
import org.chimerax.prometheus.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 21-Apr-20
 * Time: 5:03 PM
 */

@RestController
@AllArgsConstructor
@RequestMapping("/clients")
public class ClientController {

    private ClientService clientService;

    @GetMapping("/{clientId}")
    public ResponseEntity<ClientDTO> findById(@PathVariable final String clientId) {
        Optional<ClientDTO> optionalClientDTO = clientService.findByClientId(clientId);
        return optionalClientDTO
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
