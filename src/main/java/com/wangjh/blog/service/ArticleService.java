package com.wangjh.blog.service;

import com.wangjh.blog.entity.Article;
import com.wangjh.blog.entity.User;
import com.wangjh.blog.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 添加博客文章
     * @param title     标题
     * @param tags      标签
     * @param content   博客内容
     * @param user
     */
    public void insert(String title, String tags, String content, String category, User user) {
        Article article = new Article();
        article.setAuthor(user.getUsername());
        article.setArticleTitle(title);
        article.setArticleContent(content);
        article.setArticleTags(tags);
        article.setArticleCategories(category);
        article.setPublishDate(String.valueOf(new Date()));
        article.setUpdateDate(String.valueOf(new Date()));
        article.setLikes(0);
        articleMapper.insert(article);
    }
}
