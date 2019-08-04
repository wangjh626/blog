package com.wangjh.blog.mapper;

import com.github.pagehelper.Page;
import com.wangjh.blog.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ArticleExtMapper {

    @Select("select * from article order by publish_date desc")
    Page<Article> allArticle();
}
