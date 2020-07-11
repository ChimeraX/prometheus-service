package org.chimerax.prometheus.api.mail;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 03-Jun-20
 * Time: 7:59 PM
 */

@Data
@Accessors(chain = true)
public class MailDTO {

    private MailType mailType;

    @Email
    private String email;

    @NotBlank
    private String user;
}
