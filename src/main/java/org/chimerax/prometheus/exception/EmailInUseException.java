package org.chimerax.prometheus.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 26-Apr-20
 * Time: 2:01 PM
 */

@ResponseStatus(HttpStatus.CONFLICT)
public class EmailInUseException extends RegistrationException {

    public EmailInUseException() {
        this("Email already in use");
    }

    public EmailInUseException(String message) {
        super(message);
    }
}
