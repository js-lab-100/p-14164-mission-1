package com.back.domain.member.member.repository;

import com.back.domain.member.member.entity.Member;

import java.util.Optional;

public interface MemberRepositoryCustom {
    long qCount();

    Optional<Member> findQByUsername(String username);
}