package com.wangjh.blog.dto;

import lombok.Data;

@Data
public class CommentDTO {
    /** 如果是添加评论，则只需要下面两个属性 */
    private Long articleId;
    private String content;
    /* 如果是对一个评论回复，则还需要下面两个属性*/
    /** 评论的 id */
    private Long commentId;
    /** 被回复者的 id，即被 @ 的用户的 id */
    private Long respondentId;
}
