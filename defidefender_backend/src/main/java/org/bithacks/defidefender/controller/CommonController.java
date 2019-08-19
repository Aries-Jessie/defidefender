package org.bithacks.defidefender.controller;

import org.bithacks.defidefender.service.CommonService;
import org.bithacks.defidefender.utils.SuperResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/common")
public class CommonController {

    @Autowired
    private CommonService commonService;

    @RequestMapping(value = "/createCPT", method = RequestMethod.POST)
    public SuperResult createCPT(@RequestBody String jsonStr) {
        System.out.println("Create CPT Request");
        SuperResult result = commonService.createCPT(jsonStr);
        return result;
    }

    @RequestMapping(value = "/registerIssuer", method = RequestMethod.POST)
    public SuperResult registerIssuer(@RequestBody String jsonStr) {
        System.out.println("Register Issuer Request");
        SuperResult result = commonService.registerIssueType(jsonStr);
        return result;
    }

    @RequestMapping(value = "/getCredential", method = RequestMethod.GET)
    public SuperResult getCredential(@RequestBody String jsonStr) {
        System.out.println("Get Credential Request");
        SuperResult result = commonService.getCredential(jsonStr);
        return result;
    }

    @RequestMapping(value = "/getPresentation", method = RequestMethod.GET)
    public SuperResult getPresentation(@RequestBody String jsonStr) {
        System.out.println("Get Presentation Request");
        SuperResult result = commonService.getPresentation(jsonStr);
        return result;
    }
}
