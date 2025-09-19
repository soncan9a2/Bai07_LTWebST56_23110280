package vn.iotstar.Controller.admin;

import java.util.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import jakarta.validation.Valid;
import vn.iotstar.Entity.CategoryEntity;
import vn.iotstar.Model.CategoryModel;
import vn.iotstar.Service.ICategoryService;
import vn.iotstar.Service.IFileUploadService;

@Controller
@RequestMapping("admin/categories")
public class CategoryController {
	@Autowired 
	ICategoryService categoryService;
	
	@Autowired
	IFileUploadService fileUploadService;
	
	@Autowired
	MessageSource messageSource;
	
	@Value("${upload.path}")
	private String uploadPath;

	@GetMapping("add")
	public String add(ModelMap model) {
		CategoryModel cateModel = new CategoryModel();
		cateModel.setIsEdit(false);
		model.addAttribute("category", cateModel);
		return "admin/categories/addOrEdit";
	}

	@RequestMapping("list")
	public String list(ModelMap model) {
		List<CategoryEntity> list = categoryService.findAll();
		model.addAttribute("categories", list);
		return "admin/categories/list";
	}

	@GetMapping("edit/{cateId}")
	public ModelAndView edit(ModelMap model, @PathVariable("cateId") Long id) {
		Optional<CategoryEntity> opt = categoryService.findById(id);
		CategoryModel cateModel = new CategoryModel();
		if (opt.isPresent()) {
			BeanUtils.copyProperties(opt.get(), cateModel);
			cateModel.setIsEdit(true);
			model.addAttribute("category", cateModel);
			return new ModelAndView("admin/categories/addOrEdit", model);
		}
		model.addAttribute("message", messageSource.getMessage("iotstar.message.not.found", null, LocaleContextHolder.getLocale()));
		return new ModelAndView("forward:/admin/categories", model);
	}

	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model,
			@Valid @ModelAttribute("category") CategoryModel cateModel,
			BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("admin/categories/addOrEdit");
		}
		
		CategoryEntity entity = new CategoryEntity();
		BeanUtils.copyProperties(cateModel, entity);
		
		// Xử lý upload file icon
		if (cateModel.getIconFile() != null && !cateModel.getIconFile().isEmpty()) {
			String fileName = fileUploadService.uploadFile(cateModel.getIconFile(), uploadPath);
			if (fileName != null) {
				entity.setIcons(fileName);
			}
		}
		
		// Set default values
		if (entity.getStatus() == null) {
			entity.setStatus(1); // Active by default
		}
		
		categoryService.save(entity);
		String messageKey = cateModel.getIsEdit() ? "iotstar.message.edit.success" : "iotstar.message.success";
		String message = messageSource.getMessage(messageKey, null, LocaleContextHolder.getLocale());
		model.addAttribute("message", message);
		return new ModelAndView("forward:/admin/categories/searchpaginated", model);
	}

	@GetMapping("delete/{cateId}")
	public ModelAndView delete(ModelMap model, @PathVariable("cateId") Long id) {
		categoryService.deleteById(id);
		model.addAttribute("message", messageSource.getMessage("iotstar.message.delete.success", null, LocaleContextHolder.getLocale()));
		return new ModelAndView("forward:/admin/categories/searchpaginated", model);
	}

	@GetMapping("search")
	public String search(ModelMap model, @RequestParam(name="cateName", required=false) String cateName) {
		List<CategoryEntity> list = StringUtils.hasText(cateName) ?
			categoryService.findByCateNameContaining(cateName) : categoryService.findAll();
		model.addAttribute("categories", list);
		return "admin/categories/search";
	}

	@RequestMapping("searchpaginated")
	public String search(ModelMap model,
			@RequestParam(name="cateName", required=false) String cateName,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(3);
		Pageable pageable = PageRequest.of(currentPage-1, pageSize, Sort.by("cateName"));
		Page<CategoryEntity> resultPage = StringUtils.hasText(cateName) ?
			categoryService.findByCateNameContaining(cateName, pageable) : categoryService.findAll(pageable);
		if (StringUtils.hasText(cateName)) {
			model.addAttribute("cateName", cateName);
		}

		int totalPages = resultPage.getTotalPages();
		if (totalPages > 0) {
			int start = Math.max(1, currentPage-2);
			int end = Math.min(currentPage+2, totalPages);
			if (totalPages > 5) {
				if (end == totalPages) start = end - 5; 
				else if (start == 1) end = start + 5;
			}
			List<Integer> pageNumbers = java.util.stream.IntStream.rangeClosed(start, end)
				.boxed().toList();
			model.addAttribute("pageNumbers", pageNumbers);
		}
		model.addAttribute("categoryPage", resultPage);
		return "admin/categories/searchpaginated";
	}
}
