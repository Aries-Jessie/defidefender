package org.bithacks.defidefender.model.Po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CPT {
    @Id
    @GeneratedValue
    private int id;
    private int cptId;
    private String creatorWeid;

    public CPT() {
    }

    public CPT(int cptId, String creatorWeid) {
        this.cptId = cptId;
        this.creatorWeid = creatorWeid;
    }

    public String getCreatorWeid() {
        return creatorWeid;
    }

    public void setCreatorWeid(String creatorWeid) {
        this.creatorWeid = creatorWeid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCptId() {
        return cptId;
    }

    public void setCptId(int cptId) {
        this.cptId = cptId;
    }
}
