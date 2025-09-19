package vn.iotstar.Controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/products")
public class ProductController {

    @GetMapping("/list")
    public String list() {
        return "admin/products/list";
    }
}


