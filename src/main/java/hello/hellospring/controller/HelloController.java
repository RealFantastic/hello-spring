package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    //정적 컨텐츠
    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data", "hello!!");
        return "hello";
    }
    //템플릿 엔진을 통한 동적 데이터 삽입 후 렌더링
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(value="name", required = false) String name, Model model){
        model.addAttribute("name", name);

        return "hello-template";
    }
    //API 방식
    //ResponseBody : HttpResponse의 body부분에 직접 문자열 자체를 리턴함, html파일이 아님!!
    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name){
        return "hello " + name;
    }

    /*  ResponseBody를 사용한 Method에서 객체(Object)를 리턴시 문자열이 아니라
        HttpMessageConverter(JsonCoverter)를 통해 JSON으로 변환하여 리턴함.*/

    //객체를 JSON으로 변환해주는 Library : Jackson, Gson 등등...

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello {
        private String name;

        //Getter & Setter : Java Bean 규약, Property 방식
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

