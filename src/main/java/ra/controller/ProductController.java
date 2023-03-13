package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ra.model.entity.Exhibition;
import ra.model.entity.Product;
import ra.model.entity.User;
import ra.model.service.ExhibitionService;
import ra.model.service.ProductService;
import ra.model.service.UserService;
import ra.payload.request.ProductRequet;
import ra.payload.response.ProductResponse;
import ra.security.CustomUserDetail;

import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private ExhibitionService exhibitionService;


    /////////////////////////////Start GetAllProduct///////////////////////////////////////
    @GetMapping("/getAllProduct")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<ProductResponse> getAllProduct() {
        List<ProductResponse> listProductResponse = new ArrayList<>();
        List<Product> listProduct = productService.findAll();
        for (Product pro: listProduct) {
            ProductResponse prs = new ProductResponse();
            prs.setProductId(pro.getProductID());
            prs.setProductName(pro.getProductName());
            prs.setProductAuthor(pro.getProductAuthor());
            prs.setProductPrice(pro.getProductPrice());
            prs.setProductImage(pro.getProductImage());
            prs.setProductDecription(pro.getProductDescription());
            prs.setProductCreateDate(pro.getProductCreateDate());
            prs.setUserName(pro.getUsers().getUserName());
            listProductResponse.add(prs);
        }
        return listProductResponse;
    }


    /////////////////////////////End GetAllProduct///////////////////////////////////////

    /////////////////////////////Start GetProductById///////////////////////////////////////

    @GetMapping("/{productId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ProductResponse getProductById(@PathVariable("productId") int productId){
        ProductResponse pros = new ProductResponse();
        Product pro = productService.findById(productId);
        pros.setProductId(pro.getProductID());
        pros.setProductName(pro.getProductName());
        pros.setProductAuthor(pro.getProductAuthor());
        pros.setProductPrice(pro.getProductPrice());
        pros.setProductImage(pro.getProductImage());
        pros.setProductDecription(pro.getProductDescription());
        pros.setProductCreateDate(pro.getProductCreateDate());
        pros.setUserName(pro.getUsers().getUserName());
        return pros;

    }
    /////////////////////////////End GetProductById///////////////////////////////////////


///////////////////////////////////////start create product///////////////////////////////////
    @PostMapping("/createProduct")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> saveProduct(@RequestBody ProductRequet product) {
        try {
            CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User users = userService.findByUserId(userDetails.getUserId());
            Exhibition exhibition = exhibitionService.findById(product.getExhibition());
            Product proNew = new Product();
            proNew.setUsers(users);
            proNew.setExhibition(exhibition);
            proNew.setProductName(product.getProductName());
            proNew.setProductAuthor(product.getProductAuthor());
            proNew.setProductPrice(product.getProductPrice());
            proNew.setProductImage(product.getProductImage());
            proNew.setProductDescription(product.getProductDecription());
            proNew.setProductCreateDate(new Date());
            proNew.setProductStatus(true);
            productService.saveOfUpdate(proNew);
            return ResponseEntity.ok("Thêm mới Product thành công😘");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok("đã có lỗi trong quá trình sử lý!!! vui lòng ngủ một giấc rồi quay lại nhe😛");
        }
    }

    ///////////////////////////////////////end create product///////////////////////////////////


////////////////////////////////start updateProduct///////////////////////////////////////

    @PutMapping("/updateProduct/{productId}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> updateProduct(@PathVariable("productId") int productId,@RequestBody ProductRequet product){
        try {
            CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User users = userService.findByUserId(userDetails.getUserId());
            Exhibition exhibition = exhibitionService.findById(product.getExhibition());
            Product proUpdate = productService.findById(productId);
            proUpdate.setUsers(users);
            proUpdate.setExhibition(exhibition);
            proUpdate.setProductName(product.getProductName());
            proUpdate.setProductAuthor(product.getProductAuthor());
            proUpdate.setProductPrice(product.getProductPrice());
            proUpdate.setProductImage(product.getProductImage());
            proUpdate.setProductDescription(product.getProductDecription());
            proUpdate.setProductCreateDate(new Date());
            productService.saveOfUpdate(proUpdate);
            return ResponseEntity.ok("Bạn đã cap nhaật thành công😘");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok("Có lỗi trong quá trình xử lý vui lòng thử lại!");
        }
    }

    ////////////////////////////////end updateProduct///////////////////////////////////////

////////////////////////////start deleteproduct////////////////////////////////////////
@DeleteMapping("/delete/{producId}")
@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
public ResponseEntity<?> deleteproduct(@PathVariable("producId") int productId) {
    try{
        Product product  = productService.findById(productId);
        productService.delete(productId);
        return ResponseEntity.ok("Đã xóa thành công ");
    }catch (Exception e){
        return ResponseEntity.ok("Chưa xóa được kiểm tra lại ");
    }

}

    ////////////////////////////start deleteproduct////////////////////////////////////////

    ////////////////////////////start searchProductName/////////////////////////////////////

    @GetMapping("/search")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Product> searchProduct(@RequestParam("productName") String productName){
        return productService.searchByName(productName);
    }

    ////////////////////////////end searchProductName/////////////////////////////////////


    /////////////////start sortByPrice//////////////////////////
    @GetMapping("/sortByName")
    public ResponseEntity<List<Product>> sortByStudentName(@RequestParam("direction") String direction) {
        List<Product> listProduct = productService.sortByProductPrice(direction);
        return new ResponseEntity<>(listProduct, HttpStatus.OK);
    }
    /////////////////end sortByPrice//////////////////////////


    /////////////////start phantrang//////////////////////////


    @GetMapping("/getPagging")
    public ResponseEntity<Map<String,Object>> getPagging(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page,size);
        Page<Product> pageProduct = productService.pagging(pageable);
        Map<String,Object> data = new HashMap<>();
        data.put("product",pageProduct.getContent());
        data.put("total",pageProduct.getSize());
        data.put("totalItems",pageProduct.getTotalElements());
        data.put("totalPages",pageProduct.getTotalPages());
        return  new ResponseEntity<>(data,HttpStatus.OK);
    }

    /////////////////end phantrang//////////////////////////







}
