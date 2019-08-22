package org.bithacks.defidefender.controller;

import org.bithacks.defidefender.service.UserService;
import org.bithacks.defidefender.utils.SuperResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/createWeId", method = RequestMethod.POST)
    public SuperResult createWeId(@RequestBody String jsonStr) {
        System.out.println("Create WeId Request");
        SuperResult result = userService.createWeId(jsonStr);
        return result;
    }

    @RequestMapping(value = "/createSelectiveCredential", method = RequestMethod.POST)
    public SuperResult createSelectiveCredential(@RequestBody String jsonStr) {
        System.out.println("Create SelectiveCredential Request");
        SuperResult result = userService.createSelectiveCredential(jsonStr);
        return result;
    }

    @RequestMapping(value = "/createPresentation", method = RequestMethod.POST)
    public SuperResult createPresentation(@RequestBody String jsonStr) {
        System.out.println("Create Presentation Request");
        SuperResult result = userService.createPresentation(jsonStr);
        return result;
    }

    @RequestMapping(value = "/requestVerifyWeId", method = RequestMethod.POST)
    public SuperResult requestVerifyWeId(@RequestBody String jsonStr) {
        System.out.println("Request VerifyWeId Request");
        SuperResult result = userService.requestVerifyWeId(jsonStr);
        return result;
    }

    @RequestMapping(value = "/getCompanies", method = RequestMethod.GET)
    public SuperResult getCompanies() {
        System.out.println("Get Companies Request");
        SuperResult result = userService.getCompanies();
        return result;
    }

    @RequestMapping(value = "/requestLoan", method = RequestMethod.POST)
    public SuperResult requestLoan(@RequestBody String jsonStr) {
        System.out.println("Request Loan Request");
        SuperResult result = userService.requestLoan(jsonStr);
        return result;
    }

    @RequestMapping(value = "/listLoanRequests", method = RequestMethod.POST)
    public SuperResult listLoanRequests(@RequestBody String jsonStr) {
        System.out.println("List Loan Request");
        SuperResult result = userService.listLoanRequests(jsonStr);
        return result;
    }

    @RequestMapping(value = "/listLoanRecords", method = RequestMethod.POST)
    public SuperResult listLoanRecords(@RequestBody String jsonStr) {
        System.out.println("List Loan Records Request");
        SuperResult result = userService.listLoanRecords(jsonStr);
        return result;
    }

    @RequestMapping(value = "/returnLoan", method = RequestMethod.POST)
    public SuperResult returnLoan(@RequestBody String jsonStr) {
        System.out.println("Return Loan Request");
        SuperResult result = userService.repayLoan(jsonStr);
        return result;
    }

    @RequestMapping(value = "/getCanLoanAmount", method = RequestMethod.POST)
    public SuperResult getCanLoanAmount(@RequestBody String jsonStr) {
        System.out.println("getCanLoanAmount Request");
        SuperResult result = userService.getCanLoanAmount(jsonStr);
        return result;
    }

    @RequestMapping(value = "/getDailyRate", method = RequestMethod.POST)
    public SuperResult getDailyRate(@RequestBody String jsonStr) {
        System.out.println("getDailyRate Request");
        SuperResult result = userService.getDailyRate(jsonStr);
        return result;
    }

    @RequestMapping(value = "/getUserStatus", method = RequestMethod.POST)
    public SuperResult getUserStatus(@RequestBody String jsonStr) {
        System.out.println("getUserStatus Request");
        SuperResult result = userService.getUserStatus(jsonStr);
        return result;
    }

}
