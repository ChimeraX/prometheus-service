package org.chimerax.prometheus.controller;

import lombok.AllArgsConstructor;
import org.chimerax.prometheus.api.authentication.AuthenticationRequest;
import org.chimerax.prometheus.api.authentication.AuthenticationResponse;
import org.chimerax.prometheus.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 21-Apr-20
 * Time: 12:03 PM
 */

@RestController
@RequestMapping("/authenticate")
@AllArgsConstructor
public class AuthenticationController {

    private AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody final AuthenticationRequest request) {
        final  String token = authenticationService.authenticate(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
}
