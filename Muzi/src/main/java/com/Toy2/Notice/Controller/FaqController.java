package com.Toy2.Notice.Controller;


import com.Toy2.Notice.domain.FaqDto;
import com.Toy2.Notice.entity.FaqCategory;
import com.Toy2.Notice.service.FaqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping( "/faq")
public class FaqController {

    @Autowired FaqService faqService;       // FaqService 주입 받기


    /* READ - FAQ 리스트 보여줌 */
    @RequestMapping("")
    public String faqCenter(Model model){
        try {
            List<FaqDto> faqList = faqService.selectAll();      // 서비스에서 모든 FAQ 게시글을 List로 가져온다

            for (FaqDto faq : faqList) {        // 가져온 FaqDto의 리스트를 순회
                String categoryName = FaqCategory.getCategoryName(faq.getCate_no());        // 카테고리 번호를 이용해 카테고리 이름 조회
                faq.setCategoryName(categoryName);          // 조회한 카테고리 이름을 FAQ 게시글의 카테고리 이름으로 설정
            }

            model.addAttribute("list", faqList);        // model에 FAQ 목록을 추가하여 view에서 사용할 수 있도록 함
        } catch (Exception e){              // 예외가 발생하면
            e.printStackTrace();
            //model.addAttribute("msg", "LIST_ERR");          // 에러 메시지를 model에 추가
        }
        return "faq_center";
    }


    /* READ - 선택한 FAQ 게시글 조회 */
//    @RequestMapping("/view")
    @GetMapping("/view")
    public String viewFaq(@RequestParam("faq_no") Integer faq_no, Model model) throws Exception {       // 매개변수로 조회할 faq_no 넘김
        FaqDto faqDto = faqService.selectFaq(faq_no);           // 매개변수로 받은 faq_no를 가진 게시글을 faqDto로 저장
        if (faqDto == null) {               // faqDto가 null이면 해당하는 게시글이 없다는 것
            throw new Exception("FAQ not found");
        }

        // cate_no를 카테고리의 이름으로 표시하기 위한 변환
        String categoryName = FaqCategory.getCategoryName(faqDto.getCate_no());         // FaqCategory에서 번호에 해당하는 카테고리 이름을 저장
        faqDto.setCategoryName(categoryName);           // 저장한 카테고리 이름을 faqDto의 카테고리로 설정

        model.addAttribute("faqDto", faqDto);           // view에 넘기기 위해 faqDto를 model에 추가
        return "faq_view";          // faq_view.jsp 선택한 게시글 보여주는 페이지로 이동
    }


    /* CREATE - FAQ 등록 페이지로 이동 */
    @GetMapping("/register")
    public String insertFAQ() {
        return "faq_register";
    }


    /* CREATE - FAQ 등록 페이지에서 내용 입력 후 등록 */
    @PostMapping("/register")
    public String registerFAQ(
            @RequestParam("cate_no") Integer cate_no, @RequestParam("faq_order") Integer faq_order, @RequestParam("faq_title") String faq_title,
            @RequestParam("faq_content") String faq_content, @RequestParam("faq_writer") String faq_writer, Model model){       // faq_reg_date는 사용자가 입력하는게 아니라 FAQ 등록 시점을 저장하는 거

        FaqDto faqDto = new FaqDto();           // 새로운 FaqDto 객체 생성
        faqDto.setCate_no(cate_no);             // 매개변수로 넘어온 값을 Setter를 이용해서 객체에 저장
        faqDto.setFaq_title(faq_title);
        faqDto.setFaq_order(faq_order);
        faqDto.setFaq_content(faq_content);
        faqDto.setFaq_writer(faq_writer);

        try {
            if (faqService.insertFaq(faqDto) != 1)          // != 1이면 insert 안 됐다는 뜻
                throw new Exception("FAQ registration failed.");

            model.addAttribute("faqDto", faqDto);           // == 1이면 insert 된 거니까
//            model.addAttribute("msg", "REG_OK");
            return "redirect:/faq";                    // /faq로 등록해놓은 faq_center.jsp로 리다이렉트
        } catch (Exception e) {
            e.printStackTrace();
//            model.addAttribute("msg", "REG_ERR");
            return "faq_register";          // 예외가 발생하면 다시 FAQ 등록 폼으로 이동
        }
    }


    /* delete - 게시글 조회한 페이지 faq_view.jsp에서 삭제 작업 */
    //     관리자만 FAQ 게시글 삭제 가능
    @DeleteMapping("/remove")
    public void remove(@RequestParam Integer faq_no, HttpServletResponse response) throws IOException {
        try {
            if (faq_no == null || faq_no <= 0) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("유효하지 않은 FAQ 번호");
                return;
            }
            if (faqService.deleteFaq(faq_no) != 1) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("삭제 실패했습니다.");
                return;
            }
//            response.setContentType("text/plain;charset=UTF-8");      // 설정된 문자 인코딩과 Content-Type을 사용하여 응답 작성
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().write("FAQ 삭제 에러");
        }
    }

    /* update - 등록된 FAQ 수정 */
    // faq_view.jsp 페이지에서 수정
    // 작성자 & 관리자 수정 가능
    // 수정은 FAQ 등록 페이지에서 기존 데이터를 가지고 수정
    @PatchMapping("/modify")  // PatchMapping 사용
    public String update(@RequestParam FaqDto faqDto, RedirectAttributes rdr, Model m) {
        // 로그인 시 관리자/작성자인지 확인 - 추후 구현
        try {
            if (faqDto == null) {
                throw new Exception("유효하지 않은 FAQ 번호");
            }

            if (faqService.updateFaq(faqDto) != 1) {
                throw new Exception("수정 실패했습니다.");
            }

            rdr.addFlashAttribute("msg", "MOD_OK");
            return "redirect:/faq";  // 수정 성공 후 FAQ 목록 페이지로 리다이렉트

        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("faqDto", faqDto);  // 기존 데이터를 다시 폼에 전달
            m.addAttribute("msg", "MOD_ERR");
            return "faq";  // 수정 실패 시 수정 페이지로 이동
        }
    }

}