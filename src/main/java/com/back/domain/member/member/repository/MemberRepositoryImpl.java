package com.back.domain.member.member.repository;

import com.back.domain.member.member.entity.Member;
import com.back.domain.member.member.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public long qCount() {
        QMember member = QMember.member;

        return Optional.ofNullable(
                queryFactory
                        .select(member.count())
                        .from(member)
                        .fetchOne()
        ).orElse(0L);
    }

    @Override
    public Optional<Member> findQByUsername(String username) {
        QMember member = QMember.member;

        return Optional.ofNullable(
                queryFactory
                        .selectFrom(member)
                        .where(member.username.eq(username))
                        .fetchOne()
        );
    }
}