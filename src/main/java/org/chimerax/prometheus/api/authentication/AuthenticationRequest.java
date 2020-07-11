package org.chimerax.prometheus.api.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 21-Apr-20
 * Time: 12:04 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    @Email
    private String email;

    @NotBlank
    private String password;

    private String code;
}
