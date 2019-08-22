package org.bithacks.defidefender.model.Po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class LoanRecord {
    @Id
    @GeneratedValue
    private int id;
    private String weid; // 谁借款
    private String companyName; // 借款机构名
    private double amount; // 借款金额
    private int durationMonth; // 借款日期
    private String credentialOwner; // 凭证所有者
    private int isUserSelf; // 0 - 待确认 1 - 是 2 - 不是
    private int isCredentialVerified; // 0 - 待确认 1 - 已核验 2 - 未通过核验
    private double dailyRate; // 利率
    private String createdTime; // 记录的创建时间
    private String effectiveTime; // 记录的生效时间(放款的时间)
    private String repayTime; // 还款时间
    private String endTime; // 最后的截止时间
    private int status; // 0 - 待确认 1 - 已确认，未还款 2 - 已确认，已还款 3 - 已确认，超时未还 4 - 未确认 5 - 已加入黑名单

    public LoanRecord() {
    }

    public LoanRecord(String weid, String companyName, double amount, int durationMonth, String credentialOwner, int isUserSelf, int isCredentialVerified, double dailyRate, String createdTime, int status) {
        this.weid = weid;
        this.companyName = companyName;
        this.amount = amount;
        this.durationMonth = durationMonth;
        this.credentialOwner = credentialOwner;
        this.isUserSelf = isUserSelf;
        this.isCredentialVerified = isCredentialVerified;
        this.dailyRate = dailyRate;
        this.createdTime = createdTime;
        this.status = status;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(String effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public String getRepayTime() {
        return repayTime;
    }

    public void setRepayTime(String repayTime) {
        this.repayTime = repayTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getDurationMonth() {
        return durationMonth;
    }

    public void setDurationMonth(int durationMonth) {
        this.durationMonth = durationMonth;
    }

    public String getCredentialOwner() {
        return credentialOwner;
    }

    public void setCredentialOwner(String credentialOwner) {
        this.credentialOwner = credentialOwner;
    }

    public int getIsUserSelf() {
        return isUserSelf;
    }

    public void setIsUserSelf(int isUserSelf) {
        this.isUserSelf = isUserSelf;
    }

    public int getIsCredentialVerified() {
        return isCredentialVerified;
    }

    public void setIsCredentialVerified(int isCredentialVerified) {
        this.isCredentialVerified = isCredentialVerified;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
