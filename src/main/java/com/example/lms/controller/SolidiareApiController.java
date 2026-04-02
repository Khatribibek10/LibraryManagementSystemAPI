package com.example.lms.controller;

import com.example.lms.dto.request.PayloadRequest;
import com.example.lms.dto.response.ApiResponse;
import com.example.lms.service.SolidiareApiService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/solidiare")
@AllArgsConstructor
public class SolidiareApiController {
    private final SolidiareApiService apiService;

    @PostMapping("/bankList")
    public ResponseEntity<ApiResponse<Object>> bankList(@RequestBody  PayloadRequest request){
        return ResponseEntity.ok(ApiResponse.success("Bank list fetched successfully",  apiService.bankList(request)));
    }

    @PostMapping("/branchList")
    public ResponseEntity<ApiResponse<Object>> branchList(@RequestBody PayloadRequest request){
        return ResponseEntity.ok(ApiResponse.success("Branch list fetched successfully",  apiService.branchList(request)));
    }


}
