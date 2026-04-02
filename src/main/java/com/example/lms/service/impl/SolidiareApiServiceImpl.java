package com.example.lms.service.impl;

import com.example.lms.constants.ApiEndpoints;
import com.example.lms.dto.request.PayloadRequest;
import com.example.lms.dto.response.SolidiareApiResponse;
import com.example.lms.service.OAuthService;
import com.example.lms.service.SolidiareApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class SolidiareApiServiceImpl implements SolidiareApiService {
    private final RestTemplate restTemplate;
    private final OAuthService oauthService;

    @Value("${ewallet.oauth.url}")
    private String baseUrl;

    @Value("${ewallet.oauth.tenant-id}")
    private String tenantId;

    @Override
    public Object bankList(PayloadRequest payloadRequest) {
        String token = oauthService.getToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        headers.add("Accept-Language", "en");
        headers.add("Channel-Id", "portal");
        headers.add("tenant-id", tenantId);

        HttpEntity<PayloadRequest> request = new HttpEntity<>(payloadRequest, headers);

        String url = baseUrl + ApiEndpoints.BANK_LIST;
        ResponseEntity<SolidiareApiResponse> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        request,
                        SolidiareApiResponse.class
                );

        return response.getBody().getData();
    }

    @Override
    public Object branchList(PayloadRequest payloadRequest) {
        String token = oauthService.getToken();
        log.info("token: {}", token);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        headers.add("Accept-Language", "en");
        headers.add("Channel-Id", "portal");
        headers.add("tenant-id", tenantId);
//        headers.add("Authorization", "Bearer " + token);


        HttpEntity<PayloadRequest> request = new HttpEntity<>(payloadRequest, headers);

        String url = baseUrl + ApiEndpoints.BRANCH_LIST;
        ResponseEntity<SolidiareApiResponse> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        request,
                        SolidiareApiResponse.class
                );


        log.info("Response: {}", response.getBody());

        return response.getBody().getData();


    }


}
