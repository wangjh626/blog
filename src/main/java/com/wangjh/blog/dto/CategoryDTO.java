package com.wangjh.blog.dto;

import lombok.Data;

@Data
public class CategoryDTO {
    /** 在分类数组中的下标 */
    private Integer id;
    /** 分类名称 */
    private String name;
    /** 该分类下的文章篇数 */
    private Integer count;
}
