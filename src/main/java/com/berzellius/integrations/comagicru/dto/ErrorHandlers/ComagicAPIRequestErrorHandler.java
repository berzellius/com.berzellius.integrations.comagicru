package com.berzellius.integrations.comagicru.dto.ErrorHandlers;

import com.berzellius.integrations.basic.dto.api.errorhandlers.APIRequestErrorException;
import com.berzellius.integrations.basic.dto.api.errorhandlers.APIRequestErrorHandler;
import org.apache.commons.io.IOUtils;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by berz on 22.10.2015.
 */
public class ComagicAPIRequestErrorHandler extends APIRequestErrorHandler {
    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
        String returnString = IOUtils.toString(clientHttpResponse.getBody());

        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("code", clientHttpResponse.getStatusCode().toString());
        properties.put("body", returnString);
        properties.put("header", clientHttpResponse.getHeaders());

        APIRequestErrorException errorException = new APIRequestErrorException(properties);
        throw errorException;
    }
}
