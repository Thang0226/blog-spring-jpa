package com.controller.RESTful;

import com.model.Blog;
import com.model.BlogDTO;
import com.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping
    public List<BlogDTO> getAllBlogs() {
        Iterable<Blog> blogsIterable = blogService.findAll();
        List<Blog> blogs = (List<Blog>) blogsIterable;
        return blogs.stream()
                .map(blog -> new BlogDTO(
                        blog.getId(),
                        blog.getTitle(),
                        blog.getContent(),
                        blog.getAuthor(),
                        blog.getImageFile(),
                        blog.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        blog.getCategory() != null ? blog.getCategory().getName() : null
                ))
                .collect(Collectors.toList());
    }
}
