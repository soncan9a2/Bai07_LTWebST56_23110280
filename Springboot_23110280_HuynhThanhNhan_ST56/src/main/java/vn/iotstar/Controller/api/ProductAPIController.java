package vn.iotstar.Controller.api;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import vn.iotstar.Entity.CategoryEntity;
import vn.iotstar.Entity.ProductEntity;
import vn.iotstar.Model.Response;
import vn.iotstar.Service.ICategoryService;
import vn.iotstar.Service.IFileUploadService;
import vn.iotstar.Service.IProductService;

@RestController
@RequestMapping(path = "/api/product")
public class ProductAPIController {

    @Autowired
    IProductService productService;
    @Autowired
    ICategoryService categoryService;
    @Autowired
    IFileUploadService fileUploadService;

    @GetMapping
    public ResponseEntity<?> getAllProduct() {
        return new ResponseEntity<Response>(new Response(true, "Thành công", productService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getOne(@RequestParam("id") Long id) {
        return productService.findById(id)
            .<ResponseEntity<?>>map(p -> new ResponseEntity<Response>(new Response(true, "Thành công", p), HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<Response>(new Response(false, "Không tìm thấy Product", null), HttpStatus.NOT_FOUND));
    }

    @PostMapping(path = "/addProduct")
    public ResponseEntity<?> addProduct(@Validated @RequestParam("productName") String productName,
                                        @RequestParam(value = "imageFile", required = false) MultipartFile productImages,
                                        @Validated @RequestParam("unitPrice") Double productPrice,
                                        @Validated @RequestParam("discount") Double promotionalPrice,
                                        @Validated @RequestParam("description") String productDescription,
                                        @Validated @RequestParam("categoryId") Long categoryId,
                                        @Validated @RequestParam("quantity") Integer quantity,
                                        @Validated @RequestParam("status") Short status) {
        Optional<CategoryEntity> categoryOpt = categoryService.findById(categoryId);
        if (categoryOpt.isEmpty()) {
            return new ResponseEntity<Response>(new Response(false, "Category không tồn tại", null), HttpStatus.BAD_REQUEST);
        }
        ProductEntity product = new ProductEntity();
        product.setProductName(productName);
        product.setUnitPrice(productPrice);
        product.setDiscount(promotionalPrice);
        product.setDescription(productDescription);
        product.setQuantity(quantity);
        product.setStatus(status);

        product.setCategory(categoryOpt.get());

        if (productImages != null && !productImages.isEmpty()) {
            String filename = fileUploadService.uploadFile(productImages, "uploads/");
            product.setImages(filename);
        }

        Timestamp timestamp = new Timestamp(new Date(System.currentTimeMillis()).getTime());
        product.setCreateDate(timestamp);
        productService.save(product);
        return new ResponseEntity<Response>(new Response(true, "Thành công", product), HttpStatus.OK);
    }

    @RequestMapping(path = "/updateProduct", method = { RequestMethod.PUT, RequestMethod.POST })
    public ResponseEntity<?> updateProduct(@Validated @RequestParam("productId") Long productId,
                                           @Validated @RequestParam("productName") String productName,
                                           @RequestParam(value = "imageFile", required = false) MultipartFile productImages,
                                           @Validated @RequestParam("unitPrice") Double productPrice,
                                           @Validated @RequestParam("discount") Double promotionalPrice,
                                           @Validated @RequestParam("description") String productDescription,
                                           @Validated @RequestParam("categoryId") Long categoryId,
                                           @Validated @RequestParam("quantity") Integer quantity,
                                           @Validated @RequestParam("status") Short status) {

        Optional<ProductEntity> opt = productService.findById(productId);
        if (opt.isEmpty()) {
            return new ResponseEntity<Response>(new Response(false, "Không tìm thấy Product", null), HttpStatus.BAD_REQUEST);
        }

        ProductEntity entity = opt.get();
        entity.setProductName(productName);
        entity.setUnitPrice(productPrice);
        entity.setDiscount(promotionalPrice);
        entity.setDescription(productDescription);
        entity.setQuantity(quantity);
        entity.setStatus(status);

        Optional<CategoryEntity> categoryOpt = categoryService.findById(categoryId);
        if (categoryOpt.isEmpty()) {
            return new ResponseEntity<Response>(new Response(false, "Category không tồn tại", null), HttpStatus.BAD_REQUEST);
        }
        entity.setCategory(categoryOpt.get());

        if (productImages != null && !productImages.isEmpty()) {
            String filename = fileUploadService.uploadFile(productImages, "uploads/");
            entity.setImages(filename);
        }

        productService.save(entity);
        return new ResponseEntity<Response>(new Response(true, "Cập nhật Thành công", entity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/deleteProduct")
    public ResponseEntity<?> deleteProduct(@Validated @RequestParam("productId") Long productId) {
        Optional<ProductEntity> opt = productService.findById(productId);
        if (opt.isEmpty()) {
            return new ResponseEntity<Response>(new Response(false, "Không tìm thấy Product", null), HttpStatus.BAD_REQUEST);
        }
        productService.delete(opt.get());
        return new ResponseEntity<Response>(new Response(true, "Xóa Thành công", opt.get()), HttpStatus.OK);
    }
}


