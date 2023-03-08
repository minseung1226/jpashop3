package jpabook3.jpashop3.repository;

import jpabook3.jpashop3.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Rollback(value = false)
    void saveAndFind(){
        Member member = new Member();
        member.setUsername("user");

        Long saveId = memberRepository.save(member);

        Member findMember = memberRepository.find(saveId);

        assertThat(member).isEqualTo(findMember);
    }
}