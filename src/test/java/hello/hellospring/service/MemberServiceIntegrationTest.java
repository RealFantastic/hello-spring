package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** MemberService 통합 테스트 */
@SpringBootTest //스프링 컨테이너와 테스트를 함께 실행한다.
@Transactional //테스트케이스에 어노테이션이 있을 경우 테스트 시작 전 트랜잭션을 생성하고 테스트 종료 후 롤백한다.
class MemberServiceIntegrationTest {
    //테스트 코드 작성 시에는 테스트 클래스를 다른 곳에 사용할 것이 아니기 때문에
    //@Autowired를 통한 필드 Injection도 괜찮다.

    /* 때론 이처럼 통합테스트가 필요할 때도 있지만,
    * 단위 테스트를 얼마나 잘 짜는 지가 중요하다. */
   @Autowired MemberService memberService;
   @Autowired MemberRepository memberRepository;

    @Test
    //테스트 코드의 메소드 이름은 한글로 사용해도 무방함

    //현재 테스트코드의 문제 : Member 인스턴스의 spring이라는 이름은 단 한번만 가입되기 때문에
    //여러번 테스트 시에 해당 구간에서 오류가 발생할 것이다.
    //이를 해결하기 위해 @Transactional 어노테이션을 통해 테스트가 종료된 후에 롤백시켜 데이터가
    //실제 DB에 저장되지 않도록 하여 여러번 테스트가 가능하도록 한다.
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("spring");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }
    @Test
    public void 중복_회원_에외(){
     //given
     Member member1 = new Member();
     member1.setName("정환이");

     Member member2 = new Member();
     member2.setName("정환이");
     //when
     memberService.join(member1);
     /* 아래 try ~ catch문을 통해 예외를 테스트 할 수 있지만,
     *  assertj에서 제공하는 assertThrows 메소드를 사용하여 특정 메소드가 실행될 때 어떤 예외가
     *  떨어져야 하는지 간단하게 테스트 할 수 있고, assertThrows는 Exception의 Message도 가지고 있으므로
     *  해당 예외 발생시 생성되는 메세지도 검증 할 수 있다. */

     //then
     IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
     assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");


    }
}