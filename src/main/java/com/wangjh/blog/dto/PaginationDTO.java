package com.wangjh.blog.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO<T> implements Serializable {
    /** 当前页数 */
    private Integer page;
    /** 总页数 */
    private Integer totalPage;
    /** 每一个页面的页数，如：当 page = 1 时，显示 1,2,3,4,5; 当 page = 4 时，显示 2,3,4,5,6 */
    private List<Integer> pages;
    /** 需要分类的 entity */
    private List<T> data;

    /**
     * 根据总页数和当前页数计算当前页面需要显示的页数
     * @param totalPage
     * @param page
     */
    public void setPagination(Integer totalPage, Integer page) {
        this.totalPage = totalPage;
        this.page = page;

        pages = new ArrayList<>();
        pages.add(page);
        for (int i = 1; i <= 2; i++) {
            if (page - i > 0) {
                pages.add(0, page - i);
            }
            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }
    }
}
