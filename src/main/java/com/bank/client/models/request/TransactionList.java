package com.bank.client.models.request;

import com.google.gson.annotations.SerializedName;

public class TransactionList {

    @SerializedName("trid")
    private String id;

    @SerializedName("dadd")
    private String dadd;

    @SerializedName("region")
    private String region;

    @SerializedName("sum")
    private float sum;

    @SerializedName("com")
    private float commission;

    @SerializedName("clSender")
    private ClientS clientS;
    public static class ClientS {

        @SerializedName("clid")
        private String clid;
        @SerializedName("surname")
        private String surname;
        @SerializedName("name")
        private String name;
        @SerializedName("patronymic")
        private String patronymic;
        @SerializedName("account")
        private AccS accS;

        public String getClid() {
            return clid;
        }

        public void setClid(String clid) {
            this.clid = clid;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPatronymic() {
            return patronymic;
        }

        public void setPatronymic(String patronymic) {
            this.patronymic = patronymic;
        }

        public AccS getAccS() {
            return accS;
        }

        public void setAccS(AccS accS) {
            this.accS = accS;
        }

        public static class AccS{
            @SerializedName("accid")
            private String accid;
            @SerializedName("bic")
            private String bic;

            public String getAccid() {
                return accid;
            }

            public void setAccid(String accid) {
                this.accid = accid;
            }

            public String getBic() {
                return bic;
            }

            public void setBic(String bic) {
                this.bic = bic;
            }
        }

    }

    @SerializedName("clRecip")
    private ClientR clientR;
    public static class ClientR {

        @SerializedName("clid")
        private String clid;
        @SerializedName("surname")
        private String surname;
        @SerializedName("name")
        private String name;
        @SerializedName("patronymic")
        private String patronymic;
        @SerializedName("account")
        private AccR accR;

        public static class AccR{
            @SerializedName("accid")
            private String accid;
            @SerializedName("bic")
            private String bic;

            public String getAccid() {
                return accid;
            }

            public void setAccid(String accid) {
                this.accid = accid;
            }

            public String getBic() {
                return bic;
            }

            public void setBic(String bic) {
                this.bic = bic;
            }
        }

        public String getClid() {
            return clid;
        }

        public void setClid(String clid) {
            this.clid = clid;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPatronymic() {
            return patronymic;
        }

        public void setPatronymic(String patronymic) {
            this.patronymic = patronymic;
        }

        public AccR getAccR() {
            return accR;
        }

        public void setAccR(AccR accR) {
            this.accR = accR;
        }
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

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }

    public float getCommission() {
        return commission;
    }

    public void setCommission(float commission) {
        this.commission = commission;
    }

    public ClientS getClientS() {
        return clientS;
    }

    public void setClientS(ClientS clientS) {
        this.clientS = clientS;
    }

    public ClientR getClientR() {
        return clientR;
    }

    public void setClientR(ClientR clientR) {
        this.clientR = clientR;
    }
}
