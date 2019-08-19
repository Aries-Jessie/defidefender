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
}
