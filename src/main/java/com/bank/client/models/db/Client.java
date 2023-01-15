package com.bank.client.models.db;

import jakarta.persistence.*;

@Entity
@Table(name="client")
public class Client {
    @Id
    private String id;
    private String dadd;
    private String surname;
    private String name;
    private String patronymic;

    private String dclose;

    public Client(){}

    public Client(String id, String dadd, String surname, String name, String patronymic, String dclose) {
        this.id = id;
        this.dadd = dadd;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.dclose = dclose;
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

    public String getDclose() {
        return dclose;
    }

    public void setDclose(String dclose) {
        this.dclose = dclose;
    }
}
