package org.bithacks.defidefender.service;

import org.bithacks.defidefender.contract.Certification;
import org.bithacks.defidefender.utils.SuperResult;

public interface CommonService {
    SuperResult createCPT(String jsonStr);

    SuperResult registerIssueType(String jsonStr);

    SuperResult getCredential(String jsonStr);

    SuperResult getPresentation(String jsonStr);

    void initializeNetwork();

    String deployCertification();

    Certification getCertification();

    SuperResult getCPTById(String jsonStr);

    SuperResult getCredentialsByWeid(String jsonStr);
}
