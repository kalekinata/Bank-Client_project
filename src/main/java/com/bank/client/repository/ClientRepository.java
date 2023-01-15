package com.bank.client.repository;

import com.bank.client.models.db.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, String> {

    @Query("from Client where name=:name and patronymic=:patronymic and surname=:surname")
    Client findBySurnameAndNameAndPatronymic(String name, String patronymic, String surname);
}
