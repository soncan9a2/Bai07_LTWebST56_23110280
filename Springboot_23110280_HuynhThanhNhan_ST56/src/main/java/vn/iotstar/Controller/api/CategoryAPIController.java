package vn.iotstar.Controller.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import vn.iotstar.Entity.CategoryEntity;
import vn.iotstar.Model.Response;
import vn.iotstar.Service.ICategoryService;
import vn.iotstar.Service.IFileUploadService;

@RestController
@RequestMapping(path = "/api/category")
public class CategoryAPIController {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    IFileUploadService fileUploadService;

    @GetMapping
    public ResponseEntity<?> getAllCategory() {
        return new ResponseEntity<Response>(new Response(true, "Thành công", categoryService.findAll()), HttpStatus.OK);
    }

    @PostMapping(path = "/getCategory")
    public ResponseEntity<?> getCategory(@Validated @RequestParam("id") Long id) {
        Optional<CategoryEntity> category = categoryService.findById(id);
        if (category.isPresent()) {
            return new ResponseEntity<Response>(new Response(true, "Thành công", category.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<Response>(new Response(false, "Thất bại", null), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/addCategory")
    public ResponseEntity<?> addCategory(@Validated @RequestParam("categoryName") String categoryName,
                                         @RequestParam(value = "icon", required = false) MultipartFile icon) {
        CategoryEntity category = new CategoryEntity();
        category.setCateName(categoryName);
        if (icon != null && !icon.isEmpty()) {
            String filename = fileUploadService.uploadFile(icon, "uploads/");
            category.setIcons(filename);
        }
        categoryService.save(category);
        return new ResponseEntity<Response>(new Response(true, "Thêm Thành công", category), HttpStatus.OK);
    }

    @PutMapping(path = "/updateCategory")
    public ResponseEntity<?> updateCategory(@Validated @RequestParam("categoryId") Long categoryId,
                                            @Validated @RequestParam("categoryName") String categoryName,
                                            @RequestParam(value = "icon", required = false) MultipartFile icon) {
        Optional<CategoryEntity> optCategory = categoryService.findById(categoryId);
        if (optCategory.isEmpty()) {
            return new ResponseEntity<Response>(new Response(false, "Không tìm thấy Category", null), HttpStatus.BAD_REQUEST);
        } else {
            CategoryEntity entity = optCategory.get();
            entity.setCateName(categoryName);
            if (icon != null && !icon.isEmpty()) {
                String filename = fileUploadService.uploadFile(icon, "uploads/");
                entity.setIcons(filename);
            }
            categoryService.save(entity);
            return new ResponseEntity<Response>(new Response(true, "Cập nhật Thành công", entity), HttpStatus.OK);
        }
    }

    @DeleteMapping(path = "/deleteCategory")
    public ResponseEntity<?> deleteCategory(@Validated @RequestParam("categoryId") Long categoryId) {
        Optional<CategoryEntity> optCategory = categoryService.findById(categoryId);
        if (optCategory.isEmpty()) {
            return new ResponseEntity<Response>(new Response(false, "Không tìm thấy Category", null), HttpStatus.BAD_REQUEST);
        } else {
            categoryService.delete(optCategory.get());
            return new ResponseEntity<Response>(new Response(true, "Xóa Thành công", optCategory.get()), HttpStatus.OK);
        }
    }
}


