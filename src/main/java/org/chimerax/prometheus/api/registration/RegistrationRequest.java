package org.chimerax.prometheus.api.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chimerax.prometheus.api.dto.CreateDocumentDTO;

import javax.validation.constraints.Email;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 21-Apr-20
 * Time: 12:04 PM
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {
    private String firstName;

    private String lastName;

    @Email
    private String email;

    private String password;

    private String confirmPassword;

    private CreateDocumentDTO profilePicture;

}
