package com.example.lms.service;

import com.example.lms.dto.response.MemberResponse;

import java.util.List;

public interface MemberService {
    MemberResponse getMemberById(Long id);
    List<MemberResponse> getAllMembers();
    void deleteMember(Long id);
}
