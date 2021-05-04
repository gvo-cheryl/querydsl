package com.example;

import com.example.entity.Member;

import static com.example.entity.QMember.member;

import com.example.repository.MemberRepository;
import com.example.repository.MemberRepositorySupport;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class BasicTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberRepositorySupport memberRepositorySupport;
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    private Object JPAQuery;

    @BeforeEach
    public void data() {
        memberRepository.save(Member.builder().name("hs").age(30).build());
        memberRepository.save(Member.builder().name("kh").age(29).build());
        memberRepository.save(Member.builder().name("jm").age(28).build());
        memberRepository.save(Member.builder().name("ky").age(100).build());
    }

    @Test
    public void jpql() {
        Member member = memberRepository.findByName("hs");
        System.out.println("member.getName() = " + member.getName());
    }

    @Test
    public void querydsl_기본기능() {
        // when
        List<Member> result = memberRepositorySupport.findByName("hs");
        for (Member member : result) {
            System.out.println("member.getName() = " + member.getName());
            System.out.println("member.getAge() = " + member.getAge());
        }

    }

    @Test
    public void querydsl_() {
        // when
        BooleanBuilder builder = new BooleanBuilder();

        List<Member> hello = jpaQueryFactory.selectFrom(member)
                .where(member.age.gt(28).and(member.name.contains("k")))
                .orderBy(member.age.desc())
                .fetch();

        for (Member member : hello) {
            System.out.println("name : " + member.getName() + ", age : " + member.getAge());
        }

        System.out.println("====================================");

        List<Member> result = memberRepositorySupport.findByAge(28);

        for (Member member : hello) {
            System.out.println("name : " + member.getName() + ", age : " + member.getAge());
        }
    }
}
