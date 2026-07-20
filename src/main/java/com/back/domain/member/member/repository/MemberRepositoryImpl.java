package com.back.domain.member.member.repository;

import com.back.domain.member.member.entity.Member;
import com.back.domain.member.member.entity.QMember;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.Optional;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {
    private static final QMember MEMBER = QMember.member;

    private final JPAQueryFactory queryFactory;

    @Override
    public long qCount() {
        return Optional.ofNullable(
                queryFactory
                        .select(MEMBER.count())
                        .from(MEMBER)
                        .fetchOne()
        ).orElse(0L);
    }

    @Override
    public Optional<Member> findQByUsername(String username) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(MEMBER)
                        .where(MEMBER.username.eq(username))
                        .fetchOne()
        );
    }

    @Override
    public Page<Member> findQPaged(String kwType, String kw, Pageable pageable) {
        BooleanExpression searchCondition = createSearchCondition(kwType, kw);

        JPAQuery<Member> query = queryFactory
                .selectFrom(MEMBER)
                .where(searchCondition);

        pageable.getSort().forEach(sort -> {
            OrderSpecifier<?> orderSpecifier = createOrderSpecifier(sort);

            if (orderSpecifier != null) {
                query.orderBy(orderSpecifier);
            }
        });

        var content = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        var totalQuery = queryFactory
                .select(MEMBER.count())
                .from(MEMBER)
                .where(searchCondition);

        return PageableExecutionUtils.getPage(
                content,
                pageable,
                () -> Optional.ofNullable(totalQuery.fetchOne()).orElse(0L)
        );
    }

    private BooleanExpression createSearchCondition(String kwType, String kw) {
        if (kw == null || kw.isBlank()) {
            return null;
        }

        return switch (kwType) {
            case "username" -> MEMBER.username.contains(kw);
            case "nickname" -> MEMBER.nickname.contains(kw);
            default -> MEMBER.username.contains(kw)
                    .or(MEMBER.nickname.contains(kw));
        };
    }

    private OrderSpecifier<?> createOrderSpecifier(Sort.Order sort) {
        ComparableExpressionBase<?> expression = switch (sort.getProperty()) {
            case "id" -> MEMBER.id;
            case "username" -> MEMBER.username;
            case "nickname" -> MEMBER.nickname;
            default -> null;
        };

        if (expression == null) {
            return null;
        }

        return sort.isAscending()
                ? expression.asc()
                : expression.desc();
    }
}