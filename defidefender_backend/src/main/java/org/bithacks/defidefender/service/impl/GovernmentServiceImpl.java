package org.bithacks.defidefender.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.webank.weid.protocol.base.CredentialPojo;
import com.webank.weid.protocol.response.ResponseData;
import org.bithacks.defidefender.dao.CredentialRepository;
import org.bithacks.defidefender.dao.IssuedCredentialRepository;
import org.bithacks.defidefender.dao.RelationRepository;
import org.bithacks.defidefender.dao.UserRepository;
import org.bithacks.defidefender.model.Po.Credential;
import org.bithacks.defidefender.model.Po.IssuedCredential;
import org.bithacks.defidefender.model.Po.User;
import org.bithacks.defidefender.service.DIDService;
import org.bithacks.defidefender.service.GovernmentService;
import org.bithacks.defidefender.utils.CommonUtils;
import org.bithacks.defidefender.utils.ConstantFields;
import org.bithacks.defidefender.utils.PrivateKeyUtil;
import org.bithacks.defidefender.utils.SuperResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class GovernmentServiceImpl implements GovernmentService {

    @Autowired
    private DIDService weIdService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IssuedCredentialRepository issuedCredentialRepository;

    @Autowired
    private RelationRepository relationRepository;

    @Autowired
    private CredentialRepository credentialRepository;

    @Override
    public SuperResult createCredential(String jsonStr) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            int cptId = jsonObject.getInteger(ConstantFields.USER_CREDENTIAL_CPTID);
            String issuer = jsonObject.getString(ConstantFields.USER_CREDENTIAL_ISSUER);
            String privateKey = relationRepository.findRelationsByWeid(issuer).get(0).getPrivateKey();
//            String privateKey = PrivateKeyUtil.getPrivateKeyByWeId(ConstantFields.KEY_DIR, issuer);
            JSONObject claimDataObject = (JSONObject) jsonObject.get(ConstantFields.USER_CREDENTIAL_CLAIMDATA);
            HashMap<String, Object> claimData = CommonUtils.convertJsonToMap(claimDataObject.toJSONString());
            ResponseData<CredentialPojo> response = weIdService.createCredential(cptId, issuer, privateKey, claimData);
            return SuperResult.ok(response);
        } catch (Exception e) {
            return SuperResult.fail();
        }
    }

    @Override
    public SuperResult checkUser(String jsonStr) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            String weid = jsonObject.getString("weid");
            int type = jsonObject.getInteger("type");
            User user = userRepository.findOne(weid);
            int updatedStatus = type == 0 ? ConstantFields.USER_STAUTS_SUCCESS : ConstantFields.USER_STAUTS_FAIL;
            user.setStatus(updatedStatus);
            userRepository.save(user);
            return SuperResult.ok(user);
        } catch (Exception e) {
            System.out.println(e);
            return SuperResult.fail();
        }
    }

    @Override
    public SuperResult listToBeCheckedUsers() {
        List<User> users = userRepository.findUsersByStatus(ConstantFields.USER_STAUTS_WAITING);
        return SuperResult.ok(users);
    }

    @Override
    public SuperResult listVerifiedUsers() {
        List<User> users = userRepository.findUsersByStatus(ConstantFields.USER_STAUTS_SUCCESS);
        return SuperResult.ok(users);
    }

    @Override
    public SuperResult listIssuedCredentials() {
        List<Credential> credentials = credentialRepository.findCredentialsByType(0);
        return SuperResult.ok(credentials);
    }
}
