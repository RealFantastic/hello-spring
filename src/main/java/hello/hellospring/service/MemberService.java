package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {
    private final MemberRepository memberRepository;

    /*
    DI : Dependency Injection
    아래의 경우 MemberRepository를 MemberService에서 생성하는 것이 아니라 외부에서 받아서 생성한다.
    이처럼 외부에서 의존성을 주입 받는 것을 DI 라고 한다.
    */
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     * */
    public Long join(Member member){
        //비즈니스 정책1 : 같은 이름의 회원은 가입할 수 없다.(가정)
        //회원명 중복확인

//        Optional<Member> result = memberRepository.findByName(member.getName());
//        //꿀팁 : Ctrl + Alt + V 리턴타입에 맞는 변수를 만들어줌.
//        result.ifPresent(m -> { //ifPresesnt() : 값이 있을 때의 분기처리를 할 수있는 Optional 클래스의 Method
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        });

        validateDuplicateMember(member);//중복 회원 검증
        memberRepository.save(member);

        return member.getId();
    }

    private void validateDuplicateMember(Member member){
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }
    /**
     * 전체회원 조회
     * */
    public List<Member> findMembers(){
        return memberRepository.finAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
