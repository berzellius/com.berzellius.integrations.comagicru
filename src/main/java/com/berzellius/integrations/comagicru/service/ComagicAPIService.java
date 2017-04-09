package com.berzellius.integrations.comagicru.service;

import com.berzellius.integrations.basic.exception.APIAuthException;
import com.berzellius.integrations.comagicru.dto.visitorinfo.VisitorInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResponseErrorHandler;

/**
 * Created by berz on 26.03.2017.
 */
@Service
public interface ComagicAPIService {
    void setErrorHandler(ResponseErrorHandler errorHandler);

    String getActiveAcByVisitorId(Long visitorId) throws APIAuthException;

    public VisitorInfo getVisitorInfoByVisitorId(Long visitorId) throws APIAuthException;

    void setLogin(String login);

    void setPassword(String password);
}
