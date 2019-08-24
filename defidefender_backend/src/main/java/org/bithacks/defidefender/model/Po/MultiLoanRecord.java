package org.bithacks.defidefender.model.Po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class MultiLoanRecord {
    @Id
    @GeneratedValue
    private int id;
    private int loanRecordId;
    private String requester;
    private String companyName;
    private String companyNameEncryption;
    private String weid;
    private String ciphereText;
    private String createdTime;
    private String responseTime;
    private int multiCount;
    private double multiLoanAmount;
    private int status; // 判断是否响应 0 - 等待 1 - 响应成功 2 - 拒绝响应


    public MultiLoanRecord() {
    }

    public MultiLoanRecord(int loanRecordId, String requester, String companyName, String companyNameEncryption, String weid, String ciphereText, String createdTime, int status) {
        this.loanRecordId = loanRecordId;
        this.requester = requester;
        this.companyName = companyName;
        this.companyNameEncryption = companyNameEncryption;
        this.weid = weid;
        this.ciphereText = ciphereText;
        this.createdTime = createdTime;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLoanRecordId() {
        return loanRecordId;
    }

    public void setLoanRecordId(int loanRecordId) {
        this.loanRecordId = loanRecordId;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCiphereText() {
        return ciphereText;
    }

    public void setCiphereText(String ciphereText) {
        this.ciphereText = ciphereText;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public int getMultiCount() {
        return multiCount;
    }

    public void setMultiCount(int multiCount) {
        this.multiCount = multiCount;
    }

    public double getMultiLoanAmount() {
        return multiLoanAmount;
    }

    public void setMultiLoanAmount(double multiLoanAmount) {
        this.multiLoanAmount = multiLoanAmount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCompanyNameEncryption() {
        return companyNameEncryption;
    }

    public void setCompanyNameEncryption(String companyNameEncryption) {
        this.companyNameEncryption = companyNameEncryption;
    }

    public String getWeid() {
        return weid;
    }

    public void setWeid(String weid) {
        this.weid = weid;
    }
}
