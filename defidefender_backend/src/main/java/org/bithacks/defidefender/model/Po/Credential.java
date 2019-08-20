package org.bithacks.defidefender.model.Po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Credential {
    @Id
    @GeneratedValue
    private int id;
    private String weid;
    @Lob
    private String credential;
    private int type;

    public Credential() {
    }

    public Credential(String weid, String credential, int type) {
        this.weid = weid;
        this.credential = credential;
        this.type = type;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWeid() {
        return weid;
    }

    public void setWeid(String weid) {
        this.weid = weid;
    }
}
