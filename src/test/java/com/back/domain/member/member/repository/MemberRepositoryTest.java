package com.back.domain.member.member.repository;

import com.back.domain.member.member.entity.Member;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
}
