package org.bithacks.defidefender.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.webank.weid.protocol.base.CredentialPojo;
import com.webank.weid.protocol.response.ResponseData;
import org.bithacks.defidefender.service.DIDService;
import org.bithacks.defidefender.service.GovernmentService;
import org.bithacks.defidefender.utils.CommonUtils;
import org.bithacks.defidefender.utils.ConstantFields;
import org.bithacks.defidefender.utils.PrivateKeyUtil;
import org.bithacks.defidefender.utils.SuperResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class GovernmentServiceImpl implements GovernmentService {

    @Autowired
    private DIDService weIdService;

    @Override
    public SuperResult createCredential(String jsonStr) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            int cptId = jsonObject.getInteger(ConstantFields.USER_CREDENTIAL_CPTID);
            String issuer = jsonObject.getString(ConstantFields.USER_CREDENTIAL_ISSUER);
            String privateKey = PrivateKeyUtil.getPrivateKeyByWeId(ConstantFields.KEY_DIR, issuer);
            JSONObject claimDataObject = (JSONObject) jsonObject.get(ConstantFields.USER_CREDENTIAL_CLAIMDATA);
            HashMap<String, Object> claimData = CommonUtils.convertJsonToMap(claimDataObject.toJSONString());
            ResponseData<CredentialPojo> response = weIdService.createCredential(cptId, issuer, privateKey, claimData);
            return SuperResult.ok(response);
        } catch (Exception e) {
            return SuperResult.fail();
        }
    }
}
