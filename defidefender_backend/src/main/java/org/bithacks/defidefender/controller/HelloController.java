package org.bithacks.defidefender.controller;

import org.bithacks.defidefender.dao.ContractRepository;
import org.bithacks.defidefender.model.Po.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloController {

    @Autowired
    ContractRepository contractRepository;

    @RequestMapping(value = "/createContract")
    public String createContract() {
        contractRepository.save(new Contract("weid", "111111111"));
        List<Contract> contracts = contractRepository.findAll();
        for (Contract contract : contracts) {
            System.out.println(contract.getName() + ":" + contract.getAddress());
        }
        return "Create Success";
    }

    @RequestMapping(value = "/")
    public String hello() {
        return "Success";
    }

}
