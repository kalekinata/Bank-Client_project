package com.bank.client.models.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "frod_tran")
public class FraudTran {
    @Id
    @Column(name = "id")
    private String checkid;
    private String dadd;
    private String trid;
    private String status_check;
    private String description;
    public FraudTran(){}

    public FraudTran(String checkid, String dadd, String trid, String status_check, String description) {
        this.checkid = checkid;
        this.dadd = dadd;
        this.trid = trid;
        this.status_check = status_check;
        this.description = description;
    }

    public String getCheckid() {
        return checkid;
    }

    public void setCheckid(String checkid) {
        this.checkid = checkid;
    }

    public String getDadd() {
        return dadd;
    }

    public void setDadd(String dadd) {
        this.dadd = dadd;
    }

    public String getTrid() {
        return trid;
    }

    public void setTrid(String trid) {
        this.trid = trid;
    }

    public String getStatus_check() {
        return status_check;
    }

    public void setStatus_check(String status_check) {
        this.status_check = status_check;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
