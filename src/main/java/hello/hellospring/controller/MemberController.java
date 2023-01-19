package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

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

    //members 폴더의 createMemberForm.html 파일을 찾아서 화면에 내려줌.
    //GET : 조회시에만 사용.
    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }

    //POST : 데이터를 form에 넣어서 전달시에 사용
    @PostMapping("/members/new")
    public String create(MemberForm form){ //파라미터로 넘어온 MemberForm의 field로 화면의 input의 name의 값이 setName()을 통해 입력됨.
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);

        return "members/memberList";
    }
}
