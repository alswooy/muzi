package com.Toy2.product.controller;

import com.Toy2.product.db.dto.ProductDto;
import com.Toy2.product.db.dto.ResultResponseDto;
import com.Toy2.product.db.dto.request.ProductRequestDto;
import com.Toy2.product.db.dto.request.ProductPageRequestDto;
import com.Toy2.product.db.dto.request.ProductUpdateRequestDto;
import com.Toy2.product.db.dto.response.ProductResponseDto;
import com.Toy2.product.domain.service.ProductService;
import com.Toy2.product.option.db.dto.ProductOptionDto;
import com.Toy2.product.option.db.dto.request.OptionDeleteRequestDto;
import com.Toy2.product.option.db.dto.request.OptionRequestDto;
import com.Toy2.product.picture.PictureUploadService;
import com.Toy2.product.picture.dto.PictureUploadDto;
import com.Toy2.product.productdetail.db.dto.ProductPictureDto;
import com.Toy2.productcategory.db.dto.ProductCategoryDto;
import com.Toy2.productcategory.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private PictureUploadService pictureUploadService;

    @GetMapping("product/")
    public ModelAndView productHome(ModelAndView modelAndView) {
        modelAndView.setViewName("product");
        return modelAndView;
    }

    @GetMapping("product/list")
    @ResponseBody
    public ResultResponseDto<List<ProductDto>> getProductList(@ModelAttribute ProductPageRequestDto pageRequestDto) {
        List<ProductDto> productDtos = productService.selectProductPage(pageRequestDto);
        return new ResultResponseDto<>(productDtos, productDtos != null);
    }

    @GetMapping("product/detail")
    public ModelAndView getProductDetail(@RequestParam int productNumber, ModelAndView modelAndView) {
        Map<Integer, List<ProductPictureDto>> map = productService.selectPictures(productNumber);
        ProductDto productDto = productService.selectProduct(productNumber);
        Map<String, List<ProductOptionDto>> options = productService.selectProductOption(productNumber);
        modelAndView.setViewName("product-detail");
        modelAndView.addObject("attribute", map);
        modelAndView.addObject("product", productDto);
        modelAndView.addObject("productOption", options);
        return modelAndView;
    }

    @GetMapping("product/info")
    @ResponseBody
    public ProductResponseDto getProductInfo(@RequestParam int productNumber) {
        System.out.println("productNumber = " + productNumber);
        ProductDto productDto = productService.selectProduct(productNumber);
        Map<String, List<ProductOptionDto>> stringListMap = productService.selectProductOption(productNumber);
        Map<Integer, List<ProductPictureDto>> map = productService.selectPictures(productNumber);
        System.out.println("stringListMap = " + stringListMap);
        return new ProductResponseDto(productDto, stringListMap, map);
    }

    @GetMapping("product/pages")
    @ResponseBody
    public ResultResponseDto<List<Integer>> getPages(int limit) {
        List<Integer> pages = productService.countPages(limit);
        return new ResultResponseDto<>(pages, pages != null);
    }

    @GetMapping("product/categories")
    @ResponseBody
    public ResultResponseDto<Map<Integer, List<ProductCategoryDto>>> getCategories() {
        Map<Integer, List<ProductCategoryDto>> categories = productCategoryService.getCategories();
        return new ResultResponseDto<>(categories, categories != null);
    }


    @PostMapping(value = "/product/submit")
    public void test(@RequestParam Map<String, String> request) {
        System.out.println("ProductController.test");
        List<ProductOptionDto> option = request.keySet().stream().filter(k -> k.contains("option"))
                .map(request::get).mapToInt(Integer::parseInt).boxed().map(k -> productService.selectOption(k))
                .collect(Collectors.toList());
        System.out.println(option);
        System.out.println(request);
    }

    @GetMapping("product/add-product")
    public String addProductPage() {
        return "add-product";
    }

    @PostMapping("/product/add-product")
    @ResponseBody
    public boolean submitProduct(@RequestBody ProductRequestDto productRequestDto) {
        return productService.insertProductAndOption(productRequestDto);
    }


    @GetMapping("product/update-product")
    public ModelAndView updateProductPage(@RequestParam int productNumber, ModelAndView modelAndView) {
        modelAndView.setViewName("update-product");
        modelAndView.addObject("productNumber", productNumber);
        return modelAndView;
    }

    @PostMapping("product/update-product")
    public void updateProduct(@RequestBody ProductUpdateRequestDto dto) {
        System.out.println("dto = " + dto);
    }

    @PostMapping("product/update-product-test")
    public void updateProduct(@RequestBody TestDto dto) {
        System.out.println("dto = " + dto);
    }

    @PostMapping("product/delete-option")
    @ResponseBody
    public boolean deleteProductOption(@RequestBody OptionDeleteRequestDto optionDeleteRequestDto) {
        System.out.println("ProductController.deleteProductOption");
        return productService.deleteOption(optionDeleteRequestDto);
    }

    @PostMapping("product/delete-option-value")
    @ResponseBody
    public boolean deleteProductOptionValue(@RequestParam(required = false) int optionNumber) {
        System.out.println("optionNumber = " + optionNumber);
        return productService.deleteOptionValue(optionNumber);
    }

    @PostMapping
    @ResponseBody
    public boolean insertOption(@RequestParam int productNumber, @RequestParam String optionName) {
        return productService.insertOption(new OptionRequestDto(productNumber, null, null, false));
    }

    @PostMapping("multi")
    public void multiTest(@RequestParam MultipartFile multipartFile) throws IOException {
        byte[] bytes = multipartFile.getBytes();
        System.out.println("multipartFile.getBytes() = " + bytes);
        System.out.println("multipartFile = " + multipartFile);
        System.out.println("multipartFile.getOriginalFilename() = " + multipartFile.getOriginalFilename());
        pictureUploadService.insert(new PictureUploadDto(multipartFile.getOriginalFilename(), Base64.getEncoder().encodeToString(bytes)));
    }

    @GetMapping("get-image")
    @ResponseBody
    public ResponseEntity<byte[]> getImage(@RequestParam int id) {
        String base64Image = pictureUploadService.selectById(id);  // Base64로 인코딩된 이미지 가져오기
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);  // 디코딩된 바이트 배열

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        // 만약 PNG 이미지라면 MediaType.IMAGE_PNG 사용
        // headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }
}