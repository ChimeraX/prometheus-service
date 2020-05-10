package org.chimerax.prometheus.service.userinfo;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class UserHandler implements UserInfoHandler {

    private ProfileHandler profileHandler;
    private ContactHandler contactHandler;

    @Override
    public void handle(UserInfoDTO userInfo, User user) {
        profileHandler.handle(userInfo, user);
        contactHandler.handle(userInfo, user);
    }

    @Override
    public Authority getRequiredAuthority() {
        return Authority.USER;
    }
}
