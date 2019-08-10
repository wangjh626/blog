package com.wangjh.blog.dto;

import com.wangjh.blog.entity.Article;
import lombok.Data;


@Data
public class TagDTO {
    private String[] tags;

    public String[] getArticleTags(Article article) {
        return article.getArticleTags().split("，");
    }
}
