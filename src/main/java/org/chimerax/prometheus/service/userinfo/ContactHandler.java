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
public class ContactHandler implements UserInfoHandler {

    @Override
    public void handle(UserInfoDTO userInfo, User user) {
        userInfo.setPhoneNumber(user.getPhoneNumber());
    }

    @Override
    public Authority getRequiredAuthority() {
        return Authority.READ_CONTACT;
    }
}
