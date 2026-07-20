package com.back.domain.member.member.repository;

import com.back.domain.member.member.entity.Member;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("qCount")
    void t1() {
        long count = memberRepository.qCount();

        assertThat(count).isEqualTo(5L);
    }

    @Test
    @DisplayName("findQByUsername")
    void t2() {
        String username = "user1";

        Member memberUser1 = memberRepository.findQByUsername(username).get();

        assertThat(memberUser1.getUsername()).isEqualTo(username);
    }

    @Test
    @DisplayName("findQPaged")
    void t3() {
        Page<Member> page1 = memberRepository
                .findQPaged("all", "1", PageRequest.of(0, 10, Sort.by("id").ascending()));

        assertThat(page1.getNumberOfElements()).isEqualTo(1);

        Page<Member> page2 = memberRepository.findQPaged("username", "유저", PageRequest.of(0, 10, Sort.by("username").ascending()));

        assertThat(page2.getNumberOfElements()).isEqualTo(0);
    }
}
