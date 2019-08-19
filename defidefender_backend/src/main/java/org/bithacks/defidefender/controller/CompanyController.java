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

    @RequestMapping(value = "/addWeidToBlacklist", method = RequestMethod.POST)
    public SuperResult addWeidToBlacklist(@RequestBody String jsonStr) {
        System.out.println("Add WeidToBlacklist Request");
        SuperResult result = companyService.addWeidToBlacklist(jsonStr);
        return result;
    }

    @RequestMapping(value = "/listBlacklist", method = RequestMethod.GET)
    public SuperResult listBlacklist() {
        System.out.println("List blacklist Request");
        SuperResult result = companyService.listBlacklist();
        return result;
    }

    @RequestMapping(value = "/listBlacklistByWeid", method = RequestMethod.POST)
    public SuperResult listBlacklistByWeid(@RequestBody String jsonStr) {
        System.out.println("List blacklist by weid Request");
        SuperResult result = companyService.listBlacklistByWeid(jsonStr);
        return result;
    }

    @RequestMapping(value = "/listLoanRequestRecords", method = RequestMethod.POST)
    public SuperResult listLoanRequestRecords(@RequestBody String jsonStr) {
        System.out.println("List LoanRequest Records by companyName Request");
        companyService.checkLoanStatus();
        SuperResult result = companyService.listLoanRequestRecords(jsonStr);
        return result;
    }

    @RequestMapping(value = "/handleLoanRequest", method = RequestMethod.POST)
    public SuperResult handleLoanRequest(@RequestBody String jsonStr) {
        System.out.println("Handle Loan Request");
        SuperResult result = companyService.handleLoanRequest(jsonStr);
        return result;
    }

    @RequestMapping(value = "/addToBlacklist", method = RequestMethod.POST)
    public SuperResult addToBlacklist(@RequestBody String jsonStr) {
        System.out.println("Add to Blacklist Request");
        SuperResult result = companyService.addToBlacklist(jsonStr);
        return result;
    }

    @RequestMapping(value = "/listLoanRecords", method = RequestMethod.POST)
    public SuperResult listLoanRecords(@RequestBody String jsonStr) {
        System.out.println("List Loan Records by companyName Request");
        companyService.checkLoanStatus();
        SuperResult result = companyService.listLoanRecords(jsonStr);
        return result;
    }
}
