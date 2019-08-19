package org.bithacks.defidefender.service;

import com.webank.weid.protocol.response.CreateWeIdDataResult;
import com.webank.weid.protocol.response.ResponseData;
import org.bithacks.defidefender.utils.SuperResult;

public interface UserService {
    ResponseData<CreateWeIdDataResult> createWeId();

    SuperResult createSelectiveCredential(String jsonStr);

    SuperResult createPresentation(String jsonStr);
}
