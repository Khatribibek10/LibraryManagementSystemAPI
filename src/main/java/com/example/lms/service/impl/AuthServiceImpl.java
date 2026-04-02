package com.example.lms.service.impl;

import com.example.lms.dto.request.LoginRequest;
import com.example.lms.dto.request.RegisterRequest;
import com.example.lms.dto.response.AuthResponse;
import com.example.lms.entity.Member;
import com.example.lms.enums.Role;
import com.example.lms.exception.BadRequestException;
import com.example.lms.exception.DuplicateResourceException;
import com.example.lms.repository.MemberRepository;
import com.example.lms.security.JwtService;
import com.example.lms.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AuthServiceImpl  implements AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        log.info("Register Resquest() {}" ,request.getRole());
        if(memberRepository.existsByEmail(request.getEmail())){
            log.info("Email ALready exists", request.getEmail());
            throw new DuplicateResourceException("Email already registered: " + request.getEmail());
        }

        Role role;
        try{
            role = (request.getRole() != null) ? Role.valueOf(request.getRole().toUpperCase()) : Role.ROLE_USER;
        }catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid role: " + request.getRole());
        }

        Member member = Member.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .mobileNo(request.getMobileNo())
                .address(request.getAddress())
                .role(role)
                .build();
        memberRepository.save(member);
        log.info("New Member Registered: {}", member.getEmail());

        String accessToken = jwtService.generateToken(member);
        String refreshToken = jwtService.generateRefreshToken(member);

        return buildAuthResponse(member, accessToken, refreshToken);

    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        Member member = memberRepository.findByEmail(request.getEmail()).orElseThrow(()-> new BadRequestException("Member Not Found"));
        String accessToken = jwtService.generateToken(member);
        String refreshToken = jwtService.generateRefreshToken(member);

        return buildAuthResponse(member, accessToken, refreshToken);
    }

    // ─── Helper ─────────────────────────────────────────────────────────────────
    private AuthResponse buildAuthResponse(Member member, String accessToken, String refreshToken) {
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .memberId(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .role(member.getRole().name())
                .build();
    }
}
