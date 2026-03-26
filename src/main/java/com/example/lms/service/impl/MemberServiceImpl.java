package com.example.lms.service.impl;

import com.example.lms.dto.response.MemberResponse;
import com.example.lms.entity.Member;
import com.example.lms.exception.ResourceNotFoundException;
import com.example.lms.repository.MemberRepository;
import com.example.lms.service.MemberService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public MemberResponse getMemberById(Long id) {
        return mapToResponse(findMemberById(id));
    }

    @Override
    public List<MemberResponse> getAllMembers() {
        return memberRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMember(Long id) {
        Member member = findMemberById(id);
        memberRepository.delete(member);
        log.info("Member deleted with id: {}", id);
    }

    private Member findMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member", "id", id));
    }


    public MemberResponse mapToResponse(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .mobileNo(member.getMobileNo())
                .address(member.getAddress())
                .role(member.getRole().name())
                .build();
    }
}
