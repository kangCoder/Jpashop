package jpabook.jpashop.service;

import java.util.List;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)//JPA의 데이터 변경이 일어날 때는 반드시 트랜잭션 안에서 이루어져야 함.
@RequiredArgsConstructor
public class MemberService {

    //field injection (추천하지 않음). 생성자가 하나일 때는 생략 가능.
//    @Autowired
//    private MemberRepository memberRepository;

    //생성자를 통한 주입을 하자.
    private final MemberRepository memberRepository;

    //Lombok의 @AllArgumentConstructor가 이걸 만들어줌 (@RequiredArgsConstructor를 많이 씀)
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    //멀티쓰레드 환경에서 동시성 문제 때문에 똑같은 이름으로 가입될 수 있다. -> member name을 unique 설정을 하자.
    private void validateDuplicateMember(Member member) {
        //중복된다면 exception 던지기
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원힙니다.");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //회원 하나 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

}
