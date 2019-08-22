package org.bithacks.defidefender.service;

import org.bithacks.defidefender.utils.SuperResult;

public interface UserService {
    SuperResult createWeId(String jsonStr);

    SuperResult createSelectiveCredential(String jsonStr);

    SuperResult createPresentation(String jsonStr);

    SuperResult requestVerifyWeId(String jsonStr);

    SuperResult getCompanies();

    SuperResult requestLoan(String jsonStr);

    SuperResult listLoanRequests(String jsonStr);

    SuperResult listLoanRecords(String jsonStr);

    SuperResult repayLoan(String jsonStr);

    SuperResult getCanLoanAmount(String jsonStr);

    SuperResult getDailyRate(String jsonStr);

    SuperResult getUserStatus(String jsonStr);
}
