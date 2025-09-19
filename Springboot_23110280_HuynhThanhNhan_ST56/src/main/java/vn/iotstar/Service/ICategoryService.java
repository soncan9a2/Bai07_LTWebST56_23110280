package vn.iotstar.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import vn.iotstar.Entity.CategoryEntity;

public interface ICategoryService {
	List<CategoryEntity> findAll();

	Optional<CategoryEntity> findById(Long id);

	<S extends CategoryEntity> S save(S entity);

	void deleteAll();

	void delete(CategoryEntity entity);

	void deleteById(Long id);

	long count();

	<S extends CategoryEntity> Optional<S> findOne(Example<S> example);

	Page<CategoryEntity> findByCateNameContaining(String cateName, Pageable pageable);

	List<CategoryEntity> findByCateNameContaining(String cateName);

	List<CategoryEntity> findAllById(Iterable<Long> ids);

	List<CategoryEntity> findAll(Sort sort);
	Page<CategoryEntity> findAll(Pageable pageable);
	List<CategoryEntity> findByStatus(Integer status);
	Page<CategoryEntity> findByStatus(Integer status, Pageable pageable);
}
