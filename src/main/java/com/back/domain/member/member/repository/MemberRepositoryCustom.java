package com.back.domain.member.member.repository;

import com.back.domain.member.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MemberRepositoryCustom {
    long qCount();

    Optional<Member> findQByUsername(String username);

    Page<Member> findQPaged(String kwType, String kw, Pageable pageable);
}