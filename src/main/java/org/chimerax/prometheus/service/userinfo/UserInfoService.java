package org.chimerax.prometheus.service.userinfo;

import lombok.AllArgsConstructor;
import lombok.val;
import org.chimerax.common.exception.NotFoundException;
import org.chimerax.prometheus.api.dto.UserInfoDTO;
import org.chimerax.prometheus.entity.Authority;
import org.chimerax.prometheus.entity.User;
import org.chimerax.prometheus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    private final Map<Authority, UserInfoHandler> handlers = new HashMap<>();


    public UserInfoDTO getUserInfo() {
        final UserDetails actingUser = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return getUserInfo(actingUser, actingUser.getUsername());
    }

    public UserInfoDTO getUserInfo(final String email) {
        final UserDetails actingUser = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return getUserInfo(actingUser, email);
    }

    public UserInfoDTO getUserInfo(final List<String> authorities, final String email) {
        final Optional<User> userOptional = userRepository.findByEmail(email);
        final User user = userOptional.orElseThrow(() -> new NotFoundException(email));

        final UserInfoDTO userInfo = new UserInfoDTO();

        for (val authority : handlers.keySet()) {
            if (authorities.contains(authority.getAuthority())) {
                handlers.get(authority).handle(userInfo, user);
            }
        }
        return userInfo;
    }

    private UserInfoDTO getUserInfo(final UserDetails actingUser, final String email) {
        val authorities = actingUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return getUserInfo(authorities, email);
    }

    @Autowired
    private void postConstruct(final ApplicationContext context) {
        val beans = context.getBeansOfType(UserInfoHandler.class);
        beans.values().forEach((handler) -> handlers.put(handler.getRequiredAuthority(), handler));
    }
}
