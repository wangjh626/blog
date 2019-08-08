package com.wangjh.blog.dto;

import com.wangjh.blog.entity.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    /** 为什么使用 pId 就不行了呢 */
    private Long parentId;
    private Long articleId;
    private String originalAuthor;
    private Long answererId;
    private String answererUsername;
    private Long respondentId;
    private String respondentUsername;
    private Long commentDate;
    private Integer likes;
    private String commentContent;
    private User user;
}
