package org.chimerax.prometheus.service;

import lombok.AllArgsConstructor;
import org.chimerax.prometheus.api.registration.RegistrationRequest;
import org.chimerax.prometheus.entity.User;
import org.chimerax.prometheus.exception.EmailInUseException;
import org.chimerax.prometheus.exception.PasswordsDoNotMatchException;
import org.chimerax.prometheus.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 26-Apr-20
 * Time: 1:55 PM
 */

@Service
@AllArgsConstructor
public class RegistrationService {

    private UserRepository userRepository;

    public void register(final RegistrationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailInUseException(request.getEmail());
        }

        if(!request.getPassword().equals(request.getConfirmPassword())) {
            throw new PasswordsDoNotMatchException();
        }

        final User user = new User()
                .setEmail(request.getEmail())
                .setFirstName(request.getFirstName())
                .setLastName(request.getLastName())
                .setPassword(request.getPassword());

        userRepository.save(user);
    }
}
