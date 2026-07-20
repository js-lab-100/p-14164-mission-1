package com.back.domain.wiseSaying.wiseSaying.repository;

import com.back.domain.wiseSaying.wiseSaying.entity.WiseSaying;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class WiseSayingRepositoryTest {
    @Autowired
    private WiseSayingRepository wiseSayingRepository;

    @Test
    @DisplayName("findQById")
    void t1() {
        WiseSaying wiseSaying = wiseSayingRepository.findQById(1).get();

        assertThat(wiseSaying.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("findQAll")
    void t2() {
        List<WiseSaying> wiseSayings = wiseSayingRepository.findQAll();

        assertThat(wiseSayings).hasSize(5);
    }

    @Test
    @DisplayName("qCount")
    void t3() {
        long count = wiseSayingRepository.qCount();

        assertThat(count).isEqualTo(5L);
    }
}
