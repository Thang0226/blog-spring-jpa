package com.repository;

import com.model.Blog;
import com.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IBlogRepository extends PagingAndSortingRepository<Blog, Long> {
    Page<Blog> findByTitleContaining(Pageable pageable, String searchText);

    Page<Blog> findByCategory(Pageable pageable, Category category);

    Iterable<Blog> findByCategory(Category category);
}
