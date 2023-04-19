package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    private final EntityManager em; //JPA 디펜던시를 받을 경우 스프링부트가 자동으로 EntityManger 객체를 생성해줌.

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public List<Member> findAll() {
        //JPQL이라는 SQL과 비슷한 언어 사용, 아래 쿼리는 Member 객체 자체를 select 한다.(sql
        List<Member> result = em.createQuery("select m from Member m", Member.class)
                .getResultList();
        return result;

    }
    //IntelliJ 단축키 Ctrl + Alt + N : 인라인 형식으로 변경
    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
    }
}
