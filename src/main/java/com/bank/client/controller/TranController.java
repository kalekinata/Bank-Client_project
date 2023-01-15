package com.bank.client.controller;

import com.bank.client.models.*;
import com.bank.client.models.db.Acc;
import com.bank.client.models.db.Client;
import com.bank.client.models.db.FraudTran;
import com.bank.client.models.db.Transaction;
import com.bank.client.models.request.FraudTranReq;
import com.bank.client.models.request.TranCheck;
import com.bank.client.models.request.TransactionList;
import com.bank.client.repository.AccRepository;
import com.bank.client.repository.ClientRepository;
import com.bank.client.repository.FraudTranRepository;
import com.bank.client.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.UUID.randomUUID;

import com.google.gson.Gson;

@Controller
public class TranController {

    @Value("${antifraud}")
    String antifraud;

    @Autowired
    public TransactionRepository transactionRepository;
    @Autowired
    public ClientRepository clientRepository;
    @Autowired
    public AccRepository accRepository;

    @Autowired
    public FraudTranRepository fraudTranRepository;

    @GetMapping("/")
    public String main(Model model){
        return "home";
    }

    @GetMapping("/transaction")
    public String tranList(Model model){
        Iterable<Transaction> transactions = transactionRepository.findAllSort();
        model.addAttribute("transactions",transactions);
        return "tran-list";
    }

    @GetMapping("/transaction/{id}")
    public String transactionDetail(@PathVariable(value = "id") String id, Model model){
        if (!transactionRepository.existsById(id)){
            return "redirect:/";
        }

        Optional<Transaction> transactions = transactionRepository.findById(id);
        ArrayList<Transaction> result = new ArrayList<>();
        transactions.ifPresent(result::add);
        model.addAttribute("tran",result);

        Optional<Client> clSend = clientRepository.findById(
                                        accRepository.findById(
                                            transactionRepository.findById(id).get().getAccid_send()).get().getClid());
        ArrayList<Client> resSend = new ArrayList<>();
        clSend.ifPresent(resSend::add);
        model.addAttribute("clSend",resSend);

        Optional<Client> clRecip = clientRepository.findById(
                                        accRepository.findById(
                                            transactionRepository.findById(id).get().getAccid_recip()).get().getClid());
        ArrayList<Client> resRecip = new ArrayList<>();
        clRecip.ifPresent(resRecip::add);
        model.addAttribute("clRecip",resRecip);

        return "tranDetails";
    }

    @PostMapping("/transaction/{id}")
    public String transactionReq(@PathVariable(value = "id") String id, @RequestParam(value = "button") String status, Model model){
        if(!transactionRepository.existsById(id)){
            return "{\"status\":\"error\"}";
        }
        System.out.println(id);
        System.out.println(status);

        String address = antifraud+"accept";
        ResponseApi responseApi = new ResponseApi();

        FraudTranReq fraudTranReq = new FraudTranReq();
        fraudTranReq.setCheckid(fraudTranRepository.findByTrid(id).getCheckid());
        fraudTranReq.setTrid(id);
        fraudTranReq.setStatus_tr(status);

        int statusFixed = transactionRepository.setFixedTranStatus(id,status);

        System.out.println(statusFixed);

        Gson gson = new Gson();
        String jsonInputString = gson.toJson(fraudTranReq);

        System.out.println(jsonInputString);

        responseApi = responseApi.sendRequest(Method.POST, jsonInputString, address);

        ErrorResponse error = new ErrorResponse(responseApi.getCode(),responseApi.getSb().toString());

        if (error.getCode() != 200 || error.getStatus().indexOf("errdesc") != -1){
            model.addAttribute("error", "Ошибка на сервере. Данные о действиях по транзакции не переданы");
        }

        return "redirect:/transaction";
    }

    @GetMapping("/transfer")
    public String tranForm(Model model){
        return "transfer";
    }

    @PostMapping("/transfer")
    public String tranAdd(@RequestParam String surname, @RequestParam String name,
                          @RequestParam String patronymic, @RequestParam String acc,
                          @RequestParam String region, @RequestParam String surnameR,
                          @RequestParam String nameR, @RequestParam String patronymicR,
                          @RequestParam String accRec, @RequestParam float sum,
                          ModelMap modelMap){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));

        String trid = String.valueOf(randomUUID());
        String tr_dadd = format.format(new Date());
        String clid_S, clid_R;
        String accid_S, accid_R;

        if(clientRepository.findBySurnameAndNameAndPatronymic(name,patronymic,surname) != null){
            clid_S = String.valueOf(clientRepository.findBySurnameAndNameAndPatronymic(name,patronymic,surname).getId());

            System.out.println(clid_S);
            if (accRepository.findByValueAndA(acc, "A") != null){
                String clid_acc_S = accRepository.findByValueAndA(acc, "A").getClid();
                if(!clid_acc_S.equals(clid_S)){
                    System.out.println(clid_acc_S);
                    System.out.println(clid_S);
                    String message = "Счёт отправителя принадлежит другому клиенту 1";
                    System.out.println(message);
                    modelMap.addAttribute("message", message);
                    return "redirect:/";

                }else {
                    accid_S = accRepository.findByClidAndAAndValue(clid_S, "A",acc).getId();
                }
            }
            else {
                accid_S = String.valueOf(randomUUID());
                Acc acc_S = new Acc(accid_S, format.format(new Date()), clid_S, "DEF", acc, "A");
                accRepository.save(acc_S);
            }
        }else{
            if (accRepository.findByValueAndA(acc, "A") != null){
                String message = "Счёт отправителя принадлежит другому клиенту 2";
                System.out.println(message);
                modelMap.addAttribute("message", message);
                return "redirect:/";
            }
            else {
                clid_S = String.valueOf(randomUUID());
                Client client_S = new Client(clid_S, format.format(new Date()),surname,name,patronymic, null);
                clientRepository.save(client_S);

                accid_S = String.valueOf(randomUUID());
                Acc acc_S = new Acc(accid_S, format.format(new Date()), clid_S, "DEF", acc, "A");
                accRepository.save(acc_S);
            }
        }

        if(clientRepository.findBySurnameAndNameAndPatronymic(nameR, patronymicR,surnameR) != null){
            clid_R = clientRepository.findBySurnameAndNameAndPatronymic(nameR,patronymicR,surnameR).getId();
            if (accRepository.findByValueAndA(accRec, "A") != null){
                String clid_acc_R = String.valueOf(accRepository.findByValueAndA(accRec, "A").getClid());
                if(!clid_acc_R.equals(clid_R)){
                    System.out.println(clid_acc_R);
                    System.out.println(clid_R);
                    String message = "Счёт получателя принадлежит другому клиенту 1";
                    System.out.println(message);
                    modelMap.addAttribute("message", message);
                    return "redirect:/";
                }else {
                    accid_R = accRepository.findByClidAndAAndValue(clid_R, "A",accRec).getId();
                }
            }
            else {
                accid_R = String.valueOf(randomUUID());
                Acc acc_Rec = new Acc(accid_R, format.format(new Date()), clid_R, "DEF", accRec, "A");
                accRepository.save(acc_Rec);
            }
        }else{
            if (accRepository.findByValueAndA(accRec, "A") != null){
                String message = "Счёт получателя принадлежит другому клиенту 2";
                System.out.println(message);
                modelMap.addAttribute("message", message);
                return "redirect:/";
            }
            else {
                clid_R = String.valueOf(randomUUID());
                Client client_R = new Client(clid_R, format.format(new Date()),surnameR, nameR, patronymicR, null);
                clientRepository.save(client_R);

                accid_R = String.valueOf(randomUUID());
                Acc acc_Rec = new Acc(accid_R, format.format(new Date()), clid_R, "DEF", accRec, "A");
                accRepository.save(acc_Rec);
            }
        }

        Transaction transaction = new Transaction(trid, tr_dadd, clid_S, accid_S, accid_R, "transfer", sum, (float) (sum * 0.01),  region, "create");
        transactionRepository.save(transaction);

        TransactionList list = new TransactionList();
        list.setId(trid);
        list.setDadd(tr_dadd);
        list.setRegion(region);
        list.setSum(sum);
        list.setCommission((float) (sum * 0.01));

        TransactionList.ClientS clientS = new TransactionList.ClientS();
        clientS.setClid(clid_S);
        clientS.setSurname(surname);
        clientS.setName(name);
        clientS.setPatronymic(patronymic);

        TransactionList.ClientS.AccS acc_S = new TransactionList.ClientS.AccS();
        acc_S.setAccid(accid_S);
        acc_S.setBic("98765473629");
        clientS.setAccS(acc_S);

        list.setClientS(clientS);

        TransactionList.ClientR clientR = new TransactionList.ClientR();
        clientR.setClid(clid_R);
        clientR.setSurname(surnameR);
        clientR.setName(nameR);
        clientR.setPatronymic(patronymicR);

        TransactionList.ClientR.AccR acc_R = new TransactionList.ClientR.AccR();
        acc_R.setAccid(accid_R);
        acc_R.setBic("6565473629");
        clientR.setAccR(acc_R);

        list.setClientR(clientR);

        Gson gson = new Gson();
        String jsonInputString = gson.toJson(list);

        System.out.println(jsonInputString);

        String address = antifraud+"check";

        ResponseApi responseApi = new ResponseApi();

        responseApi = responseApi.sendRequest(Method.POST, jsonInputString, address);

        ErrorResponse error = new ErrorResponse(responseApi.getCode(),responseApi.getSb().toString());

        if (error.getCode() != 200){
            System.out.println(error.getCode()+"\n"+ error.getStatus()+" ERROR");
            error.setStatus("Заявка не отправлена на проверку");
            modelMap.addAttribute("error", error);

            return "redirect:/";
        }
        else {
            TranCheck tranCheck = new Gson().fromJson(responseApi.getSb().toString(), TranCheck.class);

            int statusFixed = transactionRepository.setFixedTranStatus(tranCheck.getTrid(), tranCheck.getStatus_tr());
            System.out.println(statusFixed);

            return "redirect:/";
        }
    }

    @GetMapping("/transaction/statistics/{id}")
    public String statisticsClient(@PathVariable(value = "id") String id, Model model){
        if (!clientRepository.existsById(id)){
            return "redirect:/";
        }
        Iterable<Transaction> transactions = transactionRepository.findAllClTr(id);

        String jsTrans = new Gson().toJson(transactions);
        System.out.println(jsTrans);

        model.addAttribute("transactions",transactions);

        Iterable<FraudTran> fraudTrans = fraudTranRepository.findAllClTr(id);
        String jsFraud = new Gson().toJson(fraudTrans);

        System.out.println(jsFraud);
        model.addAttribute("fraud",fraudTrans);
        return "statistics";
    }

}
