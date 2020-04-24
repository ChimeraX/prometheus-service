package org.chimerax.prometheus.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 *
 * Representing all the billing information for online payment
 *
 * Author: Silviu-Mihnea Cucuiet
 * Date: 19-Apr-20
 * Time: 7:04 PM
 */

@Data
@Entity
@Table(name = "credit_cards", schema = "billing")
@Accessors(chain = true)
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String type;

    private Date expirationDate;

    @Pattern(regexp = "[a-zA-Z ]+")
    private String name;

    @Column(length = 16)
    @Pattern(regexp = "[0-9]{16}")
    private String number;

    @Pattern(regexp = "[0-9]{3}")
    private String cvc;

    private long userId;
}
