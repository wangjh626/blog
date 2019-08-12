package com.wangjh.blog.service;

import com.wangjh.blog.dto.TagDTO;
import com.wangjh.blog.entity.Article;
import com.wangjh.blog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TagService {

    @Autowired
    private ArticleService articleService;

    public Set<String> allTag(User user) {
        // 根据用户查询该用户的所有文章
        List<Article> allArticles = new ArrayList<>();
        if (user != null) {
            // 如果用户不为空，则查询该用户的所有文章
            allArticles = articleService.findAllByUser(user.getUsername());
        } else {
            // 否则，查询所有用户的文章
            allArticles = articleService.findAll();
        }
        // 得到所有文章的标签, 并存入 set 中
        Set<String> allTag = new HashSet<>();
        TagDTO tagDTO = new TagDTO();
        for (Article article : allArticles) {
            tagDTO.setTags(article.getArticleTags().split("，"));
            String[] tags = tagDTO.getTags();
            for (String s : tags) {
                if (!allTag.contains(s)) {
                    allTag.add(s);
                }
            }
        }
        return allTag;
    }
}
