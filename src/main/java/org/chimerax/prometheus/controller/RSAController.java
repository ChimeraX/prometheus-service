package org.chimerax.prometheus.controller;

import lombok.AllArgsConstructor;
import org.chimerax.prometheus.service.RSAService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 13-Jun-20
 * Time: 9:26 PM
 */

@RestController
@RequestMapping("/rsa")
@AllArgsConstructor
public class RSAController {

   private RSAService rsaService;

    @GetMapping
    public ResponseEntity<String> getPublicKey() {
        return ResponseEntity.ok(rsaService.getPublicKey());
    }
}
