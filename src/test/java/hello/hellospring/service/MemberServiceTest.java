package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

   MemberService memberService;
//   MemberService memberService = new MemberService();
   MemoryMemberRepository memberRepository;
//   MemoryMemberRepository memberRepository = new MemoryMemberRepository();

   /*지금 위의 MemoryMemberRepository 인스턴스는 MemberService에서 가지고 있는
   * MemoryMemberRepository와는 다른 인스턴스이다.(다른 메모리 주소값을 가지므로)
   * 테스트 시에는 MemberService에서 가진 같은 인스턴스로 테스트를 하는 것이 옳기 떄문에
   * MemberService 의 생성자에 MemoryMemberRepository를 추가하여 생성하도록 한다.
   * 그 후 @BeforeEach 어노테이션을 사용해 memberService에 MemberRepository의 의존성을 주입해준다. */

    @BeforeEach
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

   @AfterEach
   public void afterEach(){
       memberRepository.clearStore();
   }
    @Test
    //테스트 코드의 메소드 이름은 한글로 사용해도 무방함
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("jung-hwan");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }
    /* 위 회원가입 테스트는 반쪽짜리 테스트이다. 회원가입에서 중요한 중복 회원 예외가 잘 처리되는지 확인하려면
    중복 회원 검증이 잘 수행되는지, 예외를 잘 던지는지 확인해야 한다. */
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

     /*try{
         memberService.join(member2);
         fail();
     }catch(IllegalStateException e){
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
     }*/

    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}