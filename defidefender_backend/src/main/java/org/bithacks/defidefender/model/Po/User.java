package org.bithacks.defidefender.model.Po;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    private String weid;
    private String name;
    private String gender;
    private String birthday;
    private String address;
    private String identityNumber;
    private String phoneNumber;
    private int status;

    public User() {
    }

    public User(String weid, String name, String gender, String birthday, String address, String identityNumber, String phoneNumber, int status) {
        this.weid = weid;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.address = address;
        this.identityNumber = identityNumber;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    public String getWeid() {
        return weid;
    }

    public void setWeid(String weid) {
        this.weid = weid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
