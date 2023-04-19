package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member,Long>, MemberRepository {
// Spring Data JPA의 JpaRepository 인터페이스를 상속하면 Spring Data JPA가 구현체를 자동으로 만들고 Bean 등록도 해준다.

    //
    @Override
    Optional<Member> findByName(String name);
    //name으로 찾기 등은 여러 프로젝트에서 사용하는 형식에 따라 다르기 때문에 공통화 할 수 없음
    //따라서 Ovrride 하는데 이것도 findBy + ㅁㅁ 규칙을 지키면
    //JPQL로 예를 들어 select m from Member m where m.name = ? 와 같은 식으로 쿼리를 만들어줌.
}
