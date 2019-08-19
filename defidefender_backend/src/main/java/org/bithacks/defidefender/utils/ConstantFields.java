package org.bithacks.defidefender.utils;

public class ConstantFields {

    public static final String KEY_DIR = PropertiesUtils.getProperty("weid.keys.dir");
    public static final String CREDENTIAL_DIR = PropertiesUtils.getProperty("weid.credentials.dir");


    public static final String USER_CPT_PUBLISHER = "publisher";
    public static final String USER_CPT_CPTSCHEMA = "cptSchema";
    public static final String USER_CREDENTIAL_CPTID = "cptId";
    public static final String USER_CREDENTIAL_ISSUER = "issuer";
    public static final String USER_CREDENTIAL_CLAIMDATA = "claimData";
    public static final String USER_SELECTIVECREDENTIAL_WEID = "weid";
    public static final String USER_SELECTIVECREDENTIAL_CLAIMPOLICYJSON = "claimPolicyJson";
    public static final String USER_PRESENTATION_OWNERWEID = "ownerWeId";
    public static final String USER_PRESENTATION_POLICYJSON = "policyJson";
    public static final String COMPANY_VERIFY_PRESENTATION_WEID = "weid";
    public static final String COMPANY_VERIFY_PRESENTATION_POLICYJSON = "policyJson";


    public static final String COMMON_ISSUE_ISSUER = "issuer";
    public static final String COMMON_ISSUE_AUTHORITYNAME = "authorityName";

}
