package org.bithacks.defidefender.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.webank.weid.protocol.base.CptBaseInfo;
import com.webank.weid.protocol.base.CredentialPojo;
import com.webank.weid.protocol.base.PresentationE;
import com.webank.weid.protocol.response.ResponseData;
import org.bithacks.defidefender.service.CommonService;
import org.bithacks.defidefender.service.DIDService;
import org.bithacks.defidefender.utils.CommonUtils;
import org.bithacks.defidefender.utils.ConstantFields;
import org.bithacks.defidefender.utils.PrivateKeyUtil;
import org.bithacks.defidefender.utils.SuperResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private DIDService weIdService;

    @Override
    public SuperResult createCPT(String jsonStr) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            String publisher = jsonObject.getString(ConstantFields.USER_CPT_PUBLISHER);
            String privateKey = PrivateKeyUtil.getPrivateKeyByWeId(ConstantFields.KEY_DIR, publisher);
            JSONObject cptSchemaNode = (JSONObject) jsonObject.get(ConstantFields.USER_CPT_CPTSCHEMA);
            HashMap<String, Object> cptSchema = CommonUtils.convertJsonToMap(cptSchemaNode.toJSONString());
            ResponseData<CptBaseInfo> response = weIdService.registCpt(publisher, privateKey, cptSchema);
            return SuperResult.ok(response);
        } catch (Exception e) {
            return SuperResult.fail();
        }
    }

    @Override
    public SuperResult registerIssueType(String jsonStr) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            String issuer = jsonObject.getString(ConstantFields.COMMON_ISSUE_ISSUER);
            String authorityName = jsonObject.getString(ConstantFields.COMMON_ISSUE_AUTHORITYNAME);
            ResponseData<Boolean> responseData = weIdService.registerIssuerType(issuer, authorityName);
            return SuperResult.ok(responseData);
        } catch (Exception e) {
            return SuperResult.fail();
        }
    }

    @Override
    public SuperResult getCredential(String jsonStr) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            String weid = jsonObject.getString("weid");
            int type = jsonObject.getInteger("type");
            CredentialPojo credentialPojo = CommonUtils.readObjectFromFile(weid, type);
            String credential = credentialPojo.toJson();
            return SuperResult.ok(credential);
        } catch (Exception e) {
            return SuperResult.fail();
        }
    }

    @Override
    public SuperResult getPresentation(String jsonStr) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            String weid = jsonObject.getString("weid");
            PresentationE presentationE = CommonUtils.readPresentationFromFile(weid);
            String presentation = presentationE.toJson();
            return SuperResult.ok(presentation);
        } catch (Exception e) {
            return SuperResult.fail();
        }
    }
}
