package com.wangjh.blog.service;


import com.wangjh.blog.dto.CategoryDTO;
import com.wangjh.blog.dto.PaginationDTO;
import com.wangjh.blog.entity.Article;
import com.wangjh.blog.entity.ArticleExample;
import com.wangjh.blog.entity.User;
import com.wangjh.blog.mapper.ArticleMapper;
import com.wangjh.blog.util.RedisUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 添加文章或者更新文章时设置文章的属性
     *
     * @param article
     * @param title
     * @param category
     * @param tags
     * @param type
     * @param content
     */
    public void setArticleAttributes(Article article, String title, String category, String tags, String type, String content) {
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
    }

    /**
     * 根据文章总数计算总页数
     *
     * @param size
     * @param totalCount
     * @return
     */
    public Integer totalPage(Integer size, Integer totalCount) {
        /* 文章总数 */
        Integer totalPage;
        // 根据文章总数计算总页数
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        return totalPage;
    }

    /**
     * 设置页数，防止页数越界
     *
     * @param totalPage
     * @return
     */
    public Integer setPage(Integer page, Integer totalPage) {
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        return page;
    }

    /**
     * 添加博客文章
     *
     * @param title   标题
     * @param tags    标签
     * @param content 博客内容
     * @param user
     */
    public void insert(String title, String category, String tags, String type, String content, User user) {
        Article article = new Article();
        article.setAuthor(user.getUsername());
        setArticleAttributes(article, title, category, tags, type, content);
        article.setPublishDate(System.currentTimeMillis());
        article.setUpdateDate(article.getPublishDate());
        article.setComments(0);
        article.setLikes(0);
        articleMapper.insert(article);
    }

    /**
     * 更新文章
     *
     * @param id
     * @param title
     * @param category
     * @param tags
     * @param type
     * @param content
     */
    public void update(Long id, String title, String category, String tags, String type, String content) {
        Article article = articleMapper.selectByPrimaryKey(id);
        article.setUpdateDate(System.currentTimeMillis());
        setArticleAttributes(article, title, category, tags, type, content);
        articleMapper.updateByPrimaryKey(article);
    }

    /**
     * 根据 id 查询博客
     *
     * @param id
     * @return
     */
    public Article findById(Long id) {
        return articleMapper.selectByPrimaryKey(id);
    }

    /**
     * 文章分页
     *
     * @param page
     * @param size
     * @return
     */
    public PaginationDTO list(Integer page, Integer size, String category) {
        PaginationDTO paginationDTO = new PaginationDTO();
        List<Article> articles = new ArrayList<>();

        /* 总页数 */
        Integer totalPage;

        // 获取所有文章
        List<Article> redisAllArticles = redisUtil.getAllArticlesOrderBy("");
        if (redisAllArticles.isEmpty()) {
            ArticleExample articleExample = new ArticleExample();
            articleExample.createCriteria().andIdIsNotNull();
            articleExample.setOrderByClause("publish_date desc");
            articles = articleMapper.selectByExample(articleExample);
            redisUtil.setListObject("allArticles", articles, 2L, TimeUnit.HOURS);
        } else {
            if (StringUtils.isEmpty(category)) {
                articles = new ArrayList<>(redisAllArticles);
            } else {
                for (Article article : redisAllArticles) {
                    if (StringUtils.equals(article.getArticleCategories(), category)) {
                        articles.add(article);
                    }
                }
            }
        }

        // 根据文章总数计算总页数
        totalPage = totalPage(size, articles.size());

        // 防止页面页数越界
        page = setPage(page, totalPage);

        // 根据总页数和当前页数计算当前页面需要显示的页数
        paginationDTO.setPagination(totalPage, page);
        // 将所有文章按时间倒序排序
        List<Article> articleList;
        if (redisAllArticles.isEmpty()) {
            articleList = articles.subList((page - 1) * size, page * size);
        } else {
            if (StringUtils.isEmpty(category)) {
                if (redisAllArticles.size() < page * size) {
                    articleList = redisAllArticles.subList((page - 1) * size, redisAllArticles.size());
                } else {
                    articleList = redisAllArticles.subList((page - 1) * size, page * size);
                }
            } else {
                if (articles.size() < page * size) {
                    articleList = articles.subList((page - 1) * size, articles.size());
                } else {
                    articleList = articles.subList((page - 1) * size, page * size);
                }
            }
        }
        for (Article article : articleList) {
            String[] strings = article.getArticleTags().split(",");
        }
        paginationDTO.setData(articleList);
        return paginationDTO;
    }

    /**
     * 根据文章 id 删除文章
     *
     * @param articleId
     */
    public void deleteById(Long articleId) {
        articleMapper.deleteByPrimaryKey(articleId);
    }

    /**
     * 查询博客的所有分类
     *
     * @return
     */
    public List<CategoryDTO> listCategories() {
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        // 查询该用户所有的博客
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
            // 查询某一个分类下的文章数量
            ArticleExample example = new ArticleExample();
            example.createCriteria().andArticleCategoriesEqualTo(categories.get(i));
            List<Article> articleList = articleMapper.selectByExample(example);
            categoryDTO.setCount(articleList.size());
            categoryDTOList.add(categoryDTO);
        }
        return categoryDTOList;
    }

    /**
     * 某个用户博客的所有分类
     *
     * @param username
     * @return
     */
    public List<CategoryDTO> listCategories(String username) {
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        // 查询该用户所有的博客
        ArticleExample articleExample = new ArticleExample();
        articleExample.createCriteria().andIdIsNotNull().andAuthorEqualTo(username);
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
            // 查询某一个分类下的文章数量
            ArticleExample example = new ArticleExample();
            example.createCriteria().andArticleCategoriesEqualTo(categories.get(i));
            List<Article> articleList = articleMapper.selectByExample(example);
            categoryDTO.setCount(articleList.size());
            categoryDTOList.add(categoryDTO);
        }
        return categoryDTOList;
    }

    /**
     * 根据 category 查找所有博客文章
     *
     * @param category
     * @return
     */
    public List<Article> listByCategory(String category) {
        ArticleExample articleExample = new ArticleExample();
        articleExample.createCriteria().andArticleCategoriesEqualTo(category);
        List<Article> articles = articleMapper.selectByExample(articleExample);
        return articles;
    }

    /**
     * 根据分类进行文章的分页
     *
     * @param category 分类
     * @param page     页数
     * @param size     每页文章总数
     * @return 文章分页
     */
    public PaginationDTO<Article> paginationByCategory(String category, Integer page, Integer size) {
        PaginationDTO<Article> paginationDTO = new PaginationDTO<>();

        /* 总页数 */
        Integer totalPage;

        // 获取所有分类为 category 的文章
        ArticleExample example = new ArticleExample();
        example.createCriteria().andArticleCategoriesEqualTo(category);
        List<Article> articles = articleMapper.selectByExample(example);

        // 根据文章总数计算总页数
        totalPage = totalPage(size, articles.size());

        // 防止页面页数越界
        page = setPage(page, totalPage);

        // 得到某一页的文章
        ArticleExample articleExample = new ArticleExample();
        articleExample.createCriteria().andArticleCategoriesEqualTo(category);
        List<Article> articleList = articleMapper.selectByExampleWithRowbounds(articleExample,
                new RowBounds((page - 1) * size, size));

        paginationDTO.setPagination(totalPage, page);
        paginationDTO.setData(articleList);
        return paginationDTO;
    }

    /**
     * 显示某个用户的所有博客
     *
     * @param username
     * @param page
     * @param size
     * @return
     */
    public PaginationDTO listByUsername(String username, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();

        /* 总页数 */
        Integer totalPage;

        // 获取所有文章
        ArticleExample articleExample = new ArticleExample();
        articleExample.createCriteria().andIdIsNotNull().andAuthorEqualTo(username);
        List<Article> articles = articleMapper.selectByExample(articleExample);
        if (articles == null || articles.size() == 0) {
            return null;
        }

        // 根据文章总数计算总页数
        totalPage = totalPage(size, articles.size());

        // 防止页面页数越界
        page = setPage(page, totalPage);

        // 根据总页数和当前页数计算当前页面需要显示的页数
        paginationDTO.setPagination(totalPage, page);
        // 将所有文章按时间倒序排序
        ArticleExample example = new ArticleExample();
        example.createCriteria().andIdIsNotNull().andAuthorEqualTo(username);
        example.setOrderByClause("publish_date desc");
        // 得到某一页的文章
        List<Article> articleList = articleMapper.selectByExampleWithRowbounds(example, new RowBounds((page - 1) * size,
                size));
        for (Article article : articleList) {
            String[] strings = article.getArticleTags().split(",");
        }
        paginationDTO.setData(articleList);
        return paginationDTO;
    }

    /**
     * 查询所有文章
     *
     * @return
     */
    public List<Article> findAll() {
        ArticleExample articleExample = new ArticleExample();
        articleExample.createCriteria().andIdIsNotNull();
        return articleMapper.selectByExample(articleExample);
    }

    /**
     * 根据标签查询文章
     *
     * @param tag
     * @param page
     * @param size
     * @return
     */
    public PaginationDTO findByTag(String tag, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();

        /* 总页数 */
        Integer totalPage;

        ArticleExample articleExample = new ArticleExample();
        // MySQL 查询字段中包含某字符串使用 %query%
        articleExample.createCriteria().andArticleTagsLike("%" + tag + "%");
        List<Article> articles = articleMapper.selectByExample(articleExample);

        // 根据文章总数计算总页数
        totalPage = totalPage(size, articles.size());

        // 防止页面页数越界
        page = setPage(page, totalPage);

        // 根据总页数和当前页数计算当前页面需要显示的页数
        paginationDTO.setPagination(totalPage, page);
        // 将所有文章按时间倒序排序
        articleExample.setOrderByClause("publish_date desc");
        // 得到某一页的文章
        List<Article> articleList = articleMapper.selectByExampleWithRowbounds(articleExample, new RowBounds((page - 1) * size,
                size));
        for (Article article : articleList) {
            String[] strings = article.getArticleTags().split(",");
        }
        paginationDTO.setData(articleList);
        return paginationDTO;
    }

    /**
     * 查询某个用户的所有文章
     *
     * @return
     */
    public List<Article> findAllByUser(String username) {
        ArticleExample articleExample = new ArticleExample();
        articleExample.createCriteria().andAuthorEqualTo(username);
        return articleMapper.selectByExample(articleExample);
    }

    /**
     * 在博客首页展示 5 篇热门文章（根据评论数排序）
     *
     * @return
     */
    public List<Article> hotArticles() {
        ArticleExample articleExample = new ArticleExample();
        articleExample.createCriteria().andCommentsGreaterThan(0);
        articleExample.setOrderByClause("comments desc");
        return articleMapper.selectByExampleWithRowbounds(articleExample, new RowBounds(0, 5));
    }

    /**
     * 文章添加评论数
     *
     * @param articleId
     */
    public void addCommentCount(Long articleId) {
        Article article = articleMapper.selectByPrimaryKey(articleId);
        article.setComments(article.getComments() + 1);
        articleMapper.updateByPrimaryKey(article);
    }

    /**
     * 文章更新
     *
     * @param article
     */
    public void update(Article article) {
        articleMapper.updateByPrimaryKey(article);
    }
}
