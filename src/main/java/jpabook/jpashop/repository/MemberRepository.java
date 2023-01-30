package jpabook.jpashop.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    //JPA가 제공하는 표준 애노테이션 (@Autowired 로 주입받는게 아니라 이걸로 받아야 하는데, 스프링부트가 이게 되게 지원해준다고는 한다.)
    //이게 있으면 스프링이 생성한 EntityManager를 DI해준다.
    //만약 EntityManagerFactory를 주입받고 싶으면 @PersistenceUnit 을 쓰면 되는데 거의 안쓴다.
    //@PersistenceContext
    private final EntityManager em; //@RequiredArgsConstructor를 통해 주입받을 수 있다.

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList(); //JPQL 문법
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
