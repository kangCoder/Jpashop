package jpabook.jpashop.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
//이 두가지가 있어야 스프링부트를 올려서 테스트를 할 수 있다.
@Transactional //roll back 을 하기 위함.
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;


    @Test
    //스프링의 @Transactional 은 기본적으로 em을 db에 flush 하지 않고 롤백해버린다. 만약 눈으로 db에 담기는걸 보고 싶다면 @Rollback을 false로 두자.
    //하지만 테스트환경에서는 db에 데이터를 남기면 안되니까 롤백시켜놓는게 맞긴 하다. 이렇게 테스트하는 것에 익숙해지자.
    //@Rollback(value = false)
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long savedId = memberService.join(member);

        //then
        assertEquals(member, memberRepository.findOne(savedId)); //같은 PK로 된다면, 같은 영속성 컨텍스트로 관리되고 있다는 것.
    }

    @Test()
    public void 중복_회원_예외() throws Exception {
        //given (예외가 터지는 것을 기대하는 테스트)
        Member member1 = new Member();
        member1.setName("kang");
        Member member2 = new Member();
        member2.setName("kang");

        //when
        memberService.join(member1);

        //then
        assertThrows(IllegalStateException.class, () -> memberService.join(member2));
    }
}