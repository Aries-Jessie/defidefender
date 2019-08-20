package org.bithacks.defidefender.model.Vo;

import java.io.Serializable;

public class BlacklistEntity implements Serializable {
    private String weid; // 谁违信
    private String record; // 违信记录
    private String publisher; // 发布者
    private String createdTime; // 创建时间

    public BlacklistEntity() {
    }

    public String getWeid() {
        return weid;
    }

    public void setWeid(String weid) {
        this.weid = weid;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public BlacklistEntity(String weid, String record, String publisher, String createdTime) {
        this.weid = weid;
        this.record = record;
        this.publisher = publisher;
        this.createdTime = createdTime;
    }
}
