package org.chimerax.prometheus.service;

import lombok.AllArgsConstructor;
import org.chimerax.common.exception.NotFoundException;
import org.chimerax.prometheus.api.dto.RootFolderDTO;
import org.chimerax.prometheus.api.registration.RegistrationRequest;
import org.chimerax.prometheus.entity.User;
import org.chimerax.prometheus.exception.EmailInUseException;
import org.chimerax.prometheus.repository.UserRepository;
import org.chimerax.prometheus.service.microservices.DocumentService;
import org.chimerax.prometheus.service.microservices.MailService;
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
    private MailService mailService;


    public boolean register(final RegistrationRequest request) {
        if (request.getCode() != null && !request.getCode().equals("")) {
            return activateNewAccount(request);
        } else {
            return createNewAccount(request);
        }
    }

    private boolean activateNewAccount(final RegistrationRequest request) {
        if (!userRepository.existsByEmail(request.getEmail())) {
            throw new NotFoundException(request.getEmail());
        }

        final User user = userRepository.findByEmail(request.getEmail()).get();

        boolean valid = mailService.validateCode(request.getEmail(), request.getCode());

        user.setActive(valid);

        userRepository.save(user);
        return valid;
    }

    private boolean createNewAccount(final RegistrationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailInUseException(request.getEmail());
        }

        User user = new User()
                .setActive(false)
                .setEmail(request.getEmail())
                .setFirstName(request.getFirstName())
                .setLastName(request.getLastName())
                .setPassword(passwordEncoder.encode(request.getPassword()));

        mailService.sendActivationEmail(user.getEmail(), user.getFirstName());
        long folderId = documentService.saveRoot(new RootFolderDTO().setUsername(user.getEmail()));
        String profilePicture = documentService.save(request.getProfilePicture().setFolderId(folderId));

        user.setProfilePicture(profilePicture);

        userRepository.save(user);
        return true;
    }
}
