package org.chimerax.prometheus.controller;

import lombok.AllArgsConstructor;
import org.chimerax.prometheus.api.authorization.AuthorizationCodeRequest;
import org.chimerax.prometheus.api.authorization.AuthorizationCodeResponse;
import org.chimerax.prometheus.api.authorization.AuthorizationTokenRequest;
import org.chimerax.prometheus.api.authorization.AuthorizationTokenResponse;
import org.chimerax.prometheus.service.AuthorizationService;
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
@RequestMapping("/oauth")
@AllArgsConstructor
public class AuthorizationController {

    private AuthorizationService authorizationService;

    @PostMapping("/authorize")
    public ResponseEntity<AuthorizationCodeResponse> authorize(@RequestBody final AuthorizationCodeRequest request) {
        final String code = authorizationService.authorize(request.getClientId(), request.getScope());
        return ResponseEntity.ok(new AuthorizationCodeResponse(code));
    }

    @PostMapping("/token")
    public ResponseEntity<AuthorizationTokenResponse> token(@RequestBody final AuthorizationTokenRequest request) {
        final String token = authorizationService.token(request.getClientId(), request.getSecret(), request.getCode());
        return ResponseEntity.ok(new AuthorizationTokenResponse(token));
    }
}
