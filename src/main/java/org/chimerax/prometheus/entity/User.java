package org.chimerax.prometheus.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

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

    @Column(unique = true, updatable = false)
    private String userId;

    private String firstName;

    private String lastName;

    private String password;

    @Column(unique = true, nullable = false, updatable = false)
    private String email;

    private String profilePicture;

    private boolean active;

    @Column(name = "created_at", updatable = false )
    private long createdAt;

    @PrePersist
    void prePersist() {
        createdAt = new Date().getTime();
        userId = UUID.randomUUID().toString().replace("-", "");
    }
}
