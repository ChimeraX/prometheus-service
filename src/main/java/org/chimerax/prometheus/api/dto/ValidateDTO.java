package org.chimerax.prometheus.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 03-Jun-20
 * Time: 7:59 PM
 */

@Data
@AllArgsConstructor
public class ValidateDTO {

    private String email;

    private String code;

}
