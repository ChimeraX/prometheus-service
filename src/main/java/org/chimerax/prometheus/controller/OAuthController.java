package org.chimerax.prometheus.controller;

import lombok.AllArgsConstructor;
import org.chimerax.prometheus.api.authorization.*;
import org.chimerax.prometheus.api.dto.UserInfoDTO;
import org.chimerax.prometheus.service.OAuthService;
import org.chimerax.prometheus.service.userinfo.UserInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 21-Apr-20
 * Time: 12:03 PM
 */

@RestController
@RequestMapping("/oauth")
@AllArgsConstructor
public class OAuthController {

    private OAuthService oAuthService;
    private UserInfoService userInfoService;

    @Secured({"ROLE_USER"})
    @PostMapping("/authorize")
    public ResponseEntity<AuthorizationCodeResponse> authorize(@RequestBody final AuthorizationCodeRequest request) {
        final String code = oAuthService.authorize(request.getClientId(), request.getScope());
        return ResponseEntity.ok(new AuthorizationCodeResponse(code));
    }

    @PostMapping("/token")
    public ResponseEntity<AuthorizationTokenResponse> generateToken(@RequestBody final AuthorizationTokenRequest request) {
        final String token = oAuthService.generateToken(
                request.getClientId(), request.getSecret(), request.getCode());
        return ResponseEntity.ok(new AuthorizationTokenResponse(token));
    }

    @PostMapping("/exchange")
    public ResponseEntity<ExchangeCodesResponse> generateSingleCode(@RequestBody final ExchangeCodesRequest request) {
        final String code = oAuthService.generateSingleCode(
                request.getClientId(), request.getSecret(), request.getCodes());
        return ResponseEntity.ok(new ExchangeCodesResponse(code));
    }

    @GetMapping("/userinfo")
    public ResponseEntity<UserInfoDTO> userInfo() {
        return ResponseEntity.ok(userInfoService.getUserInfo());
    }

    @GetMapping("/userinfo/{email}")
    public ResponseEntity<UserInfoDTO> userInfo(@PathVariable String email) {
        return ResponseEntity.ok(userInfoService.getUserInfo(email));
    }

    @PostMapping("/users")
    public ResponseEntity<ClientUsersResponse> findUsers(@RequestBody final ClientUsersRequest request) {
        return ResponseEntity.ok(new ClientUsersResponse(oAuthService.findUsers(
                request.getClientId(),
                request.getSecret(),
                request.getUsers())
        ));
    }
}
