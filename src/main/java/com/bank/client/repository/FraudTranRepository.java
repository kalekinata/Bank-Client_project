package com.bank.client.repository;

import com.bank.client.models.db.FraudTran;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface FraudTranRepository extends CrudRepository<FraudTran, String> {
    FraudTran findByTrid(String trid);

    @Query(value = "select new FraudTran (ft.checkid, ft.dadd," +
            "       ft.trid, ft.status_check, ft.description)" +
            "       from Transaction as t" +
            "       join FraudTran as ft on t.id = ft.trid" +
            "       where t.clid=:clid and ft.status_check is not null" +
            "       order by t.dadd")
    Iterable<FraudTran> findAllClTr(String clid);
}
