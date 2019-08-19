package org.bithacks.defidefender.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.webank.weid.constant.ErrorCode;
import com.webank.weid.protocol.base.CredentialPojo;
import com.webank.weid.protocol.base.PresentationE;
import com.webank.weid.protocol.response.CreateWeIdDataResult;
import com.webank.weid.protocol.response.ResponseData;
import org.bithacks.defidefender.service.DIDService;
import org.bithacks.defidefender.service.UserService;
import org.bithacks.defidefender.utils.ConstantFields;
import org.bithacks.defidefender.utils.PrivateKeyUtil;
import org.bithacks.defidefender.utils.SuperResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private DIDService weIdService;

    @Override
    public ResponseData<CreateWeIdDataResult> createWeId() {
        ResponseData<CreateWeIdDataResult> response = weIdService.createWeid();
        if (response.getErrorCode().intValue() == ErrorCode.SUCCESS.getCode()) {
            PrivateKeyUtil.savePrivateKey(
                    ConstantFields.KEY_DIR,
                    response.getResult().getWeId(),
                    response.getResult().getUserWeIdPrivateKey().getPrivateKey()
            );
        }
        response.getResult().setUserWeIdPrivateKey(null);
        return response;
    }

    @Override
    public SuperResult createSelectiveCredential(String jsonStr) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            String weid = jsonObject.getString(ConstantFields.USER_SELECTIVECREDENTIAL_WEID);
            JSONObject claimPolicyObject = (JSONObject) jsonObject.get(ConstantFields.USER_SELECTIVECREDENTIAL_CLAIMPOLICYJSON);
            ResponseData<CredentialPojo> response = weIdService.createSelectiveCredential(weid, claimPolicyObject.toJSONString());
            return SuperResult.ok(response);
        } catch (Exception e) {
            return SuperResult.fail();
        }
    }

    @Override
    public SuperResult createPresentation(String jsonStr) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            String ownerWeId = jsonObject.getString(ConstantFields.USER_PRESENTATION_OWNERWEID);
            JSONObject policyJsonObject = (JSONObject) jsonObject.get(ConstantFields.USER_PRESENTATION_POLICYJSON);
            ResponseData<PresentationE> response = weIdService.createPresentation(ownerWeId, policyJsonObject.toJSONString());
            return SuperResult.ok(response);
        } catch (Exception e) {
            return SuperResult.fail();
        }
    }

}
