package org.chimerax.prometheus.service.userinfo;

import org.chimerax.prometheus.api.dto.UserInfoDTO;
import org.chimerax.prometheus.entity.Authority;
import org.chimerax.prometheus.entity.User;
import org.springframework.stereotype.Component;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 08-May-20
 * Time: 7:34 PM
 */

@Component
public class ProfileHandler implements UserInfoHandler {

    @Override
    public void handle(UserInfoDTO userInfo, User user) {
        userInfo
                .setProfilePicture(user.getProfilePicture())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setEmail(user.getEmail());
    }

    @Override
    public Authority getRequiredAuthority() {
        return Authority.READ_PROFILE;
    }
}
