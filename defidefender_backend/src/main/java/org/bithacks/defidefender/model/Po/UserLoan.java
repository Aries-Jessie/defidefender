package org.bithacks.defidefender.model.Po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserLoan {
    @Id
    @GeneratedValue
    private int id;
    private String weid;
    private String companyName;
    private double loanAmount;
    private double repayAmount;

    public UserLoan() {
    }

    public UserLoan(String weid, String companyName, double loanAmount) {
        this.weid = weid;
        this.companyName = companyName;
        this.loanAmount = loanAmount;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double getRepayAmount() {
        return repayAmount;
    }

    public void setRepayAmount(double repayAmount) {
        this.repayAmount = repayAmount;
    }
}
