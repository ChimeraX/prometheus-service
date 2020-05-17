package org.chimerax.prometheus.controller;

import lombok.AllArgsConstructor;
import org.chimerax.prometheus.api.authorization.*;
import org.chimerax.prometheus.api.dto.UserInfoDTO;
import org.chimerax.prometheus.service.AuthorizationService;
import org.chimerax.prometheus.service.userinfo.UserInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

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
    private UserInfoService userInfoService;

    @Secured({"ROLE_USER"})
    @PostMapping("/authorize")
    public ResponseEntity<AuthorizationCodeResponse> authorize(@RequestBody final AuthorizationCodeRequest request) {
        final String code = authorizationService.authorize(request.getClientId(), request.getScope());
        return ResponseEntity.ok(new AuthorizationCodeResponse(code));
    }

    @PostMapping("/token")
    public ResponseEntity<AuthorizationTokenResponse> generateToken(@RequestBody final AuthorizationTokenRequest request) {
        final String token = authorizationService.generateToken(
                request.getClientId(), request.getSecret(), request.getCode());
        return ResponseEntity.ok(new AuthorizationTokenResponse(token));
    }

    @PostMapping("/exchange")
    public ResponseEntity<ExchangeCodesResponse> generateSingleCode(@RequestBody final ExchangeCodesRequest request) {
        final String code = authorizationService.generateSingleCode(
                request.getClientId(), request.getSecret(), request.getCodes());
        return ResponseEntity.ok(new ExchangeCodesResponse(code));
    }

    @GetMapping("/userinfo")
    public ResponseEntity<UserInfoDTO> userInfo() {
        return ResponseEntity.ok(userInfoService.getUserInfo());
    }
}
