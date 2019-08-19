package org.bithacks.defidefender.service;

import com.webank.weid.protocol.base.Cpt;
import com.webank.weid.protocol.base.CptBaseInfo;
import com.webank.weid.protocol.base.CredentialPojo;
import com.webank.weid.protocol.base.PresentationE;
import com.webank.weid.protocol.response.CreateWeIdDataResult;
import com.webank.weid.protocol.response.ResponseData;

import java.util.Map;

public interface DIDService {

    ResponseData<CreateWeIdDataResult> createWeid();

    ResponseData<Boolean> registerIssuerType(String issuer, String authorityName);

    ResponseData<CptBaseInfo> registCpt(
            String publisher,
            String privateKey,
            Map<String, Object> claim);

    ResponseData<CredentialPojo> createCredential(
            Integer cptId,
            String issuer,
            String privateKey,
            Map<String, Object> claimData);

    ResponseData<CredentialPojo> createSelectiveCredential(String weid, String claimPolicyJson);

    ResponseData<PresentationE> createPresentation(
            String ownerWeid,
            String policyJson);

    ResponseData<Boolean> verifyPresentation(String weid, String policyJson);

    ResponseData<Boolean> verifyCredential(String weid, String issuer, int type);

    ResponseData<Cpt> getCptById(int cptId);

}
