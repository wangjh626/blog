package com.wangjh.blog.controller;

import com.wangjh.blog.dto.PaginationDTO;
import com.wangjh.blog.entity.User;
import com.wangjh.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class TableController {

    @Autowired
    private ArticleService articleService;

    /**
     * 在控制台显示某个用户的所有文章
     * @param model
     * @param page
     * @param size
     * @param request
     * @return
     */
    @GetMapping("/admin/articleTable")
    public String table(Model model, @RequestParam(value = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "15") Integer size, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        PaginationDTO paginationDTO = articleService.listByUsername(user.getUsername(),page, size);
        if (paginationDTO != null) {
            model.addAttribute("paginationDTO", paginationDTO);
            List articles = paginationDTO.getData();
            model.addAttribute("articles", articles);
            return "article-table";
        } else {
            // 如果该用户没有写过博
            return "admin-404";
        }
    }
}
