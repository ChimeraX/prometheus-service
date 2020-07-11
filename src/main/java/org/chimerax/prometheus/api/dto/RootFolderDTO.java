package org.chimerax.prometheus.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 24-May-20
 * Time: 8:08 PM
 */

@Data
@Accessors(chain = true)
public class RootFolderDTO {

    String username;
}
