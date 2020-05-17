package org.chimerax.prometheus.exception;

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
