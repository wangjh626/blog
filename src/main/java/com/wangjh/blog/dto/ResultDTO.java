package com.wangjh.blog.dto;

import lombok.Data;

@Data
public class ResultDTO<T> {
    private Integer code;
    private String message;
    private T data;

    /**
     * 请求成功时的不带数据的返回结果
     * @return
     */
    public static ResultDTO successOf() {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }

    /**
     * 请求成功时的带数据的返回结果
     * @param t
     * @param <T>
     * @return
     */
    public static <T> ResultDTO successOf(T t) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        resultDTO.setData(t);
        return resultDTO;
    }

    /**
     * 用户没有登录时不能点赞
     * @return
     */
    public static ResultDTO errorOf(Integer code) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        if (code == 201) {
            resultDTO.setMessage("未登录，无法点赞");
        }
        if (code == 202) {
            resultDTO.setMessage("不能给自己的博客点赞");
        }
        if (code == 203) {
            resultDTO.setMessage("您已经点过赞了哟~");
        }
        return resultDTO;
    }
}
