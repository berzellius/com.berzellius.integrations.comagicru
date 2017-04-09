package com.berzellius.integrations.comagicru.service;

import com.berzellius.integrations.basic.dto.api.errorhandlers.APIRequestErrorException;
import com.berzellius.integrations.basic.exception.APIAuthException;
import com.berzellius.integrations.basic.service.APIServiceRequestsImpl;
import com.berzellius.integrations.comagicru.dto.ComagicAuthResponse;
import com.berzellius.integrations.comagicru.dto.ComagicResponse;
import com.berzellius.integrations.comagicru.dto.ComagicVisitorInfoResponse;
import com.berzellius.integrations.comagicru.dto.visitorinfo.Ac;
import com.berzellius.integrations.comagicru.dto.visitorinfo.VisitorInfo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by berz on 26.03.2017.
 */
@Service
public class ComagicAPIServiceImpl extends APIServiceRequestsImpl implements ComagicAPIService{

    private String baseUrl = "http://api.comagic.ru/";
    private ResponseErrorHandler errorHandler;
    private List<String> cookies;

    private String login;
    private String password;

    private String session_id;

    private static final Logger log = Logger.getLogger("ComagicAPI");


    @Override
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(this.getErrorHandler());
        return restTemplate;
    }

    @Override
    public void logIn() throws APIAuthException {
        String url = this.getBaseUrl() + "api/login/";
        HttpEntity<MultiValueMap<String, String>> request = this
                .requestByParams(this.createLoginParams());

        ComagicAuthResponse authResponse = this.request(
                url, HttpMethod.POST, request, ComagicAuthResponse.class
        );

        Assert.notNull(authResponse, "empty result while log in!");
        Assert.notNull(authResponse.getSuccess(), "empty data about success/not success log in!");

        if(authResponse.getSuccess()){
            Assert.notNull(authResponse.getData(), "auth result is success, but no data!");
            Assert.notNull(authResponse.getData().getSession_key(), "auth result is success, but no session_key!");
            this.setSession_id(authResponse.getData().getSession_key());
        }
        else{
            System.out.println("not success!" + authResponse.getMessage());
            throw new APIAuthException("not success log in, maybe wrong login/pass!");
        }
    }

    protected <T extends ComagicResponse> T requestData(String url, HttpMethod httpMethod, MultiValueMap<String, String> params, Class<T> cl) throws APIAuthException {
        if(this.getSession_id() == null){
            this.logIn();
        }

        HttpEntity<MultiValueMap<String, String>> request = this.requestByParamsAbstract(params);
        params.set("session_key", this.getSession_id());

        try {
            T response = this.request(this.getBaseUrl() + url, httpMethod, request, this.getRestTemplate(), cl, null);
            this.reLogins = 0;

            if(!response.getSuccess() && response.getMessage().equals("102401:Unauthorized")){
                log.info("need to re-authorize in Comagic..");
                this.reLogin();
                return requestData(url, httpMethod, params, cl);
            }

            return response;
        }
        catch (APIRequestErrorException e){
            log.warning("error while request to Comagic!");
            log.warning(e.getParams().toString());

            this.reLogin();
            return requestData(url, httpMethod, params, cl);
        }
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    private MultiValueMap<String, String> createLoginParams() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("login", getLogin());
        params.add("password", getPassword());

        return params;
    }

    private HttpEntity<MultiValueMap<String, String>> requestByParams(MultiValueMap<String, String> params){
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        if(this.cookies != null) {
            requestHeaders.add("Cookie", String.join(";", this.cookies));
        }

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, requestHeaders);
        return  request;
    }

    public ResponseErrorHandler getErrorHandler() {
        return errorHandler;
    }

    @Override
    public void setErrorHandler(ResponseErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    public void setCookies(List<String> cookies) {
        this.cookies = cookies;
    }

    @Override
    public String getActiveAcByVisitorId(Long visitorId) throws APIAuthException {
        VisitorInfo visitorInfo = this.getVisitorInfoByVisitorId(visitorId);

        String acActual = null;
        for(Ac ac : visitorInfo.getAcs()){
            if(ac.getState() != null && ac.getState().equals("active")){
                acActual = ac.getName();
            }
        }

        return acActual;
    }

    @Override
    public VisitorInfo getVisitorInfoByVisitorId(Long visitorId) throws APIAuthException {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        ComagicVisitorInfoResponse response = this.requestData(
                "api/v1/visitor/".concat(visitorId.toString()).concat("/"),
                HttpMethod.GET,
                params,
                ComagicVisitorInfoResponse.class
        );

        return response.getData();
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }
}
