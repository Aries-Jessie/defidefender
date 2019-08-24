package org.bithacks.defidefender.controller;

import org.bithacks.defidefender.service.CommonService;
import org.bithacks.defidefender.utils.SuperResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/common")
@CrossOrigin
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

    @RequestMapping(value = "/getCredential", method = RequestMethod.POST)
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

    @RequestMapping(value = "/getCPTById", method = RequestMethod.POST)
    public SuperResult getCPTById(@RequestBody String jsonStr) {
        System.out.println("Get Presentation Request");
        SuperResult result = commonService.getCPTById(jsonStr);
        return result;
    }

    @RequestMapping(value = "/initializeAndDeploy", method = RequestMethod.POST)
    public SuperResult initializeAndDeploy() {
        System.out.println("initializeAndDeploy Request");
        commonService.initializeNetwork();
        String address = commonService.deployCertification();
        return SuperResult.ok(address);
    }

    @RequestMapping(value = "/getCredentialsByWeid", method = RequestMethod.POST)
    public SuperResult getCredentialsByWeid(@RequestBody String jsonStr) {
        System.out.println("Get CredentialsByWeid Request");
        return commonService.getCredentialsByWeid(jsonStr);
    }
}
