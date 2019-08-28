package com.wangjh.blog.controller;

import com.wangjh.blog.dto.*;
import com.wangjh.blog.entity.Article;
import com.wangjh.blog.entity.User;
import com.wangjh.blog.service.ArticleService;
import com.wangjh.blog.service.CommentService;
import com.wangjh.blog.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class ArticleController {

    private List<CategoryDTO> categories;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private MessageService messageService;

    /**
     * 获取一篇博客下的所有评论，以及评论底下的回复
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/article/{id}")
    public String artilce(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
        // 根据博客 id 获取某一篇博客
        Article article = articleService.findById(id);
        model.addAttribute("article", article);
        // 获取某篇博客下的所有评论
        List<CommentDTO> comments = commentService.listComments(id);
        model.addAttribute("comments", comments);
        // 获取评论下的所有回复
        List<CommentDTO> replies = commentService.listReplies(id);
        model.addAttribute("replies", replies);
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            int messageCount = messageService.messageCount(user.getId());
            request.getSession().setAttribute("messageCount", messageCount);
        }
        return "article";
    }

    /**
     * 导航栏 --- 分类
     * @param page
     * @param size
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/categories")
    public String categories(@RequestParam(name = "page", defaultValue = "1") Integer page,
                             @RequestParam(name = "size", defaultValue = "10") Integer size,
                             Model model, HttpServletRequest request) {
        // 所有分类
        categories = articleService.listCategories();
        model.addAttribute("categories", categories);

        Object id = request.getSession().getAttribute("id");
        request.getSession().removeAttribute("id");
        String category = null;
        if (id != null) {
            CategoryDTO categoryDTO = categories.get((Integer) id);
            category = categoryDTO.getName();
        }
        // 所有文章进行分页
        PaginationDTO paginationDTO;
        if (StringUtils.isEmpty(category)) {
            paginationDTO = articleService.list(page, size, "");
        } else {
            paginationDTO = articleService.list(page, size, category);
        }
        model.addAttribute("paginationDTO", paginationDTO);
        // 文章标签
        TagDTO tagDTO = new TagDTO();
        model.addAttribute("tagDTO", tagDTO);
        return "categories";
    }

    /**
     * 导航栏 --- 分类（根据分类展示博客文章）
     * @param id
     * @param request
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/categories/{id}")
    public String category(@PathVariable("id") Integer id, HttpServletRequest request,
                           @RequestParam(name = "page", defaultValue = "1") Integer page,
                           @RequestParam(name = "size", defaultValue = "10") Integer size) {
        request.getSession().setAttribute("id", id);
        return "forward:/categories";
    }

    /**
     * 在控制台根据文章 id 删除文章
     *
     * @param articleId
     * @return
     */
    @GetMapping("/admin/delete/{id}")
    public String deleteArticle(@PathVariable("id") Long articleId) {
        articleService.deleteById(articleId);
        // 删除文章的同时也要删除该文章下的所有评论
        commentService.deleteAllComments(articleId);
        return "redirect:/admin/articleTable";
    }

    /**
     * 在控制台查询某个用户博客的所有分类
     *
     * @param model
     * @return
     */
    @GetMapping("/admin/category")
    public String category(Model model, HttpServletRequest request) {
        // 从 Session 中获取用户
        User user = (User) request.getSession().getAttribute("user");
        // 根据用户查询博客的所有分类
        categories = articleService.listCategories(user.getUsername());
        model.addAttribute("categories", categories);
        return "article-table-category";
    }

    /**
     * 在控制台展示某个分类下的所有博客文章
     *
     * @return
     */
    @GetMapping("/admin/category/{id}")
    public ModelAndView list(@PathVariable("id") Integer id,
                             @RequestParam(name = "page", defaultValue = "1") Integer page,
                             @RequestParam(name = "size", defaultValue = "15") Integer size) {
        ModelAndView modelAndView = new ModelAndView("article-table-category-list");
        // 根据 id 获取一个分类下的所有博客文章
        PaginationDTO<Article> paginationDTO = articleService.paginationByCategory(categories.get(id).getName(), page, size);
        modelAndView.addObject("paginationDTO", paginationDTO);
        return modelAndView;
    }

    /**
     * 文章点赞
     * @param commentDTO
     * @return
     */
    @PostMapping("/like")
    @ResponseBody
    public Object like(@RequestBody CommentDTO commentDTO, HttpServletRequest request, HttpServletResponse response) {
        Object user = request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf();
        }
        // 查询被点赞文章
        Article article = articleService.findById(commentDTO.getArticleId());
        // 文章 likes 数量加一
        article.setLikes(article.getLikes() + 1);
        // 更新文章
        articleService.update(article);

        return ResultDTO.successOf();
    }
}
