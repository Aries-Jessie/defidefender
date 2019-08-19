package org.bithacks.defidefender.service;

import com.webank.weid.protocol.base.CptBaseInfo;
import com.webank.weid.protocol.response.ResponseData;
import org.bithacks.defidefender.utils.SuperResult;

public interface CommonService {
    SuperResult createCPT(String jsonStr);

    SuperResult registerIssueType(String jsonStr);

    SuperResult getCredential(String jsonStr);

    SuperResult getPresentation(String jsonStr);
}
