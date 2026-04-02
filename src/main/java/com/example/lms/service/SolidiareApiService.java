package com.example.lms.service;

import com.example.lms.dto.request.PayloadRequest;

public interface SolidiareApiService{
   Object branchList(PayloadRequest request);
   Object bankList(PayloadRequest request);
}
