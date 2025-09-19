package vn.iotstar.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryModel {
    private Long cateId;
    
    @NotBlank(message = "Category name is required")
    @Size(max = 200, message = "Category name must be less than 200 characters")
    private String cateName;
    
    private String icons;
    private MultipartFile iconFile;
    
    private Integer status = 1;
    
    private Boolean isEdit = false;
}
