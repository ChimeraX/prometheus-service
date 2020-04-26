package org.chimerax.prometheus.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 26-Apr-20
 * Time: 2:01 PM
 */

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class PasswordsDoNotMatchException extends RegistrationException {

    public PasswordsDoNotMatchException() {
    }

    public PasswordsDoNotMatchException(String message) {
        super(message);
    }
}
