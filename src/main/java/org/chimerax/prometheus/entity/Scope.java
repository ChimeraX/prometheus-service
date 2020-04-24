package org.chimerax.prometheus.entity;

/**
 * Author: Silviu-Mihnea Cucuiet
 * Date: 21-Apr-20
 * Time: 5:43 PM
 */

public enum Scope {

    // username, firstName, lastName, email, profilePicture
    PROFILE,

    // phoneNumber, country
    CONTACT,

    FILES,

    // creditCards and billingAddresses
    BILLING,

    // full authorities
    USER,
    ;

    /**
     * Returns a description key that can be used to retrieve a description from the database
     * to describe to the user what the scope mean
     * @return the description key used for retrieving the description
     */
    public String getDescriptionKey() {
        return "SCOPE_" + name() + "_DESCRIPTION";
    }

}
