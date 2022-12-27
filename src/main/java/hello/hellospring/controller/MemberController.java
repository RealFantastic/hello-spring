package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller //해당 어노테이션을 통해 스프링이 뜰 때 해당 컨트롤러를 객체로 가지고 있다 == 스프링 빈이 관리된다.
public class MemberController {

    //private final MemberService memberService = new MemberService();
    // new를 사용해서 인스턴스를 관리하지 않는 이유
    /* 스프링이 관리를 하게되면, 스프링 컨테이너에 객체를 등록하고 컨테이너로 부터 받아서 써야하기 때문
    *  MemberController 뿐만 아니라 다른 컨트롤러에서도 MemberService를 가져다 쓸 수 있는데,
    *  MemberService 인스턴스는 여러개가 만들어지는 것이 아니라 하나의 인스턴스로 관리되어야 하기 때문에 new를 사용하지 않는다.*/

    private final MemberService memberService;
    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }
}
