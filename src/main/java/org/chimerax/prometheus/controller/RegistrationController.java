package org.chimerax.prometheus.controller;

import lombok.AllArgsConstructor;
import org.chimerax.prometheus.api.authentication.AuthenticationRequest;
import org.chimerax.prometheus.api.authentication.AuthenticationResponse;
import org.chimerax.prometheus.api.registration.RegistrationRequest;
import org.chimerax.prometheus.api.registration.RegistrationResponse;
import org.chimerax.prometheus.service.AuthenticationService;
import org.chimerax.prometheus.service.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 21-Apr-20
 * Time: 12:03 PM
 */

@RestController
@RequestMapping("/register")
@AllArgsConstructor
public class RegistrationController {

    private RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<RegistrationResponse> register(@RequestBody final RegistrationRequest request) {
        registrationService.register(request);
        return ResponseEntity.ok(new RegistrationResponse());
    }
}
