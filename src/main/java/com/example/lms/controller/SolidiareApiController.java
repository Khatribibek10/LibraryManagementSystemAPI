package com.example.lms.controller;

import com.example.lms.dto.request.PayloadRequest;
import com.example.lms.dto.response.ApiResponse;
import com.example.lms.service.SolidiareApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @Operation(
            summary = "Fetch Solidiare bank lists",
            description = "Call Internally to Third party API using. Only ADMIN role allowed.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("ROLE_ADMIN")
    public ResponseEntity<ApiResponse<Object>> bankList(@RequestBody  PayloadRequest request){
        return ResponseEntity.ok(ApiResponse.success("Bank list fetched successfully",  apiService.bankList(request)));
    }

    @PostMapping("/branchList")
    @PreAuthorize("ROLE_ADMIN")
    @Operation(
            summary = "Fetch Solidiare branch lists",
            description = "Call Internally to Third party API using. Only ADMIN role allowed. ",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<ApiResponse<Object>> branchList(@RequestBody PayloadRequest request){
        return ResponseEntity.ok(ApiResponse.success("Branch list fetched successfully",  apiService.branchList(request)));
    }


}
