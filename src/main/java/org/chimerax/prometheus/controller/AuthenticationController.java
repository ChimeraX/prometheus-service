package org.chimerax.prometheus.controller;

import lombok.AllArgsConstructor;
import org.chimerax.prometheus.api.authentication.AuthenticationRequest;
import org.chimerax.prometheus.service.AuthenticationService;
import org.chimerax.prometheus.service.RSAService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

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
    private RSAService rsaService;

    @PostMapping
    public ResponseEntity<String> authenticate(@RequestBody final AuthenticationRequest request, final HttpServletResponse response) {
        request.setPassword(rsaService.decrypt(request.getPassword()));
        final  String token = authenticationService.authenticate(request.getEmail(), request.getPassword());
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/currentUser")
    public ResponseEntity<Void> isAuthenticated() {
        return ResponseEntity.ok().build();
    }

}
