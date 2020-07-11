package org.chimerax.prometheus.api.authorization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.chimerax.prometheus.api.dto.UserInfoDTO;

import java.util.Map;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 21-Apr-20
 * Time: 12:04 PM
 */
@Getter
@AllArgsConstructor
public class ClientUsersResponse {
    private Map<String, UserInfoDTO> users;
}
