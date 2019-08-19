package org.bithacks.defidefender.service;

import org.bithacks.defidefender.utils.SuperResult;

public interface CompanyService {

    SuperResult verifyPresentation(String jsonStr);

    SuperResult verifyCredential(String jsonStr);
}
