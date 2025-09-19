package vn.iotstar.Entity;
import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Categories")
public class CategoryEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cate_id")
	private Long cateId;
	
	@Column(name = "cate_name", length = 200, columnDefinition = "nvarchar(200) not null")
	private String cateName;
	
	@Column(name = "icons", length = 500)
	private String icons;
	
	@Column(name = "status")
	private Integer status = 1;
}
