package com.bank.client.models.request;

public class TranAcc {
    private String id;
    private String dadd;
    private String region;
    private String status;
    private Float sum;
    private Float com;
    private String clid_send;
    private String accid_send;
    private String clid_recip;
    private String accid_recip;

    public TranAcc(){};

    public TranAcc(String id, String dadd, String region, String status, Float sum, Float com, String clid_send, String accid_send, String clid_recip, String accid_recip) {
        this.id = id;
        this.dadd = dadd;
        this.region = region;
        this.status = status;
        this.sum = sum;
        this.com = com;
        this.clid_send = clid_send;
        this.accid_send = accid_send;
        this.clid_recip = clid_recip;
        this.accid_recip = accid_recip;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDadd() {
        return dadd;
    }

    public void setDadd(String dadd) {
        this.dadd = dadd;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Float getSum() {
        return sum;
    }

    public void setSum(Float sum) {
        this.sum = sum;
    }

    public Float getCom() {
        return com;
    }

    public void setCom(Float com) {
        this.com = com;
    }

    public String getClid_send() {
        return clid_send;
    }

    public void setClid_send(String clid_send) {
        this.clid_send = clid_send;
    }

    public String getAccid_send() {
        return accid_send;
    }

    public void setAccid_send(String accid_send) {
        this.accid_send = accid_send;
    }

    public String getClid_recip() {
        return clid_recip;
    }

    public void setClid_recip(String clid_recip) {
        this.clid_recip = clid_recip;
    }

    public String getAccid_recip() {
        return accid_recip;
    }

    public void setAccid_recip(String accid_recip) {
        this.accid_recip = accid_recip;
    }
}
