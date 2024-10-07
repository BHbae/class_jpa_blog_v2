package com.tenco.blog_v1.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class UserController {

    /**
     * 회원가입 페이지 요청
     * 주소 설계 : http://localhost:8080/join-form
     * @param model
     * @return 문자열
     * 반환되는 문자열을 뷰리졸버가 처리라며
     * 머스테치 템프릿 엔진을 통해서 뷰 파일을 렌더링 한다.
     */
    @GetMapping("/join-form")
    public String joinForm(Model model){
        log.warn("회원가입 페이지");
        model.addAttribute("name","회원가입 페이지");

        return "user/join-form"; // 템플릿 경로 : user/join-form.mustache
    }

    /**
     * 로그인 페이지 요청
     * 주소 설계 : http://localhost:8080/login-form
     * @param model
     * @return 문자열
     * 반환되는 문자열을 뷰리졸버가 처리라며
     * 머스테치 템프릿 엔진을 통해서 뷰 파일을 렌더링 한다.
     */
    @GetMapping("/login-form")
    public String loginForm(Model model){
        log.warn("로그인 페이지");
        model.addAttribute("name","로그인 페이지");

        return "user/login-form"; // 템플릿 경로 : user/join-form.mustache
    }

    /**
     * 회원 정보 수정 페이지 요청
     * 주소 설계 : http://localhost:8080/user/update-form
     * @param model
     * @return 문자열
     * 반환되는 문자열을 뷰리졸버가 처리라며
     * 머스테치 템프릿 엔진을 통해서 뷰 파일을 렌더링 한다.
     */
    @GetMapping("/update-form")
    public String updateForm(Model model){
        log.warn("회원 정보 수정 페이지");
        model.addAttribute("name","회원 정보 수정 페이지");

        return "user/update-form"; // 템플릿 경로 : user/join-form.mustache
    }


}
