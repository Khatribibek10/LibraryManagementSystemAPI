package com.example.lms.controller;

import com.example.lms.dto.response.ApiResponse;
import com.example.lms.dto.response.MemberResponse;
import com.example.lms.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/members")
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class MemberController {
    private final MemberService memberService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    @Operation(
            description = "Required ADMIN and LIBRARIAN role"
    )
    public ResponseEntity<ApiResponse<List<MemberResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("All members fetched", memberService.getAllMembers()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN') or #id == authentication.principal.id")
    @Operation(
            description = "Required ADMIN and LIBRARIAN role"
    )
    public ResponseEntity<ApiResponse<MemberResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Member fetched", memberService.getMemberById(id)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN', 'LIBRARIAN')")
    @Operation(
            description = "Required ADMIN and LIBRARIAN role"
    )
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.ok(ApiResponse.success("Member deleted successfully"));
    }
}
