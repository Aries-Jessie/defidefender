package org.bithacks.defidefender.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.webank.weid.protocol.response.ResponseData;
import org.bithacks.defidefender.service.CompanyService;
import org.bithacks.defidefender.service.DIDService;
import org.bithacks.defidefender.utils.ConstantFields;
import org.bithacks.defidefender.utils.SuperResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private DIDService weIdService;

    @Override
    public SuperResult verifyPresentation(String jsonStr) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            String weid = jsonObject.getString(ConstantFields.COMPANY_VERIFY_PRESENTATION_WEID);
            JSONObject policyJsonObject = (JSONObject) jsonObject.get(ConstantFields.COMPANY_VERIFY_PRESENTATION_POLICYJSON);
            ResponseData<Boolean> response = weIdService.verifyPresentation(weid, policyJsonObject.toJSONString());
            return SuperResult.ok(response);
        } catch (Exception e) {
            return SuperResult.fail();
        }
    }

    @Override
    public SuperResult verifyCredential(String jsonStr) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            String weid = jsonObject.getString("weid");
            String issuer = jsonObject.getString("issuer");
            int type = jsonObject.getInteger("type");
            ResponseData<Boolean> response = weIdService.verifyCredential(weid, issuer, type);
            return SuperResult.ok(response);
        } catch (Exception e) {
            return SuperResult.fail();
        }
    }
}
