package org.chimerax.prometheus.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 21-Apr-20
 * Time: 11:05 AM
 */

@Data
@Entity
@Table(name = "granted_access", schema = "clients")
@Accessors(chain = true)
public class GrantedAccess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    private String clientId;

    @Enumerated(EnumType.STRING)
    @CollectionTable(
            schema = "clients",
            name = "granted_access_authority",
            joinColumns = {
                    @JoinColumn(name = "ga_id", referencedColumnName = "id")
            }
    )
    @ElementCollection(targetClass = Authority.class)
    private Set<Authority> authorities = new HashSet<>();

    @Column(unique = true, nullable = false)
    private String code;
}
