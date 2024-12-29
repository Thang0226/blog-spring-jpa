package com.controller.RESTful;

import com.model.Blog;
import com.model.BlogDTO;
import com.model.Category;
import com.service.IBlogService;
import com.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/blogs")
public class RESTBlogController {
    @Autowired
    private IBlogService blogService;
    @Autowired
    private ICategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<BlogDTO>> getAllBlogs() {
        Iterable<Blog> blogsIterable = blogService.findAll();
        List<Blog> blogs = (List<Blog>) blogsIterable;
        return new ResponseEntity<>(generateBlogDTOs(blogs), HttpStatus.OK);
    }

    @GetMapping("/category/{cat_name}")
    public ResponseEntity<Iterable<BlogDTO>> getBlogsByCategory(@PathVariable String cat_name) {
        Iterable<Category> categories = categoryService.findAll();
        Category category = null;
        for (Category cat : categories) {
            if (cat.getName().equalsIgnoreCase(cat_name)) {
                category = cat;
                break;
            }
        }
        if (category == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Iterable<Blog> blogsIterable = blogService.findByCategory(category);
        List<Blog> blogs = (List<Blog>) blogsIterable;
        return new ResponseEntity<>(generateBlogDTOs(blogs), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogDTO> getBlogById(@PathVariable Long id) {
        Optional<Blog> blogOptional = blogService.findById(id);
        if (!blogOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        Blog blog = blogOptional.get();
        BlogDTO blogDTO = new BlogDTO();
        blogDTO.setId(id);
        blogDTO.setTitle(blog.getTitle());
        blogDTO.setContent(blog.getContent());
        blogDTO.setAuthor(blog.getAuthor());
        blogDTO.setImageFile(blog.getImageFile());
        blogDTO.setTime(blog.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        blogDTO.setCategory(blog.getCategory().getName());
        return new ResponseEntity<>(blogDTO, HttpStatus.OK);
    }

    private List<BlogDTO> generateBlogDTOs(List<Blog> blogs) {
        return blogs.stream().map(blog -> new BlogDTO(blog.getId(), blog.getTitle(), blog.getContent(),
                blog.getAuthor(), blog.getImageFile(), blog.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                blog.getCategory() != null ? blog.getCategory().getName() : null)).collect(Collectors.toList());
    }
}
