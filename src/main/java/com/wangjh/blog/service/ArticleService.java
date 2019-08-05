package com.wangjh.blog.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wangjh.blog.dto.CategoryDTO;
import com.wangjh.blog.dto.PaginationDTO;
import com.wangjh.blog.entity.Article;
import com.wangjh.blog.entity.ArticleExample;
import com.wangjh.blog.entity.User;
import com.wangjh.blog.mapper.ArticleExtMapper;
import com.wangjh.blog.mapper.ArticleMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.*;

@Service
public class ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleExtMapper articleExtMapper;

    /**
     * 添加博客文章
     * @param title     标题
     * @param tags      标签
     * @param content   博客内容
     * @param user
     */
    public void insert(String title, String category, String tags, String type, String content, User user) {
        Article article = new Article();
        article.setAuthor(user.getUsername());
        article.setArticleTitle(title);
        article.setArticleCategories(category);
        article.setArticleTags(tags);
        article.setArticleType(type);
        article.setArticleContent(content);
        if (StringUtils.length(content) >= 200) {
            article.setArticleTabloid(content.substring(0, 200));
        } else {
            article.setArticleTabloid(content);
        }
        article.setPublishDate(System.currentTimeMillis());
        article.setUpdateDate(article.getPublishDate());
        article.setLikes(0);
        articleMapper.insert(article);
    }

    public Article showArticle(Integer id) {
        return articleMapper.selectByPrimaryKey(id);
    }

    public void update(Integer id, String title, String category, String tags, String type, String content) {
        Article article = articleMapper.selectByPrimaryKey(id);
        article.setUpdateDate(System.currentTimeMillis());
        article.setArticleTitle(title);
        article.setArticleCategories(category);
        article.setArticleTags(tags);
        article.setArticleType(type);
        article.setArticleContent(content);
        if (StringUtils.length(content) >= 200) {
            article.setArticleTabloid(content.substring(0, 200));
        } else {
            article.setArticleTabloid(content);
        }
        articleMapper.updateByPrimaryKey(article);
    }

    public Article findById(Integer id) {
        return articleMapper.selectByPrimaryKey(id);
    }

//    public Page<Article> list(Integer page, Integer size) {
//        PageHelper.startPage(page, size);
//        return articleExtMapper.allArticle();
//    }

    /**
     * 文章分页
     * @param page
     * @param size
     * @return
     */
    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();

        /* 总页数 */
        Integer totalPage;
        /* 文章总数 */
        Integer totalCount;

        ArticleExample articleExample = new ArticleExample();
        articleExample.createCriteria().andIdIsNotNull();
        List<Article> articles = articleMapper.selectByExample(articleExample);
        totalCount = articles.size();

        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount/ size + 1;
        }

        // 防止页面页数越界
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage, page);
        ArticleExample example = new ArticleExample();
        example.createCriteria().andIdIsNotNull();
        example.setOrderByClause("publish_date desc");
        List<Article> articleList = articleMapper.selectByExampleWithRowbounds(example, new RowBounds((page - 1) * 5, size));
        paginationDTO.setData(articleList);
        return paginationDTO;
    }

    /**
     * 根据文章 id 删除文章
     * @param articleId
     */
    public void deleteById(Integer articleId) {
        articleMapper.deleteByPrimaryKey(articleId);
    }

    /**
     * 博客的所有分类
     * @return
     */
    public List<CategoryDTO> listCategories() {
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        // 查询所有的博客
        ArticleExample articleExample = new ArticleExample();
        articleExample.createCriteria().andIdIsNotNull();
        List<Article> articles = articleMapper.selectByExample(articleExample);
        // 将不重复的分类存入一个 ArrayLIst 中
        List<String> categories = new ArrayList<>();
        for (Article article : articles) {
            if (!categories.contains(article.getArticleCategories())) {
                categories.add(article.getArticleCategories());
            }
        }
        // 得到一个 CategoryDTO 的 List
        for (int i = 0; i < categories.size(); i++) {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(i);
            categoryDTO.setName(categories.get(i));
            categoryDTOList.add(categoryDTO);
        }
        return categoryDTOList;
    }

    /**
     * 根据 category 查找所有博客文章
     * @param category
     * @return
     */
    public List<Article> listByCategory(String category) {
        ArticleExample articleExample = new ArticleExample();
        articleExample.createCriteria().andArticleCategoriesEqualTo(category);
        List<Article> articles = articleMapper.selectByExample(articleExample);
        return articles;
    }
}