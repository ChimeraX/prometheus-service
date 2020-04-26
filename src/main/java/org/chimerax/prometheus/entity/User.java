package org.chimerax.prometheus.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Class that represents a customer information
 *
 * Author: Silviu-Mihnea Cucuiet
 * Date: 21-Apr-20
 * Time: 10:01 AM
 */

@Data
@Entity
@Table(name = "users", schema = "users")
@Accessors(chain = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;

    private String lastName;

    private String password;

    @Column(unique = true, nullable = false, updatable = false)
    private String email;

    private String profilePicture;

    private String country;

    private String phoneNumber;

    private boolean active;
}
