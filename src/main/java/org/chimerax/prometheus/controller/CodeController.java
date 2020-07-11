package org.chimerax.prometheus.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 10-Jun-20
 * Time: 4:10 PM
 */

@RestController
@RequestMapping("/t")
public class CodeController {

    @GetMapping("")
    public void _csrf(final HttpServletResponse response) {
        Cookie cookie = new Cookie("_csrf", random());
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);
    }

    @GetMapping("/cnounce")
    public String cnounce() {
        return random();
    }

    @GetMapping("/rsakey")
    public String rsaKey() {
        return random();
    }

    private String random() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
