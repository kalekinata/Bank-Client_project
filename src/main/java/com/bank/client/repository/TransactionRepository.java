package com.bank.client.repository;

import com.bank.client.models.db.Transaction;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, String> {

    @Query(value = "select new Transaction (t.id, t.dadd," +
            "       t.clid, t.accid_send, t.accid_recip," +
            "       t.type, t.sum," +
            "       t.commission, t.region," +
            "       t.status)" +
            "       from Transaction as t" +
            "       join FraudTran as ft on t.id = ft.trid" +
            "       where t.clid=:clid and ft.status_check is not null" +
            "       order by t.dadd")
    Iterable<Transaction> findAllClTr(String clid);

    @Query("from Transaction where clid=:clid")
    List<Transaction> findByClid(String clid);

    @Modifying
    @Transactional
    @Query("update Transaction t set t.status_check=:status_check where t.id=:trid")
    int setFixedTranCheck(String trid, String status_check);

    @Modifying
    @Transactional
    @Query("update Transaction t set t.status=:status where t.id=:trid")
    int setFixedTranStatus(String trid, String status);

    @Query(value = "from Transaction order by dadd desc")
    Iterable<Transaction> findAllSort();
}