package org.bithacks.defidefender.model.Po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class IssuedCredential {

    @Id
    @GeneratedValue
    private int id;
    private String credentialOwner;

    public IssuedCredential() {
    }

    public IssuedCredential(String credentialOwner) {
        this.credentialOwner = credentialOwner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCredentialOwner() {
        return credentialOwner;
    }

    public void setCredentialOwner(String credentialOwner) {
        this.credentialOwner = credentialOwner;
    }
}
