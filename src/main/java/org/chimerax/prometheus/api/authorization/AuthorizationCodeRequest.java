package org.chimerax.prometheus.api.authorization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.chimerax.prometheus.entity.Scope;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 21-Apr-20
 * Time: 12:04 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizationCodeRequest {

    @NotBlank
    private String clientId;


    private List<Scope> scope;
}
