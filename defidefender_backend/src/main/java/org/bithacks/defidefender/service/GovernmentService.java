package org.bithacks.defidefender.service;

import org.bithacks.defidefender.utils.SuperResult;

public interface GovernmentService {

    SuperResult createCredential(String jsonStr);

    SuperResult checkUser(String jsonStr);

    SuperResult listToBeCheckedUsers();

    SuperResult listVerifiedUsers();

    SuperResult listIssuedCredentials();
}
