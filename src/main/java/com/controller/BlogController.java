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
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/blogs")
public class BlogController {
    @Autowired
    private IBlogService blogService;

    @GetMapping
    public String listBlogs(Model model) {
        Iterable<Blog> blogs = blogService.findAll();
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
        return "redirect:/blogs";
    }

    @GetMapping("/{id}/view")
    public String showView(@PathVariable Long id, Model model) {
        Optional<Blog> blog = blogService.findById(id);
        model.addAttribute("blog", blog.get());
        return "view";
    }

    @GetMapping("/{id}/update")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Optional<Blog> blogOptional = blogService.findById(id);
        Blog blog = blogOptional.get();
        model.addAttribute("image", blog.getImageFile());
        BlogForm blogForm = new BlogForm(id, blog.getTitle(), blog.getContent(), blog.getAuthor(),
                null, blog.getTime().toString());
        model.addAttribute("blogForm", blogForm);
        return "update";
    }

    @PostMapping("/update")
    public String updateBlog(BlogForm blogForm, RedirectAttributes redirectAttributes) {
        Optional<Blog> blogOptional = blogService.findById(blogForm.getId());
        Blog blog = blogOptional.get();
        MultipartFile multipartFile = blogForm.getImageFile();
        if (!Objects.requireNonNull(multipartFile.getOriginalFilename()).isEmpty()) {
            String fileName = multipartFile.getOriginalFilename();
            try {
                FileCopyUtils.copy(blogForm.getImageFile().getBytes(), new File(folderPath + fileName));
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println(ex.getMessage());
            }
            blog.setImageFile(fileName);
        }
        blog.setTitle(blogForm.getTitle());
        blog.setContent(blogForm.getContent());
        blog.setAuthor(blogForm.getAuthor());
        blog.setTime(LocalDateTime.now());
        blogService.save(blog);

        redirectAttributes.addFlashAttribute("message", "Blog updated successfully");
        return "redirect:/blogs";
    }

    @GetMapping("/{id}/delete")
    public String deleteBlog(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        blogService.remove(id);
        redirectAttributes.addFlashAttribute("message", "Blog deleted");
        return "redirect:/blogs";
    }
}
