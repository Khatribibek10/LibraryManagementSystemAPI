package com.example.lms.service;

import com.example.lms.dto.request.PayloadRequest;

import java.util.List;

public interface SolidiareApiService{
   Object branchList(PayloadRequest request);
   Object bankList(PayloadRequest request);
}
