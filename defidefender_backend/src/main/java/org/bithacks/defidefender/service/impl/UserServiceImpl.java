package org.bithacks.defidefender.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.webank.weid.constant.ErrorCode;
import com.webank.weid.protocol.base.CredentialPojo;
import com.webank.weid.protocol.base.PresentationE;
import com.webank.weid.protocol.response.CreateWeIdDataResult;
import com.webank.weid.protocol.response.ResponseData;
import org.bithacks.defidefender.dao.LoanRecordRepository;
import org.bithacks.defidefender.dao.RelationRepository;
import org.bithacks.defidefender.dao.UserRepository;
import org.bithacks.defidefender.model.Po.LoanRecord;
import org.bithacks.defidefender.model.Po.Relation;
import org.bithacks.defidefender.model.Po.User;
import org.bithacks.defidefender.service.DIDService;
import org.bithacks.defidefender.service.UserService;
import org.bithacks.defidefender.utils.ConstantFields;
import org.bithacks.defidefender.utils.PrivateKeyUtil;
import org.bithacks.defidefender.utils.SuperResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private DIDService weIdService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RelationRepository relationRepository;

    @Autowired
    private LoanRecordRepository loanRecordRepository;

    @Override
    public SuperResult createWeId(String jsonStr) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            String name = jsonObject.getString("name");
            int type = jsonObject.getInteger("type"); // 0-用户 1-政府 2-机构
            ResponseData<CreateWeIdDataResult> response = weIdService.createWeid();
            if (response.getErrorCode().intValue() == ErrorCode.SUCCESS.getCode()) {
                relationRepository.save(new Relation(name, response.getResult().getWeId(), type));
                PrivateKeyUtil.savePrivateKey(
                        ConstantFields.KEY_DIR,
                        response.getResult().getWeId(),
                        response.getResult().getUserWeIdPrivateKey().getPrivateKey()
                );
            }
            response.getResult().setUserWeIdPrivateKey(null);
            return SuperResult.ok(response);
        } catch (Exception e) {
            return SuperResult.fail();
        }
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

    @Override
    public SuperResult requestVerifyWeId(String jsonStr) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            String weid = jsonObject.getString(ConstantFields.USER_VERIFYWEID_WEID);
            String name = jsonObject.getString(ConstantFields.USER_VERIFYWEID_NAME);
            String gender = jsonObject.getString(ConstantFields.USER_VERIFYWEID_GENDER);
            String birthday = jsonObject.getString(ConstantFields.USER_VERIFYWEID_BIRTHDAY);
            String address = jsonObject.getString(ConstantFields.USER_VERIFYWEID_ADDRESS);
            String identityNumber = jsonObject.getString(ConstantFields.USER_VERIFYWEID_IDENTITYNUMBER);
            String phoneNumber = jsonObject.getString(ConstantFields.USER_VERIFYWEID_PHONENUMBER);
            User user = userRepository.save(
                    new User(weid,
                            name,
                            gender,
                            birthday,
                            address,
                            identityNumber,
                            phoneNumber,
                            ConstantFields.USER_STAUTS_WAITING));
            return SuperResult.ok(user);
        } catch (Exception e) {
            System.out.println(e);
            return SuperResult.fail();
        }
    }

    @Override
    public SuperResult getCompanies() {
        List<Relation> relations = relationRepository.findRelationsByType(2);
        return SuperResult.ok(relations);
    }

    @Override
    public SuperResult requestLoan(String jsonStr) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            String companyName = jsonObject.getString("companyName");
            double amount = jsonObject.getDoubleValue("amount");
            String expiredDate = jsonObject.getString("expiredDate");
            String credentialOwner = jsonObject.getString("credentialOwner");
            LoanRecord record = loanRecordRepository.save(new LoanRecord(credentialOwner, companyName, amount, expiredDate, credentialOwner, ConstantFields.LOAN_STATUS_WAITING));
            return SuperResult.ok(record);
        } catch (Exception e) {
            return SuperResult.fail();
        }
    }

    @Override
    public SuperResult listLoanRequests(String jsonStr) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            String weid = jsonObject.getString("weid");
            List<LoanRecord> records = loanRecordRepository.findLoanRecordsByWeidAndStatus(weid, ConstantFields.LOAN_STATUS_WAITING);
            return SuperResult.ok(records);
        } catch (Exception e) {
            return SuperResult.fail();
        }
    }

    @Override
    public SuperResult listLoanRecords(String jsonStr) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            String weid = jsonObject.getString("weid");
            List<LoanRecord> records = loanRecordRepository.findLoanRecordsByWeidAndStatusGreaterThanEqual(weid, 1);
            return SuperResult.ok(records);
        } catch (Exception e) {
            return SuperResult.fail();
        }
    }

    @Override
    public SuperResult returnLoan(String jsonStr) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            int id = jsonObject.getIntValue("id");
            LoanRecord record = loanRecordRepository.findOne(id);
            record.setStatus(ConstantFields.LOAN_STATUS_CONFIRM_RETURN);
            loanRecordRepository.save(record);
            return SuperResult.ok(record);
        } catch (Exception e) {
            return SuperResult.fail();
        }
    }

}
