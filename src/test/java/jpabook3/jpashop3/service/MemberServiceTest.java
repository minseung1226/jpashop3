package jpabook3.jpashop3.service;

import jpabook3.jpashop3.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import jpabook3.jpashop3.domain.Member;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;


    @Test
    void 회원가입(){
        Member member;
        member = new Member();
        member.setName("kim");
        memberService.join(member);

        Member findMember = memberService.findOne(member.getId());

        assertThat(member).isEqualTo(findMember);
    }

    @Test
    void 중복회원예외(){
        Member member;
        member = new Member();
        member.setName("kim");
        memberService.join(member);

        Member member2 = new Member();
        member2.setName("kim");


        assertThatThrownBy(()->memberService.join(member2))
                .isInstanceOf(IllegalStateException.class);
    }


}