package org.bithacks.defidefender.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.webank.weid.protocol.response.ResponseData;
import org.bithacks.defidefender.contract.Certification;
import org.bithacks.defidefender.dao.LoanRecordRepository;
import org.bithacks.defidefender.dao.RelationRepository;
import org.bithacks.defidefender.model.Po.LoanRecord;
import org.bithacks.defidefender.model.Vo.BlacklistEntity;
import org.bithacks.defidefender.service.CompanyService;
import org.bithacks.defidefender.service.DIDService;
import org.bithacks.defidefender.utils.CommonUtils;
import org.bithacks.defidefender.utils.ConstantFields;
import org.bithacks.defidefender.utils.SuperResult;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple5;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
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

    private CommonServiceImpl commonService = new CommonServiceImpl();

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

    @Override
    public SuperResult addWeidToBlacklist(String jsonStr) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            String weid = jsonObject.getString("weid");
            String record = jsonObject.getString("record");
            String publisher = jsonObject.getString("publisher");
            Certification certification = commonService.getCertification();
            TransactionReceipt receipt = certification.addBlacklistEntity(UUID.randomUUID().toString(), weid, record, publisher, CommonUtils.generateDate()).send();
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
            int status = handleType == 0 ? ConstantFields.LOAN_STATUS_CONFIRM_NOTRETURN : ConstantFields.LOAN_STATUS_REJECT;
            record.setStatus(status);
            loanRecordRepository.save(record);
            return SuperResult.ok();
        } catch (Exception e) {
            return SuperResult.fail();
        }
    }

    @Override
    public SuperResult checkLoanStatus() {
        List<LoanRecord> newRecords = new ArrayList<>();
        List<LoanRecord> allRecords = loanRecordRepository.findAll();
        String today = CommonUtils.generateDate();
        for (LoanRecord record : allRecords) {
            if (record.getStatus() == ConstantFields.LOAN_STATUS_WAITING && today.compareTo(record.getExpiredDate()) > 0) {
                record.setStatus(ConstantFields.LOAN_STATUS_REJECT);
                newRecords.add(record);
            } else if (record.getStatus() == ConstantFields.LOAN_STATUS_CONFIRM_NOTRETURN && today.compareTo(record.getExpiredDate()) > 0) {
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
                TransactionReceipt receipt = certification.addBlacklistEntity(UUID.randomUUID().toString(), record.getWeid(), recordStr, publisher, CommonUtils.generateDate()).send();
                List<Certification.AddBlacklistEntityEventEventResponse> response = certification.getAddBlacklistEntityEventEvents(receipt);
                if (!response.isEmpty()) {
                    return SuperResult.ok();
                } else {
                    System.out.println(" event log not found, maybe transaction not exec. ");
                    return SuperResult.fail();
                }
            }
        } catch (Exception e) {
            return SuperResult.fail();
        }
        return null;
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
}
