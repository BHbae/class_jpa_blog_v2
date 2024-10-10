package com.tenco.blog_v2.board;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BoardController {

    // DI
    // 네이티브 쿼리 연습
    private final BoardNativeRepository boardNativeRepository;

    // JPA API, JPQL
    private final BoardRepository boardRepository;


    // 게시글 수정 화면 요청
    // 주소설계 - http://localhost:8080/board/id/update
    @GetMapping("/board/{id}/update-form")
    public String update(@PathVariable(name = "id")Integer id,HttpServletRequest request){
        // 1. 게시글 조회
        Board board = boardRepository.findById(id);

        // 2. 요청 속성에 조회한 게시글 속성 및 값추가
        request.setAttribute("board", board);
        // 뷰 리졸브 - 템플릿 반화
        return "board/update-form";
    }


    // 게시글 수정 요청 기능
    // board/{id}/update
    @PostMapping("/board/{id}/update")
    public String update(@PathVariable(name = "id")Integer id,
                        @ModelAttribute BoardDTO.UpdateDTO dto,
                         Model model){
        // 1. 데이터 바인딩 방식 수정
        // 2. 인증 검사 - 로그인 여부 판단
        // 3. 권한 체크 - 내글이 맞나
       Board board = boardRepository.findById(id);

        // 4. 유효성 검사
        if(board == null){
          return "";
        }

        if(dto.title.isEmpty() || dto.title.trim().isEmpty()){
            return "";
        }
        if(dto.content.isEmpty() || dto.content.trim().isEmpty()){
            return "";
        }
        // 5. 서비스측 위임 (직접 구현) - 레파지토리 사영
        boardRepository.updateByIdJPA(id, dto.getTitle(), dto.getContent());

        return "redirect:/board/"+ id;
    }

    // 주소 설계 - http://localhost:8080/board/1
    // 특정 게시글 요청 화면
    @GetMapping("/board/{id}")
    public String detail(@PathVariable(name = "id")Integer id, HttpServletRequest request){
        // JPA API의 활용
        // Board board = boardRepository.findById(id);
        Board board = boardRepository.findByIdJoinUser(id);

        request.setAttribute("board",board);

        return "board/detail-form";
    }

    @GetMapping("/")
    public String index(Model model){

        List<Board> boardList = boardNativeRepository.findAll();

        // 방법 1: 단일 속성 추가
        model.addAttribute("boardList", boardList);
        log.warn("여기까지 오나");
        return "index";
    }

    // 주소 설계 - http://localhost:8080/board/save-form
    // 게시글 작성화면
    @GetMapping("/board/save-form")
    public String saveForm(){
        return "board/save-form";
    }

    // 주소설계 - http://localhost:8080/board/save
    // 게시글 저장
    @PostMapping("/board/save")
    public String save(@RequestParam(name = "title")String title,
                       @RequestParam(name = "content")String content){
        log.warn("save 실행 : 제목={},내용={}", title,content);
        boardNativeRepository.save(title,content);
        return "redirect:/";
    }





    // 주소 설계 - http://localhost:8080/board/1/delete (form 활용이기 깨문에 delete 선언)
    // form 태그에서는 GET, POST 방식만 지원한다.
    // 특정 게시글 삭제
    @PostMapping("baord/{id}/delete")
    public String delete(@PathVariable(name = "id")Integer id){
        boardNativeRepository.deleteById(id);
        return "redirect:/";
    }





}
