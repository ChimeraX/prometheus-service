package org.chimerax.prometheus.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 20-Apr-20
 * Time: 7:30 PM
 */
@Data
@Entity
@Table(name = "clients", schema = "clients")
@Accessors(chain = true)
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String clientId;

    private String logo;

    private String secret;

    @ElementCollection(targetClass = Scope.class)
    @JoinTable(schema = "clients",
            name = "client_scopes",
            joinColumns = @JoinColumn(name = "client_id", referencedColumnName = "id"))
    @Enumerated(EnumType.STRING)
    private Set<Scope> scope;
}
