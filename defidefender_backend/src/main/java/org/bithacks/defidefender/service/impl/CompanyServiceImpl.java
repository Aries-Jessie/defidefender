package org.bithacks.defidefender.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.webank.weid.protocol.response.ResponseData;
import org.bithacks.defidefender.contract.Certification;
import org.bithacks.defidefender.dao.LoanRecordRepository;
import org.bithacks.defidefender.dao.RelationRepository;
import org.bithacks.defidefender.dao.UserLoanRepository;
import org.bithacks.defidefender.model.Po.LoanRecord;
import org.bithacks.defidefender.model.Po.UserLoan;
import org.bithacks.defidefender.model.Vo.BlacklistEntity;
import org.bithacks.defidefender.service.CommonService;
import org.bithacks.defidefender.service.CompanyService;
import org.bithacks.defidefender.service.DIDService;
import org.bithacks.defidefender.utils.CommonUtils;
import org.bithacks.defidefender.utils.ConstantFields;
import org.bithacks.defidefender.utils.SuperResult;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private DIDService weIdService;

    @Autowired
    private LoanRecordRepository loanRecordRepository;

    @Autowired
    private RelationRepository relationRepository;

    @Autowired
    private CommonService commonService;

    @Autowired
    private UserLoanRepository userLoanRepository;

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
            int id = jsonObject.getIntValue("id");
            String weid = jsonObject.getString("weid");
            String issuer = jsonObject.getString("issuer");
            int type = jsonObject.getInteger("type");
            int verifyType = jsonObject.getIntValue("verifyType");
            LoanRecord record = loanRecordRepository.findOne(id);
            int isVerified = verifyType == 0 ? ConstantFields.LOAN_USER_ISCREDENTIALVERIFIED_YES : ConstantFields.LOAN_USER_ISCREDENTIALVERIFIED_NO;
            record.setIsCredentialVerified(isVerified);
            loanRecordRepository.save(record);
            ResponseData<Boolean> response = weIdService.verifyCredential(weid, issuer, type);
            return SuperResult.ok(response);
        } catch (Exception e) {
            return SuperResult.fail();
        }
    }

    @Override
    public SuperResult addWeidToBlacklist(String jsonStr) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            String weid = jsonObject.getString("weid");
            String record = jsonObject.getString("record");
            String publisher = jsonObject.getString("publisher");
            Certification certification = commonService.getCertification();
            TransactionReceipt receipt = certification.addBlacklistEntity(UUID.randomUUID().toString(), weid, record, publisher, CommonUtils.generateDateStr()).send();
            List<Certification.AddBlacklistEntityEventEventResponse> response = certification.getAddBlacklistEntityEventEvents(receipt);
            if (!response.isEmpty()) {
                return SuperResult.ok();
            } else {
                System.out.println(" event log not found, maybe transaction not exec. ");
                return SuperResult.fail();
            }
        } catch (Exception e) {
            return SuperResult.fail();
        }
    }

    @Override
    public SuperResult listBlacklist() {
        try {
            Certification certification = commonService.getCertification();
            BigInteger bigCount = certification.getBlacklistRecordsCount().send();
            int count = bigCount.intValue();
            List<BlacklistEntity> records = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                Tuple5<String, String, String, String, String> entityTuple = certification.getBlacklistEntityByIndex(new BigInteger(i + "")).send();
                records.add(
                        new BlacklistEntity(entityTuple.getValue2(), entityTuple.getValue3(), entityTuple.getValue4(), entityTuple.getValue5())
                );
            }
            return SuperResult.ok(records);
        } catch (Exception e) {
            return SuperResult.fail();
        }
    }

    @Override
    public SuperResult listBlacklistByWeid(String jsonStr) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            String weid = jsonObject.getString("weid");
            Certification certification = commonService.getCertification();
            List<BigInteger> allIndexs = certification.getUserAllBlacklistEntitiesIndexs(weid).send();
            List<BlacklistEntity> records = new ArrayList<>();
            for (BigInteger i : allIndexs) {
                Tuple5<String, String, String, String, String> entityTuple = certification.getBlacklistEntityByIndex(i).send();
                records.add(
                        new BlacklistEntity(entityTuple.getValue2(), entityTuple.getValue3(), entityTuple.getValue4(), entityTuple.getValue5())
                );
            }
            return SuperResult.ok(records);
        } catch (Exception e) {
            return SuperResult.fail();
        }
    }

    @Override
    public SuperResult listLoanRequestRecords(String jsonStr) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            String companyName = jsonObject.getString("companyName");
            List<LoanRecord> records = loanRecordRepository.findLoanRecordsByCompanyNameAndStatus(companyName, ConstantFields.LOAN_STATUS_WAITING);
            return SuperResult.ok(records);
        } catch (Exception e) {
            return SuperResult.fail();
        }
    }

    @Override
    public SuperResult handleLoanRequest(String jsonStr) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            int id = jsonObject.getIntValue("id");
            int handleType = jsonObject.getIntValue("handleType"); // 0 - 确认 1 - 拒绝
            LoanRecord record = loanRecordRepository.findOne(id);
            if (handleType == 0) {
                String nowDate = CommonUtils.generateDateStr();
                String endTime = CommonUtils.getEndTime(nowDate, record.getDurationMonth());
                record.setStatus(ConstantFields.LOAN_STATUS_CONFIRM_NOTRETURN);
                record.setEffectiveTime(nowDate);
                record.setEndTime(endTime);
                loanRecordRepository.save(record);
                List<UserLoan> userLoansByWeidAndCompanyName = userLoanRepository.findUserLoansByWeidAndCompanyName(record.getWeid(), record.getCompanyName());
                // 已经在这家公司借过款
                if (userLoansByWeidAndCompanyName != null && userLoansByWeidAndCompanyName.size() != 0) {
                    UserLoan userLoan = userLoansByWeidAndCompanyName.get(0);
                    double loanAmount = userLoan.getLoanAmount();
                    loanAmount += record.getAmount();
                    userLoan.setLoanAmount(loanAmount);
                    userLoanRepository.save(userLoan);
                } else { // 从未在这家公司借过款
                    userLoanRepository.save(new UserLoan(record.getWeid(), record.getCompanyName(), record.getAmount()));
                }
            } else {
                record.setStatus(ConstantFields.LOAN_STATUS_REJECT);
                loanRecordRepository.save(record);
            }
            return SuperResult.ok();
        } catch (Exception e) {
            return SuperResult.fail();
        }
    }

    @Override
    public SuperResult checkLoanStatus() {
        List<LoanRecord> newRecords = new ArrayList<>();
        List<LoanRecord> allRecords = loanRecordRepository.findAll();
        String today = CommonUtils.generateDateStr();
        for (LoanRecord record : allRecords) {
            if (record.getStatus() == ConstantFields.LOAN_STATUS_CONFIRM_NOTRETURN && today.compareTo(record.getEndTime()) > 0) {
                record.setStatus(ConstantFields.LOAN_STATUS_CONFIRM_TIMEOUT);
                newRecords.add(record);
            }
        }
        loanRecordRepository.save(newRecords);
        return null;
    }

    @Override
    public SuperResult addToBlacklist(String jsonStr) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            int id = jsonObject.getIntValue("id");
            LoanRecord record = loanRecordRepository.findOne(id);
            if (record.getStatus() == ConstantFields.LOAN_STATUS_CONFIRM_TIMEOUT) {
                record.setStatus(ConstantFields.LOAN_STATUS_ADDBLACKLIST);
                loanRecordRepository.save(record);
                String publisher = relationRepository.findRelationsByName(record.getCompanyName()).get(0).getWeid();
                String recordStr = CommonUtils.generateBlacklistRecord(record);
                Certification certification = commonService.getCertification();
                TransactionReceipt receipt = certification.addBlacklistEntity(UUID.randomUUID().toString(), record.getWeid(), recordStr, publisher, CommonUtils.generateDateStr()).send();
                List<Certification.AddBlacklistEntityEventEventResponse> response = certification.getAddBlacklistEntityEventEvents(receipt);
                if (!response.isEmpty()) {
                    return SuperResult.ok();
                } else {
                    System.out.println(" event log not found, maybe transaction not exec. ");
                    return SuperResult.fail();
                }
            }
            return SuperResult.ok();
        } catch (Exception e) {
            return SuperResult.fail();
        }
    }

    @Override
    public SuperResult listLoanRecords(String jsonStr) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            String companyName = jsonObject.getString("companyName");
            List<LoanRecord> records = loanRecordRepository.findLoanRecordsByCompanyNameAndStatusGreaterThanEqual(companyName, 1);
            return SuperResult.ok(records);
        } catch (Exception e) {
            return SuperResult.fail();
        }
    }

    @Override
    public SuperResult verifyUserAuthenticity(String jsonStr) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            int id = jsonObject.getIntValue("id");
            LoanRecord record = loanRecordRepository.findOne(id);
            record.setIsUserSelf(ConstantFields.LOAN_USER_ISSELF_YES);
            loanRecordRepository.save(record);
            return SuperResult.ok(true);
        } catch (Exception e) {
            return SuperResult.fail();
        }
    }

    @Override
    public SuperResult verifyMultiParityLoan(String jsonStr) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            String weid = jsonObject.getString("weid");
            // 获取已借平台
            List<UserLoan> userLoansByWeid = userLoanRepository.findUserLoansByWeid(weid);
            double count = 0.0, notPayAmount = 0.0;
            // 用户有过历史借款记录
            if (userLoansByWeid != null && userLoansByWeid.size() != 0) {
                // 计算多头借贷情况
                count = userLoansByWeid.size() * 1.0;
                for (UserLoan userLoan : userLoansByWeid) {
                    double loanAmount = userLoan.getLoanAmount();
                    double repayAmount = userLoan.getRepayAmount();
                    notPayAmount += loanAmount - repayAmount;
                }
            }
            HashMap<String, Double> result = new HashMap<>();
            result.put("multiParityCount", count);
            result.put("notPayAmount", notPayAmount);
            return SuperResult.ok(result);
        } catch (Exception e) {
            return SuperResult.fail();
        }
    }
}
