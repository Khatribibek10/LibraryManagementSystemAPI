package com.example.lms.service.impl;

import com.example.lms.constants.ApiEndpoints;
import com.example.lms.dto.request.OAuthRequest;
import com.example.lms.dto.response.OAuthResponse;
import com.example.lms.properties.OAuthProperties;
import com.example.lms.service.OAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthServiceImp implements OAuthService {
    private final RestTemplate restTemplate;
    private final OAuthProperties properties;
    private String accessToken;

    @Value("${ewallet.oauth.tenant-id}")
    private  String tenantId;

    @Override
    public String login() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("unAuthorized", "true");
        headers.add("isOpenAPI", "true");
        headers.add("tenant-id", tenantId);

        OAuthRequest body = new OAuthRequest(properties.getUsername(), properties.getPassword());
        HttpEntity<OAuthRequest> request = new HttpEntity<>(body, headers);

        log.info("AuthURL(): {}", properties.getUrl() + ApiEndpoints.OAUTH_TOKEN);
        ResponseEntity<OAuthResponse> response =
                restTemplate.exchange(properties.getUrl() + ApiEndpoints.OAUTH_TOKEN, HttpMethod.POST, request, OAuthResponse.class);

        log.info("Response Object oAUTH: {}", response);

        accessToken = response.getBody().getData().getAccessToken();
        log.info("MeroAccessToken(): {}", accessToken);
        return accessToken;
    }

    @Override
    public String getToken() {
        if (accessToken == null) return login();
        return accessToken;
    }
}
