package org.chimerax.prometheus.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 26-Apr-20
 * Time: 2:00 PM
 */

public class RegistrationException extends RuntimeException{

    public RegistrationException() {
    }

    public RegistrationException(String message) {
        super(message);
    }
}
