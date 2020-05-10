package org.chimerax.prometheus.service.userinfo;

import org.chimerax.prometheus.api.dto.UserInfoDTO;
import org.chimerax.prometheus.entity.Authority;
import org.chimerax.prometheus.entity.User;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 08-May-20
 * Time: 7:34 PM
 */
public interface UserInfoHandler {

    void handle(final UserInfoDTO userInfo, final User user);

    Authority getRequiredAuthority();
}
