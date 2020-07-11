package org.chimerax.prometheus.service.microservices;

import lombok.AllArgsConstructor;
import org.chimerax.prometheus.api.dto.ValidateDTO;
import org.chimerax.prometheus.api.mail.MailDTO;
import org.chimerax.prometheus.api.mail.MailType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 20-May-20
 * Time: 4:58 PM
 */

@Service
@AllArgsConstructor
public class MailService {

    private static final String URL = "http://hermes";
    private static final String MAIL_URL = URL + "/mail";
    private static final String VALIDATE_URL = URL + "/validate";

    private RestTemplate restTemplate;

    public void sendActivationEmail(final String email, final String user) {
        restTemplate.postForObject(MAIL_URL, new MailDTO()
                .setEmail(email)
                .setUser(user)
                .setMailType(MailType.REGISTRATION), Void.class);
    }

    public void sendAuthenticationEmail(final String email, final String user) {
        restTemplate.postForObject(MAIL_URL, new MailDTO()
                .setEmail(email)
                .setUser(user)
                .setMailType(MailType.AUTHENTICATION), Void.class);
    }

    public boolean validateCode(final String email, final String code) {
        ResponseEntity<Void> response = restTemplate.postForEntity(VALIDATE_URL, new ValidateDTO(email, code), Void.class);
        return response.getStatusCode().is2xxSuccessful();
    }


}
