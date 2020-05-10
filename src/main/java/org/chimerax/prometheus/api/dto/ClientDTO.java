package org.chimerax.prometheus.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.chimerax.prometheus.entity.Scope;

import java.util.Set;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 21-Apr-20
 * Time: 5:05 PM
 */

@Data
@Accessors(chain = true)
public class ClientDTO {

    private String name;

    private String logo;

    private String clientId;

    private Set<Scope> scope;
}
