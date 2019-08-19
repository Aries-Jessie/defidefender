package org.bithacks.defidefender.controller;

import org.bithacks.defidefender.service.CompanyService;
import org.bithacks.defidefender.utils.SuperResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @RequestMapping(value = "/verifyPresentation", method = RequestMethod.POST)
    public SuperResult verifyPresentation(@RequestBody String jsonStr) {
        System.out.println("Verify Presentation Request");
        SuperResult result = companyService.verifyPresentation(jsonStr);
        return result;
    }

    @RequestMapping(value = "/verifyCredential", method = RequestMethod.POST)
    public SuperResult verifyCredential(@RequestBody String jsonStr) {
        System.out.println("Verify Credential Request");
        SuperResult result = companyService.verifyCredential(jsonStr);
        return result;
    }
}
