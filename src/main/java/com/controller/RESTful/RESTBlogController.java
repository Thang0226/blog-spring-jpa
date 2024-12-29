package com.controller.RESTful;

import com.model.Blog;
import com.model.BlogDTO;
import com.model.Category;
import com.service.IBlogService;
import com.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/blogs")
public class RESTBlogController {
    @Autowired
    private IBlogService blogService;
    @Autowired
    private ICategoryService categoryService;

    @GetMapping
    public List<BlogDTO> getAllBlogs() {
        Iterable<Blog> blogsIterable = blogService.findAll();
        List<Blog> blogs = (List<Blog>) blogsIterable;
        return generateBlogDTOs(blogs);
    }

    @GetMapping("/category/{cat_name}")
    public List<BlogDTO> getBlogsByCategory(@PathVariable String cat_name) {
        Iterable<Category> categories = categoryService.findAll();
        Category category = null;
        for (Category cat : categories) {
            if (cat.getName().equalsIgnoreCase(cat_name)) {
                category = cat;
                break;
            }
        }
        if (category == null) {
            return null;
        }
        Iterable<Blog> blogsIterable = blogService.findByCategory(category);
        List<Blog> blogs = (List<Blog>) blogsIterable;
        return generateBlogDTOs(blogs);
    }

    private List<BlogDTO> generateBlogDTOs(List<Blog> blogs) {
        return blogs.stream().map(blog -> new BlogDTO(blog.getId(), blog.getTitle(), blog.getContent(),
                blog.getAuthor(), blog.getImageFile(), blog.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                blog.getCategory() != null ? blog.getCategory().getName() : null)).collect(Collectors.toList());
    }
}
