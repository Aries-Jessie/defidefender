package org.bithacks.defidefender.controller;

import org.bithacks.defidefender.service.GovernmentService;
import org.bithacks.defidefender.utils.SuperResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/government")
public class GovernmentController {

    @Autowired
    private GovernmentService governmentService;

    @RequestMapping(value = "/createCredential", method = RequestMethod.POST)
    public SuperResult createCredential(@RequestBody String jsonStr) {
        System.out.println("Create Credential Request");
        SuperResult result = governmentService.createCredential(jsonStr);
        return result;
    }

    @RequestMapping(value = "/checkUser", method = RequestMethod.POST)
    public SuperResult checkUser(@RequestBody String jsonStr) {
        System.out.println("Check User Request");
        SuperResult result = governmentService.checkUser(jsonStr);
        return result;
    }

    @RequestMapping(value = "/listVerifiedUsers", method = RequestMethod.GET)
    public SuperResult listVerifiedUsers() {
        System.out.println("List VerifiedUsers Request");
        SuperResult result = governmentService.listVerifiedUsers();
        return result;
    }

    @RequestMapping(value = "/listToBeCheckedUsers", method = RequestMethod.GET)
    public SuperResult listToBeCheckedUsers() {
        System.out.println("List ToBeCheckedUsers Request");
        SuperResult result = governmentService.listToBeCheckedUsers();
        return result;
    }

    @RequestMapping(value = "/listIssuedCredentials", method = RequestMethod.GET)
    public SuperResult listIssuedCredentials() {
        System.out.println("List IssuedCredentials Request");
        SuperResult result = governmentService.listIssuedCredentials();
        return result;
    }
}
