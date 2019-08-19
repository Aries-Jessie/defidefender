package org.bithacks.defidefender.model.Po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class LoanRecord {
    @Id
    @GeneratedValue
    private int id;
    private String weid;
    private String companyName;
    private double amount;
    private String expiredDate;
    private String credentialOwner;
    private int status; // 0 - 待确认 1 - 已确认，未还款 2 - 已确认，已还款 3 - 已确认，超时未还 4 - 未确认 5 - 已加入黑名单


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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getCredentialOwner() {
        return credentialOwner;
    }

    public void setCredentialOwner(String credentialOwner) {
        this.credentialOwner = credentialOwner;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LoanRecord() {
    }

    public LoanRecord(String weid, String companyName, double amount, String expiredDate, String credentialOwner, int status) {
        this.weid = weid;
        this.companyName = companyName;
        this.amount = amount;
        this.expiredDate = expiredDate;
        this.credentialOwner = credentialOwner;
        this.status = status;
    }
}
