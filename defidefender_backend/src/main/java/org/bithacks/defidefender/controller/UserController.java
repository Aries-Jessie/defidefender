package org.bithacks.defidefender.controller;

import com.webank.weid.protocol.response.CreateWeIdDataResult;
import com.webank.weid.protocol.response.ResponseData;
import org.bithacks.defidefender.service.UserService;
import org.bithacks.defidefender.utils.SuperResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/createWeId", method = RequestMethod.POST)
    public SuperResult createWeId() {
        System.out.println("Create WeId Request");
        ResponseData<CreateWeIdDataResult> response = userService.createWeId();
        return SuperResult.ok(response);
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

}
