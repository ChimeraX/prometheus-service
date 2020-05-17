package org.chimerax.prometheus.service;

import lombok.AllArgsConstructor;
import org.chimerax.prometheus.api.registration.RegistrationRequest;
import org.chimerax.prometheus.entity.User;
import org.chimerax.prometheus.exception.EmailInUseException;
import org.chimerax.prometheus.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private DocumentService documentService;
    private PasswordEncoder passwordEncoder;


    public void register(final RegistrationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailInUseException(request.getEmail());
        }

        User user = new User()
                .setActive(false)
                .setEmail(request.getEmail())
                .setFirstName(request.getFirstName())
                .setLastName(request.getLastName())
                .setPassword(passwordEncoder.encode(request.getPassword()));

        user = userRepository.save(user);

        String profilePicture =  documentService.save(request.getProfilePicture());

        user.setProfilePicture(profilePicture);

        userRepository.save(user);
    }
}
