package org.bithacks.defidefender.utils;

import org.springframework.stereotype.Component;

@Component
public class ConstantFields {

    public static final String KEY_DIR = PropertiesUtils.getProperty("weid.keys.dir");
    public static final String CREDENTIAL_DIR = PropertiesUtils.getProperty("weid.credentials.dir");
    public static final String PRESENTATION_DIR = PropertiesUtils.getProperty("weid.presentations.dir");

    public static final int USER_STAUTS_WAITING = 0;
    public static final int USER_STAUTS_SUCCESS = 1;
    public static final int USER_STAUTS_FAIL = 2;


    public static final String CONTRACT_CERTIFICATION = "Certification";

    public static final String USER_CPT_PUBLISHER = "publisher";
    public static final String USER_CPT_CPTSCHEMA = "cptSchema";
    public static final String USER_CREDENTIAL_CPTID = "cptId";
    public static final String USER_CREDENTIAL_ISSUER = "issuer";
    public static final String USER_CREDENTIAL_CLAIMDATA = "claimData";
    public static final String USER_SELECTIVECREDENTIAL_WEID = "weid";
    public static final String USER_SELECTIVECREDENTIAL_CLAIMPOLICYJSON = "claimPolicyJson";
    public static final String USER_PRESENTATION_OWNERWEID = "ownerWeId";
    public static final String USER_PRESENTATION_POLICYJSON = "policyJson";
    public static final String USER_VERIFYWEID_WEID = "weid";
    public static final String USER_VERIFYWEID_NAME = "name";
    public static final String USER_VERIFYWEID_GENDER = "gender";
    public static final String USER_VERIFYWEID_BIRTHDAY = "birthday";
    public static final String USER_VERIFYWEID_ADDRESS = "address";
    public static final String USER_VERIFYWEID_IDENTITYNUMBER = "identityNumber";
    public static final String USER_VERIFYWEID_PHONENUMBER = "phoneNumber";

    public static final String COMPANY_VERIFY_PRESENTATION_WEID = "weid";
    public static final String COMPANY_VERIFY_PRESENTATION_POLICYJSON = "policyJson";


    public static final String COMMON_ISSUE_ISSUER = "issuer";
    public static final String COMMON_ISSUE_AUTHORITYNAME = "authorityName";

    // 0 - 待确认 1 - 已确认，未还款 2 - 已确认，已还款 3 - 已确认，超时未还 4 - 已拒绝 5 - 已加入黑名单
    public static final int LOAN_STATUS_WAITING = 0;
    public static final int LOAN_STATUS_CONFIRM_NOTRETURN = 1;
    public static final int LOAN_STATUS_CONFIRM_RETURN = 2;
    public static final int LOAN_STATUS_CONFIRM_TIMEOUT = 3;
    public static final int LOAN_STATUS_REJECT = 4;
    public static final int LOAN_STATUS_ADDBLACKLIST = 5;

}
