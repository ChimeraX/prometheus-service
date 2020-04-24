package org.chimerax.prometheus.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 19-Apr-20
 * Time: 9:25 PM
 */

@Data
@Entity
@Table(name = "addresses", schema = "billing")
@Accessors(chain = true)
public class BillingAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String country;

    private String street;

    private int streetNumber;

    @Pattern(regexp = "[0-9]{4}")
    private String zipCode;

    private long userId;

    private String details;
}
