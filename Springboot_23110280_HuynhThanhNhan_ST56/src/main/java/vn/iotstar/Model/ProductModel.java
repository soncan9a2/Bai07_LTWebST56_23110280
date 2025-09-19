package vn.iotstar.Model;

import java.util.Date;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductModel {
    private Long productId;

    @NotBlank
    private String productName;

    @Min(0)
    private int quantity;

    @Min(0)
    private double unitPrice;

    private String images;
    private MultipartFile imageFile;

    @NotBlank
    private String description;

    @Min(0)
    private double discount;

    private Date createDate;

    @NotNull
    private Short status;

    @NotNull
    private Long cateId;
}


