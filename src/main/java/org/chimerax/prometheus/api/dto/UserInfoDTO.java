package org.chimerax.prometheus.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 26-Apr-20
 * Time: 3:36 PM
 */

@Data
@Accessors(chain = true)
public class UserInfoDTO {

    private String firstName;

    private String lastName;

    private String email;

    private String profilePicture;

    private String country;

    private String phoneNumber;
}
