package org.bithacks.defidefender.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.webank.weid.constant.ErrorCode;
import com.webank.weid.protocol.base.CredentialPojo;
import com.webank.weid.protocol.base.PresentationE;
import com.webank.weid.protocol.response.CreateWeIdDataResult;
import com.webank.weid.protocol.response.ResponseData;
import org.bithacks.defidefender.dao.LoanRecordRepository;
import org.bithacks.defidefender.dao.RelationRepository;
import org.bithacks.defidefender.dao.UserLoanRepository;
import org.bithacks.defidefender.dao.UserRepository;
import org.bithacks.defidefender.model.Po.LoanRecord;
import org.bithacks.defidefender.model.Po.Relation;
import org.bithacks.defidefender.model.Po.User;
import org.bithacks.defidefender.model.Po.UserLoan;
import org.bithacks.defidefender.service.DIDService;
import org.bithacks.defidefender.service.UserService;
import org.bithacks.defidefender.utils.CommonUtils;
import org.bithacks.defidefender.utils.ConstantFields;
import org.bithacks.defidefender.utils.PrivateKeyUtil;
import org.bithacks.defidefender.utils.SuperResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
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

    @Autowired
    private UserLoanRepository userLoanRepository;

    @Override
    public SuperResult createWeId(String jsonStr) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            String name = jsonObject.getString("name");
            int type = jsonObject.getInteger("type"); // 0-用户 1-政府 2-机构
            ResponseData<CreateWeIdDataResult> response = weIdService.createWeid();
            if (response.getErrorCode().intValue() == ErrorCode.SUCCESS.getCode()) {
                String privateKey = response.getResult().getUserWeIdPrivateKey().getPrivateKey();
                relationRepository.save(new Relation(name, response.getResult().getWeId(), privateKey, type));
//                PrivateKeyUtil.savePrivateKey(
//                        ConstantFields.KEY_DIR,
//                        response.getResult().getWeId(),
//                        response.getResult().getUserWeIdPrivateKey().getPrivateKey()
//                );
            }
//            response.getResult().setUserWeIdPrivateKey(null);
            return SuperResult.ok(response.getResult());
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
            int durationMonth = jsonObject.getIntValue("durationMonth");
            String weid = jsonObject.getString("weid");
            HashMap<String, String> requestMap = new HashMap<>();
            requestMap.put("weid", weid);
            String requestStr = JSON.toJSONString(requestMap);
            double dailyRate = Double.parseDouble(getDailyRate(requestStr).getData().toString());
            String createdTime = CommonUtils.generateDateStr();
            LoanRecord record = loanRecordRepository.save(new LoanRecord(weid, companyName, amount, durationMonth, weid, ConstantFields.LOAN_USER_ISSELF_WAITING, ConstantFields.LOAN_USER_ISCREDENTIALVERIFIED_WAITING, dailyRate, createdTime, ConstantFields.LOAN_STATUS_WAITING));
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
    public SuperResult repayLoan(String jsonStr) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            int id = jsonObject.getIntValue("id");
            LoanRecord record = loanRecordRepository.findOne(id);
            String repayTime = CommonUtils.generateDateStr();
            record.setRepayTime(repayTime);
            record.setStatus(ConstantFields.LOAN_STATUS_CONFIRM_RETURN);
            loanRecordRepository.save(record);
            // 获取用户借贷情况
            UserLoan userLoan = userLoanRepository.findUserLoansByWeidAndCompanyName(record.getWeid(), record.getCompanyName()).get(0);
            // 修改已还金额
            double repayAmount = userLoan.getRepayAmount();
            repayAmount += record.getAmount();
            userLoan.setRepayAmount(repayAmount);
            userLoanRepository.save(userLoan);
            return SuperResult.ok(record);
        } catch (Exception e) {
            return SuperResult.fail();
        }
    }

    @Override
    public SuperResult getCanLoanAmount(String jsonStr) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            String companyName = jsonObject.getString("companyName");
            String weid = jsonObject.getString("weid");
            // 获取能够借款的总额
            // 计算方式 初始金额 + 当前平台的借款金额 - 其他平台的待还金额
            double resultAmount = ConstantFields.LOAN_INITIAL_AMOUNT;
            List<UserLoan> userLoansByWeidAndCompanyName = userLoanRepository.findUserLoansByWeidAndCompanyName(weid, companyName);
            List<UserLoan> userLoansByWeid = userLoanRepository.findUserLoansByWeid(weid);
            // 用户在此平台上借过钱
            if (userLoansByWeidAndCompanyName != null && userLoansByWeidAndCompanyName.size() != 0) {
                resultAmount += userLoansByWeidAndCompanyName.get(0).getLoanAmount();
            }
            if (userLoansByWeid != null && userLoansByWeid.size() != 0) {
                for (UserLoan userLoan : userLoansByWeid) {
                    if (!userLoan.getCompanyName().equals(companyName)) {
                        resultAmount = resultAmount - userLoan.getLoanAmount() + userLoan.getRepayAmount();
                    }
                }
            }
            resultAmount = Math.max(resultAmount, 0.0);
            return SuperResult.ok(resultAmount);
        } catch (Exception e) {
            return SuperResult.fail();
        }
    }

    @Override
    public SuperResult getDailyRate(String jsonStr) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            String weid = jsonObject.getString("weid");
            // 获取用户日利率
            List<UserLoan> userLoansByWeid = userLoanRepository.findUserLoansByWeid(weid);
            double dailyRate = ConstantFields.LOAN_INITIAL_DAILYRATE;
            if (userLoansByWeid != null && userLoansByWeid.size() != 0) {
                int multiCount = userLoansByWeid.size();
                multiCount = Math.min(ConstantFields.MAX_LOANCOMPANY_COUNT, multiCount);
                dailyRate = dailyRate * (1 + multiCount * 0.2);
            }
            return SuperResult.ok(dailyRate);
        } catch (Exception e) {
            return SuperResult.fail();
        }
    }

    @Override
    public SuperResult getUserStatus(String jsonStr) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            String weid = jsonObject.getString("weid");
            List<User> usersByWeidAndStatus = userRepository.findUsersByWeidAndStatus(weid, ConstantFields.USER_STAUTS_SUCCESS);
            List<User> userToBeChecked = userRepository.findUsersByWeidAndStatus(weid, ConstantFields.USER_STAUTS_WAITING);
            HashMap<String, Integer> result = new HashMap<>();
            if (usersByWeidAndStatus != null && usersByWeidAndStatus.size() != 0) {
                result.put("status", 1); // 已核验
            } else if (userToBeChecked != null && userToBeChecked.size() != 0) {
                result.put("status", 0); // 待核验
            } else {
                result.put("status", 2); // 未核验
            }
            return SuperResult.ok(result);
        } catch (Exception e) {
            return SuperResult.fail();
        }
    }

}
