package org.chimerax.prometheus.service;

import lombok.AllArgsConstructor;
import org.chimerax.common.exception.NotFoundException;
import org.chimerax.prometheus.api.dto.UserInfoDTO;
import org.chimerax.prometheus.entity.Authority;
import org.chimerax.prometheus.entity.User;
import org.chimerax.prometheus.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 26-Apr-20
 * Time: 12:23 PM
 */

@Service
@AllArgsConstructor
public class UserInfoService {

    private UserRepository userRepository;

    public UserInfoDTO getUserInfo() {
        final UserDetails actingUser = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        final Optional<User> userOptional = userRepository.findByEmail(actingUser.getUsername());
        final User user = userOptional.orElseThrow(() -> new NotFoundException(actingUser.getUsername()));

        final Set<String> authorities = actingUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        final UserInfoDTO userInfo = new UserInfoDTO();

        if(authorities.contains(Authority.READ_PROFILE.getAuthority())) {
            handleProfile(userInfo, user);
        }

        if(authorities.contains(Authority.READ_CONTACT.getAuthority())) {
            handleContact(userInfo, user);
        }

        return userInfo;
    }

    public void handleContact(final UserInfoDTO userInfo, final User user) {
        userInfo.setPhoneNumber(user.getPhoneNumber());
    }

    public void handleProfile(final UserInfoDTO userInfo, final User user) {
        userInfo
                .setProfilePicture(user.getProfilePicture())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setEmail(user.getEmail());
    }
}
