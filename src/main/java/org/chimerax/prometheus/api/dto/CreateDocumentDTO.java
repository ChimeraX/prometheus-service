package org.chimerax.prometheus.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 13-May-20
 * Time: 5:09 PM
 */

@Data
@Accessors(chain = true)
public class CreateDocumentDTO {

    private String name;

    private String type;

    private String data;

    private long size;
}
