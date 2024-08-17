package com.Toy2.cart.controller;

import com.Toy2.cart.entity.CartDto;
import com.Toy2.cart.service.CartService;
import com.Toy2.order.entity.OrderDetailDto;
import com.Toy2.order.entity.OrderDto;
import com.Toy2.product.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

//예외처리
@Controller
@RequestMapping("/cart")
public class CartController {
    private CartService cartService;
    private ProductService productService;
    @Autowired
    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    /**
     * 회원의 장바구니 페이지 출력
     * @param model
     * @return "cart"
     * @throws Exception
     */
    @GetMapping("/cart")
    public String cartPage(Model model, HttpSession session) throws Exception {
        String customerEmail = (String) session.getAttribute("c_email");
        List<CartDto> cartDto = cartService.getCarts((String) session.getAttribute("c_email"));
        model.addAttribute("cartDto", cartDto);
        return "cart";
    }

    /**
     * 장바구니에서 장바구니 수량과 옵션을 변경하여 DB에 저장(지금 productOption 수정이
     * @param cartNo
     * @param productCnt
     * @return "redirect:/cart"
     * @throws Exception
     */
    @PostMapping("/modify")
    public String modifyCart(@RequestParam(name = "cartNo") int cartNo,
                             @RequestParam(name = "productCnt") int productCnt) throws Exception {
        if(productCnt <= 0) //0이하의
            return "redirect:/cart/cart";
        CartDto cartDto = new CartDto(productCnt);

        cartDto.setCartNo(cartNo);
        cartService.modifyCart(cartNo, cartDto);
        return "redirect:/cart/cart";
    }

    /**
     * 장바구니 페이지에 제품 정보 그대로를 주문페이지로 이동
     * 바로구매 페이지에서도 그정보를 그대로 이동
     * @param request
     * @param model
     * @return order
     * @throws Exception
     */
    @PostMapping("/order")
    public String orderPageGo(HttpServletRequest request, Model model, HttpSession session) throws Exception {
        String customerEmail = (String) session.getAttribute("c_email");

        OrderDto orDto = new OrderDto(customerEmail);
        List<OrderDetailDto> orderDetailList = new ArrayList<>();
        //제품을 담을 dto가 없어서 이렇게 우선 작성 차후에 변경 예정
        String[] productPrice = request.getParameterValues("productPrice");
        String[] productDeliveryPrice = request.getParameterValues("productDeliveryPrice");
        String[] productNos = request.getParameterValues("productNo");
        String[] productCnts = request.getParameterValues("productCnt");
        String[] productOptions = request.getParameterValues("productOption");

        if (customerEmail == null) {
            throw new Exception("로그인이 필요합니다.");
        }
        try {
            if (productNos != null) {
                for (int i = 0; i < productNos.length; i++) {
                    Long pp  = Long.parseLong(productPrice[i]);
                    Integer dp = Integer.valueOf(productDeliveryPrice[i]);
                    String productName = productService.selectProduct(Integer.parseInt(productNos[i])).getProductName();

                    if(Integer.parseInt(productCnts[i]) <= 0)
                        throw new Exception("0이하의 값 입력 불가");
                    OrderDetailDto odDto = new OrderDetailDto(
                            orDto.getOrderNo(),
                            Integer.parseInt(productNos[i]),
                            Integer.parseInt(productCnts[i]),
                            "주문완료",
                            productOptions[i],
                            "일반배송",
                            pp,
                            dp
                    );
                    odDto.setOrderDetailProductName(productName);
                    orderDetailList.add(odDto);
                }
            }else{
                throw new NullPointerException();
            }
        } catch (Exception e) {
            return "redirect:/cart/cart";
        }
        model.addAttribute("orderDto", orDto);
        model.addAttribute("orderDetailList", orderDetailList);

        return "order";
    }

    /**
     * 장바구니 번호로 장바구니 선택삭제
     * 전체삭제를 위해 리스트로 받아서 하나씩 삭제
     * @param cartNos
     * @return "redirect:/cart/cart"
     * @throws Exception
     */
    @PostMapping("/remove")
    public String removeSelectedItems(@RequestParam(name = "cartNo") List<Integer> cartNos) throws Exception {
        cartService.removeCarts(cartNos);
        return "redirect:/cart/cart";
    }

    @PostMapping("/add")
    public String addCar(HttpServletRequest request, Model model) throws Exception{

        int productNumber = Integer.parseInt(request.getParameter("productNo"));
        int productCnt = Integer.parseInt(request.getParameter("productCnt"));
        String productOption = request.getParameter("productOption");

        // 세션에서 사용자 이메일을 가져옴 (예: 로그인된 사용자)
        String customerEmail = (String) request.getSession().getAttribute("c_email");
        if (customerEmail == null || customerEmail.isEmpty()) {
            model.addAttribute("errorMessage", "로그인이 필요합니다.");
            return "redirect:/login";
        }

        // CartDto 객체를 생성하고 장바구니에 추가
        CartDto cartDto = new CartDto(productNumber, productCnt, productOption, customerEmail);
        cartService.addCart(cartDto);
        return "redirect:/cart/cart";
    }

}
