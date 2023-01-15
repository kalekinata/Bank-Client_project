package com.bank.client.controller;

import com.bank.client.models.db.FraudTran;
import com.bank.client.models.db.Transaction;
import com.bank.client.repository.AccRepository;
import com.bank.client.repository.FraudTranRepository;
import com.bank.client.repository.TransactionRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RequestController {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    AccRepository accRepository;
    @Autowired
    FraudTranRepository fraudTranRepository;

    @GetMapping("/info_tran")
    public String infoTrans(@RequestParam(value = "clid") String clid, Model model){
        if(clid == null){
            return "{\"errdesc\":\"Не передан обязательный параметр\"}";
        }

        System.out.println(clid);
        List<Transaction> list = transactionRepository.findByClid(clid);

        Gson gson = new Gson();
        String js = gson.toJson(list);
        System.out.println(js);

        return js;
    }

    @PostMapping("/result_check")
    public String resultCheck(@RequestBody FraudTran fT){
        if(fT.getCheckid() == null || fT.getTrid() == null || fT.getStatus_check() == null || fT.getDescription() == null || fT.getDadd() == null){
            return "{\"errdesc\":\"Не переданы обязательные параметры\"}";
        }

        if(!transactionRepository.existsById(fT.getTrid())){
            return "{\"errdesc\":\"Транзакция отсутствует в системе\"}";
        }

        String json = new Gson().toJson(fT);

        FraudTran fraudTran = new FraudTran(fT.getCheckid(), fT.getDadd(), fT.getTrid(), fT.getStatus_check(), fT.getDescription());
        fraudTranRepository.save(fraudTran);

        String jsonStr = new Gson().toJson(fraudTran);

        int statusCheckFixed = transactionRepository.setFixedTranCheck(fT.getTrid(), fT.getStatus_check());

        System.out.println(json);
        System.out.println(jsonStr);
        System.out.println(statusCheckFixed);

        return "{\"status\":\"succes\"}";
    }
}