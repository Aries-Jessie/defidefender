package org.bithacks.defidefender.service;

import org.bithacks.defidefender.utils.SuperResult;

public interface CompanyService {

    SuperResult verifyPresentation(String jsonStr);

    SuperResult verifyCredential(String jsonStr);

    SuperResult addWeidToBlacklist(String jsonStr);

    SuperResult listBlacklist();

    SuperResult listBlacklistByWeid(String jsonStr);

    SuperResult listLoanRequestRecords(String jsonStr);

    SuperResult handleLoanRequest(String jsonStr);

    SuperResult checkLoanStatus();

    SuperResult addToBlacklist(String jsonStr);

    SuperResult listLoanRecords(String jsonStr);

    SuperResult verifyUserAuthenticity(String jsonStr);

    SuperResult verifyMultiParityLoan(String jsonStr);

    SuperResult requestVerifyMultiParityLoan(String jsonStr);

    SuperResult listRequestVerifyMultiParityLoanRecords(String jsonStr);

    SuperResult hanleRequestVerifyMultiParityLoanRecord(String jsonStr);

    SuperResult listMultiParityLoanInfo(String jsonStr);
}
