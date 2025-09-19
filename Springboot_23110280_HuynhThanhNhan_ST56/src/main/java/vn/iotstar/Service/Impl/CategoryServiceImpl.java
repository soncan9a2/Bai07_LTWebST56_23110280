package vn.iotstar.Service.Impl;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import vn.iotstar.Entity.CategoryEntity;
import vn.iotstar.Repository.CategoryRepository;
import vn.iotstar.Service.ICategoryService;


@Service
public class CategoryServiceImpl implements ICategoryService {
	
	private final CategoryRepository categoryRepository;
	
	@Autowired
	public CategoryServiceImpl(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	public <S extends CategoryEntity> S save(S entity) {
		if (entity.getCateId() == null) {
			return categoryRepository.save(entity);
		}else {
			Optional <CategoryEntity> opt = findById(entity.getCateId());
			if(opt.isPresent()) {
				if (StringUtils.isEmpty(entity.getCateName())) {
					entity.setCateName(opt.get().getCateName());
				}
				if (StringUtils.isEmpty(entity.getIcons())) {
					entity.setIcons(opt.get().getIcons());
				}
			}
		}
		return categoryRepository.save(entity);
	}

	@Override
	public Optional<CategoryEntity> findById(Long id) {
		return categoryRepository.findById(id);
	}

	@Override
	public List<CategoryEntity> findAll(){
		return categoryRepository.findAll();
	}

	@Override
	public Page<CategoryEntity> findAll(Pageable pageable) {
		return categoryRepository.findAll(pageable);
	}

	@Override
	public List<CategoryEntity> findAll(Sort sort) {
		return categoryRepository.findAll(sort);
	}

	@Override
	public List<CategoryEntity> findAllById(Iterable<Long> ids) {
		return categoryRepository.findAllById(ids);
	}

	@Override
	public List<CategoryEntity> findByCateNameContaining(String cateName) {
		return categoryRepository.findByCateNameContaining(cateName);
	}

	@Override
	public Page<CategoryEntity> findByCateNameContaining(String cateName, Pageable pageable) {
		return categoryRepository.findByCateNameContaining(cateName, pageable);
	}

	@Override
	public List<CategoryEntity> findByStatus(Integer status) {
		return categoryRepository.findByStatus(status);
	}

	@Override
	public Page<CategoryEntity> findByStatus(Integer status, Pageable pageable) {
		return categoryRepository.findByStatus(status, pageable);
	}




	@Override
	public <S extends CategoryEntity> Optional<S> findOne(Example<S> example) {
		return categoryRepository.findOne(example);
	}



	@Override
	public long count() {
		return categoryRepository.count();
	}



	@Override
	public void deleteById(Long id) {
		categoryRepository.deleteById(id);
	}



	@Override
	public void delete(CategoryEntity entity) {
		categoryRepository.delete(entity);
	}



	@Override
	public void deleteAll() {
		categoryRepository.deleteAll();
	}
	
	
	
	
	
	
	
	
}
