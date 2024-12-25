package com.controller;

import com.model.Blog;
import com.model.BlogForm;
import com.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private IBlogService blogService;

    @GetMapping
    public String listBlogs(Model model) {
        List<Blog> blogs = blogService.findAll();
        model.addAttribute("blogs", blogs);
        return "list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("blogForm", new BlogForm());
        return "create";
    }

    @Value("${file-upload}")
    private String folderPath;

    @PostMapping("/save")
    public String saveBlog(BlogForm blogForm, RedirectAttributes redirectAttributes) {
        MultipartFile multipartFile = blogForm.getImageFile();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(blogForm.getImageFile().getBytes(), new File(folderPath + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        Blog blog = new Blog(blogForm.getId(), blogForm.getTitle(), blogForm.getContent(), blogForm.getAuthor(),
                fileName, LocalDateTime.now());
        blogService.save(blog);

        redirectAttributes.addFlashAttribute("message", "New blog added successfully");
        return "redirect:/blog";
    }

    @GetMapping("/{id}/view")
    public String showView(@PathVariable Long id, Model model) {
        Blog blog = blogService.findById(id);
        model.addAttribute("blog", blog);
        return "view";
    }

    @GetMapping("/{id}/update")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Blog blog = blogService.findById(id);
        model.addAttribute("blog", blog);
        return "update";
    }
}
