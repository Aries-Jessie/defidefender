package org.bithacks.defidefender.model.Po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Relation {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String weid;
    private String privateKey;
    private int type; // 0 - 用户 1 - 政府 2 - 机构

    public Relation() {
    }

    public Relation(String name, String weid, String privateKey, int type) {
        this.name = name;
        this.weid = weid;
        this.privateKey = privateKey;
        this.type = type;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeid() {
        return weid;
    }

    public void setWeid(String weid) {
        this.weid = weid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
